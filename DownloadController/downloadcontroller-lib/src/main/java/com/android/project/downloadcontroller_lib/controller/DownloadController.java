package com.android.project.downloadcontroller_lib.controller;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.android.project.downloadcontroller_lib.dao.DownloadRequest;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   July 07, 2017<br/>
 * IN CLASS:        com.android.project.downloadcontroller<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadController {

    public static final String TAG = "DownloadController";

    private static final String _DIR_MAIN = "/F45";
    private static final String _DIR_VIDEO = _DIR_MAIN + "/videos";
    private static final String _DIR_IMAGE = _DIR_MAIN + "/images";
    private static final String _DIR_MUSIC = _DIR_MAIN + "/music";
    private static final String _DIR_EXTRAS = _DIR_MAIN + "/extras";
    private static final String _DIR_TEMP = _DIR_MAIN + "/.temp";
    private static final String _DIR_APK = _DIR_MAIN + "/apk";
    private static final String _DIR_LOGS = _DIR_MAIN + "/log";
    private static final String _DIR_MOVEMENT = _DIR_MAIN + "/movement";

    private final long POLL_RATE_MS = 1000;

    private static volatile DownloadController _instance;

    private static DownloadManager downloadManager;

    private int MAX_DOWNLOAD_BUCKET_QUEUE = 3;
    private HashMap<String, DownloadRequest> downloadRequestMap = new HashMap<>();

    private ArrayBlockingQueue<DownloadRequest> onProcessQueue = new ArrayBlockingQueue<>(MAX_DOWNLOAD_BUCKET_QUEUE);
    private ArrayBlockingQueue<DownloadRequest> waitingQueue = new ArrayBlockingQueue<>(500);

    private ScheduledExecutorService pollUpdater = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture pollUpdaterFuture;

    public static DownloadController getInstance(Context context) {
        if (_instance == null) {
            synchronized (DownloadController.class) {
                if (_instance == null) {
                    _instance = new DownloadController(context);
                }
            }
        }
        return _instance;
    }

    /**
     * DEVELOPER:       OMIPLEKEVIN<br/>
     * LAST MODIFIED:   Jul 09, 0009<br/>
     * IN CLASS:        DownloadController<br/>
     * <br/>
     * once constructor is called - we need to update all queued downloads from DownloadManager and update our map for easy referencing
     * <br/>
     */
    public DownloadController(Context context) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        getQueuedDownloads();
    }

    public void setDownloadQueueLimit(int limit) {
        MAX_DOWNLOAD_BUCKET_QUEUE = limit;
        onProcessQueue = new ArrayBlockingQueue<>(MAX_DOWNLOAD_BUCKET_QUEUE);
    }

    private void getQueuedDownloads() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterByStatus(
                DownloadManager.STATUS_PAUSED |
                        DownloadManager.STATUS_PENDING |
                        DownloadManager.STATUS_RUNNING);
        Cursor cursor = downloadManager.query(query);
        if (!cursor.moveToFirst()) {
            Log.e(TAG, "getQueuedDownloads: you don't have any queued downloads");
        } else {
            Log.e(TAG, "getQueuedDownloads: you have queued downloads");
            int counts = 0;
            do {
                int idIndex = cursor.getColumnIndex(DownloadManager.COLUMN_ID);
                int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
                int savePathIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                int filenameIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TITLE);
                int fileExtensionIndex = cursor.getColumnIndex(DownloadManager.COLUMN_MEDIA_TYPE);
                int stateIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                int reasonIndex = cursor.getColumnIndex(DownloadManager.COLUMN_REASON);
                DownloadRequest request = new DownloadRequest(
                        cursor.getLong(idIndex),
                        cursor.getString(uriIndex),
                        cursor.getString(savePathIndex),
                        cursor.getString(filenameIndex),
                        cursor.getString(fileExtensionIndex),
                        cursor.getInt(stateIndex),
                        cursor.getString(reasonIndex)
                );

                //only limit a size of MAX_DOWNLOAD_BUCKET_QUEUE on the download queue of the download manager
                synchronized (onProcessQueue) {
                    if (cursor.getInt(stateIndex) == DownloadManager.STATUS_RUNNING && onProcessQueue.remainingCapacity() <= MAX_DOWNLOAD_BUCKET_QUEUE) {
                        onProcessQueue.add(request);
                    } else {
                        Log.w(TAG, "getQueuedDownloads: " + cursor.getString(stateIndex));
                    }
                }
                //BUT keep track of those items
                mapDownloadRequests(request);
                Log.d(TAG, "requestContent: " + request.toString());
                counts++;
            } while (cursor.moveToNext());
            Log.d(TAG, "found " + counts + " queuedDownloads");
        }
    }

    private void mapDownloadRequests(DownloadRequest request) {
        if (downloadRequestMap == null) {
            Log.w(TAG, "mapDownloadRequests: downloadRequestMap is null, re-initializing");
            downloadRequestMap = new HashMap<>();
        }
        downloadRequestMap.put(request.getUrl(), request);

        //check on the waiting list, if there is no duplicate, then add to the waiting list
        boolean shouldAddToWaiting = true;
        Iterator<DownloadRequest> requestIterator = onProcessQueue.iterator();
        while (requestIterator.hasNext()) {
            if (requestIterator.next().getUrl().equals(request.getUrl())) {
                shouldAddToWaiting = false;
                break;
            }
        }

        if (shouldAddToWaiting) {
            waitingQueue.add(request);
        }
    }

    public void addToQueue(List<DownloadRequest> requestList) {
        if (downloadRequestMap != null) {
            for (DownloadRequest request : requestList) {
                addToQueue(request);
            }
        }
    }

    public void addToQueue(DownloadRequest request) {
        if (downloadRequestMap != null && downloadManager != null) {
            //add to request map
            mapDownloadRequests(request);
        }
    }

    private DownloadManager.Request createDownloadManagerRequest(DownloadRequest request) {
        DownloadManager.Request req = new DownloadManager.Request(Uri.parse(request.getUrl()))
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                .setDestinationInExternalPublicDir(request.getSavePath(), request.getSaveName())
                .setAllowedOverMetered(true);
//                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
//                    .setVisibleInDownloadsUi(false);

        return req;
    }

    private boolean isEnqueued(String sourceUrl) {
        //check first on the onProcessQueue
        Iterator<DownloadRequest> bucketRequest = onProcessQueue.iterator();
        while (bucketRequest.hasNext()) {
            DownloadRequest br = bucketRequest.next();
            if (br.getUrl().replace("%20", " ").equals(sourceUrl.replace("%20", " "))) {
                return true;
            }
        }

        //reset iterator(?) to check for waitingQueue
        bucketRequest = waitingQueue.iterator();
        while (bucketRequest.hasNext()) {
            DownloadRequest br = bucketRequest.next();
            if (br.getUrl().replace("%20", " ").equals(sourceUrl.replace("%20", " "))) {
                if (br.getState() == DownloadManager.STATUS_SUCCESSFUL || br.getPercentage() == 100F) {
                    return true;
                }
            }
        }

        return false;
    }

    public void startListening() {

        //start listening to the downloads - with or without
        pollUpdaterFuture = pollUpdater.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.w(TAG, "polling...");
                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterByStatus(
                        DownloadManager.STATUS_PAUSED |
                                DownloadManager.STATUS_PENDING |
                                DownloadManager.STATUS_RUNNING |
                                DownloadManager.STATUS_SUCCESSFUL);
                Cursor cursor = downloadManager.query(query);
                if (!cursor.moveToFirst()) {
                    Log.e(TAG, "getQueuedDownloads: you don't have any queued downloads");
                    if (waitingQueue.remainingCapacity() > 0) {
                        //lets add just ONE item to kick start the requests queuing below
                        DownloadRequest newRequest = waitingQueue.poll();
                        //enqueue download to DownloadManager
                        if (!isEnqueued(newRequest.getUrl())) {
                            long downloadID = downloadManager.enqueue(createDownloadManagerRequest(newRequest));
                            //set the downloadID of the request
                            newRequest.setDownloadID(downloadID);
                            //assign the task
                            downloadRequestMap.get(newRequest.getUrl()).setDownloadID(downloadID);

                            onProcessQueue.add(downloadRequestMap.get(newRequest.getUrl()));

                            Log.d(TAG, "adding new items to download, " + onProcessQueue.remainingCapacity() + " available slots");
                        }
                    }
                } else {
                    Log.e(TAG, "======================================================");
                    Log.d(TAG, "cursor: " + cursor.getCount());
                    Log.d(TAG, "cursor: queuing...");
                    do {
                        int idIndex = cursor.getColumnIndex(DownloadManager.COLUMN_ID);
                        int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_URI);
                        int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                        int receivedSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
                        int totalSizeIndex = cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);

                        /**
                         * STATUS_SUCCESSFUL = 8
                         * STATUS_PAUSED = 4
                         * STATUS_PENDING = 1
                         * STATUS_RUNNING = 2
                         */
                        Log.d(TAG, "download progress: " +
                                cursor.getLong(idIndex) + ", " +
                                cursor.getString(uriIndex) + ", " +
                                cursor.getInt(statusIndex) + ", " +
                                cursor.getLong(receivedSizeIndex) + ", " +
                                cursor.getLong(totalSizeIndex));
                        float percentage = ((float) cursor.getLong(receivedSizeIndex) / (float) cursor.getLong(totalSizeIndex)) * 100F;

                        if (percentage < 0) {
                            Log.w(TAG, "NEGATIVE! " + cursor.getString(uriIndex) + " - " +
                                    cursor.getLong(receivedSizeIndex) + " - " +
                                    cursor.getLong(totalSizeIndex));
                        }
                        try {
                            downloadRequestMap.get(cursor.getString(uriIndex)).setDownloadPercentage(percentage);
                            downloadRequestMap.get(cursor.getString(uriIndex)).setState(cursor.getInt(statusIndex));
                        } catch (Exception e) {
                            e.printStackTrace();
                            //create a new record
                            mapDownloadRequests(new DownloadRequest(
                                    cursor.getLong(idIndex),
                                    cursor.getString(uriIndex),
                                    getSavePath(cursor.getString(uriIndex)),
                                    Uri.parse(cursor.getString(uriIndex)).getLastPathSegment(),
                                    getFileExtension(cursor.getString(uriIndex))));
                        }

                        if (cursor.getInt(statusIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                            Log.w(TAG, "we have complete download: " + cursor.getString(uriIndex));
                            boolean removedItem = onProcessQueue.remove(downloadRequestMap.get(cursor.getString(uriIndex)));
                            waitingQueue.remove(downloadRequestMap.get(cursor.getString(uriIndex)));
                            Log.w(TAG, "we have removed item from bucket: " + removedItem + ", new size: " + onProcessQueue.size());
                        }

                    } while (cursor.moveToNext());
                    cursor.close();

                    //queue this to download
                    if (onProcessQueue.remainingCapacity() > 0 && waitingQueue.size() > 0) {
                        //fill in new item(s) to the bucket depending on the allowed slots on the downloadBucket
                        for (int i = 0; i < onProcessQueue.remainingCapacity(); i++) {
                            Log.d(TAG, "adding new items to download, " + onProcessQueue.remainingCapacity() + " available slots");
                            DownloadRequest newRequest = waitingQueue.poll();
                            //check if is on queue
                            if (!isEnqueued(newRequest.getUrl())) {
                                //enqueue download to DownloadManager
                                long downloadID = downloadManager.enqueue(createDownloadManagerRequest(newRequest));
                                //set the downloadID of the request
                                newRequest.setDownloadID(downloadID);
                                //assign the task
                                downloadRequestMap.get(newRequest.getUrl()).setDownloadID(downloadID);

                                onProcessQueue.add(downloadRequestMap.get(newRequest.getUrl()));
                            }
                        }
                    } else {
                        Log.w(TAG, onProcessQueue.remainingCapacity() + "/" + MAX_DOWNLOAD_BUCKET_QUEUE + " download bucket is full...");
                    }

                    try {
                        //insert event bus here...
                        EventBus.getDefault().post(downloadRequestMap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e(TAG, "======================================================");
                } // END OF ELSE
            } // END OF RUN
        }, 100L, POLL_RATE_MS, TimeUnit.MILLISECONDS);
    }

    public void stopListening() {
        if (pollUpdaterFuture != null) {
            pollUpdaterFuture.cancel(true);
        }
    }

    /**
     * DEVELOPER:       OMIPLEKEVIN<br/>
     * LAST MODIFIED:   Jul 12, 0012<br/>
     * IN CLASS:        DownloadController<br/>
     * <br/>
     * get a specific entry of download from the mapped list of downloads.
     * <b>!IMPORTANT!</b> - <code>url</code> passed on should be the unescaped url, not the escaped value
     * <br/>
     *
     * @param url
     * @return
     */
    public DownloadRequest getDownloadState(String url) {
        return downloadRequestMap.get(url);
    }

    public void cancelDownload(long downloadID) {
        if (downloadManager != null) {
            int recordsDeleted = downloadManager.remove(downloadID);
            Log.d(TAG, "cancelDownload - records deleted: " + recordsDeleted);
        }
    }

    public void reorderQueue() {

    }

    public void showQueue(String tag, ArrayBlockingQueue<DownloadRequest> request) {
        Iterator<DownloadRequest> iterator = request.iterator();
        while (iterator.hasNext()) {
            Log.w(TAG, tag + " QUEUE: " + iterator.next().getUrl());
        }
    }

    public static String getFileExtension(String url) {
        if (url.contains(".")) {
            String subUrl = url.substring(url.lastIndexOf('.') + 1);
            Log.d("DownloadManager", "getFileExtension: " + subUrl);
            if (subUrl.length() == 0) {
                return "";
            }

            if (subUrl.contains("?")) {
                return subUrl.substring(0, subUrl.indexOf('?'));
            }

            return subUrl;
        } else {
            //option to follow link of get the redirect link from the headers
        }

        return "";
    }

    public static String getSavePath(String sourceUrl) {
        switch (classifier(sourceUrl.replace("%20", " "))) {
            case 1:
                return _DIR_VIDEO;
            case 2:
                return _DIR_IMAGE;
            case 3:
                return _DIR_MUSIC;
            case 4:
                return _DIR_MOVEMENT;
            default:
                return _DIR_EXTRAS;
        }
    }

    public static int classifier(String sourceUrl) {
        String fileExtension = getFileExtension(sourceUrl.replace("%20", " "));
        fileExtension = fileExtension.toUpperCase(Locale.getDefault());
        switch (fileExtension) {
            case "MP4":
            case "3GP":
            case "MOV":
            case "M4A":
                //video file classification
                return 1;
            case "JPG":
            case "JPEG":
            case "PNG":
            case "BMP":
                //image file classification
                return 2;
            case "MP3":
            case "OGG":
            case "WMV":
                //music file classification
                return 3;
            case "GIF":
                //gif file classification
                return 4;
            default:
                //unknown / unlisted classification
                return 0;
        }
    }
}
