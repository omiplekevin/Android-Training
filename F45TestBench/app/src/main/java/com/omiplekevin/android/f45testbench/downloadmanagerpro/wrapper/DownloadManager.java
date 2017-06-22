package com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.DownloadManagerPro;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.QueueSort;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.TaskStates;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.report.ReportStructure;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.report.listener.DownloadManagerListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 05, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class DownloadManager implements DownloadManagerListener {

    private static final String TAG = "DownloadManager";
    private static final String _DIR_MAIN = Environment.getExternalStorageDirectory() + "/F45";
    private static final String _DIR_VIDEO = _DIR_MAIN + "/videos";
    private static final String _DIR_IMAGE = _DIR_MAIN + "/images";
    private static final String _DIR_MUSIC = _DIR_MAIN + "/music";
    private static final String _DIR_EXTRAS = _DIR_MAIN + "/extras";
    private static final String _DIR_TEMP = _DIR_MAIN + "/.temp";
    private static final String _DIR_APK = _DIR_MAIN + "/apk";
    private static final String _DIR_LOGS = _DIR_MAIN + "/log";
    private static final String _DIR_MOVEMENT = _DIR_MAIN + "/movement";

    private DownloadManagerPro downloadManagerPro;
    private RequestQueue volleyRequestQueue;
    private List<DownloadEvent> downloadUrls = new ArrayList<>();
    private List<DownloadEvent> downloadItemList = new ArrayList<>();
    private ConcurrentHashMap<Integer, DownloadEvent> mappedDownloadTasks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<Long, Integer> attempts = new ConcurrentHashMap<>();
    private int priorityCount = 0;
    private int nonPriorityCount = 0;
    private int ALLOWED_RETRIES = 10;
    private int COUNTDOWN_VALUE = 10;
    private int MAX_CHUNKS = 1;
    private int MAX_ASYNC_DOWNLOADS = 4;
    private int DOWNLOAD_COUNT = 0;
    private int PRIORITIZATION = QueueSort.HighToLowPriority;

    private Context context;
    private Subject subject;

    public DownloadManager(Activity activity, Subject subject) {
        this.context = activity.getApplicationContext();
        volleyRequestQueue = Volley.newRequestQueue(this.context);
        /*this.subject = subject;*/
    }

    @Override
    public void OnDownloadStarted(long taskId) {
        Log.d("DownloadManager", "OnDownloadStarted: " + taskId);
        ReportStructure struct = downloadManagerPro.singleDownloadStatus((int) taskId);
        if (mappedDownloadTasks != null || struct != null) {
            DownloadEvent downloadEvent = mappedDownloadTasks.get((int) taskId);
            if (downloadEvent != null) {
                downloadEvent.state = struct.state;
                Log.d("DownloadManager", "OnDownloadStarted state: " + struct.state);
                EventBus.getDefault().post(downloadEvent);
            } else {
                Log.d("DownloadManager", "OnDownloadStarted downloadEvent is null");
            }
        } else {
            Log.d("DownloadManager", "OnDownloadStarted, can't get taskID: " + taskId + ", mappedDownloadTasks: " + mappedDownloadTasks + ", struct: " + struct);
        }
    }

    @Override
    public void OnDownloadPaused(final long taskId) {
        Log.d("DownloadManager", "OnDownloadPaused: " + taskId + ", state: " + (getDownloadManagerPro().singleDownloadStatus((int)taskId)).state);
    }

    @Override
    public void onDownloadProcess(final long taskId, final double percent, final long downloadedLength) {
        // TODO: Jun 12, 0012 implement observer here...
        Log.d("DownloadManager", "onDownloadProcess");
        ReportStructure struct = downloadManagerPro.singleDownloadStatus((int) taskId);
        if (mappedDownloadTasks != null || struct != null) {
            DownloadEvent downloadEvent = mappedDownloadTasks.get((int) taskId);
            if (downloadEvent != null) {
                downloadEvent.state = struct.state;
                downloadEvent.percentage = percent;
                downloadEvent.receivedBytes = downloadedLength;
                downloadEvent.fileSize = struct.fileSize;
                EventBus.getDefault().post(downloadEvent);
            } else {
                Log.d("DownloadManager", "onDownloadProcess downloadEvent is null");
            }
        } else {
            Log.d("DownloadManager", "onDownloadProcess, can't get taskID: " + taskId + ", mappedDownloadTasks: " + mappedDownloadTasks + ", struct: " + struct);
        }

        //clean up attempts table
        if (attempts != null) {
            Integer attempt = attempts.get(taskId);
            if (attempt != null) {
                attempts.remove(taskId);
            }
        }
    }

    @Override
    public void OnDownloadFinished(long taskId) {
        Log.d("DownloadManager", "OnDownloadFinished: " + taskId);
        ReportStructure struct = downloadManagerPro.singleDownloadStatus((int) taskId);
        if (mappedDownloadTasks != null) {
            DownloadEvent downloadEvent = mappedDownloadTasks.get((int) taskId);
            if (downloadEvent != null) {
                downloadEvent.state = struct.state;
                downloadEvent.percentage = TaskStates.DOWNLOAD_FINISHED;
                EventBus.getDefault().post(downloadEvent);
            } else {
                Log.d("DownloadManager", "OnDownloadFinished downloadEvent is null");
            }
        } else {
            Log.d("DownloadManager", "OnDownloadFinished, can't get taskID: " + taskId + ", mappedDownloadTasks: " + mappedDownloadTasks);
        }
    }

    @Override
    public void OnDownloadRebuildStart(long taskId) {
        Log.d("DownloadManager", "OnDownloadRebuildStart: " + taskId);
    }

    @Override
    public void OnDownloadRebuildFinished(long taskId) {
        Log.d("DownloadManager", "OnDownloadRebuildFinished: " + taskId);
    }

    @Override
    public void OnDownloadCompleted(long taskId) {
        Log.d("DownloadManager", "OnDownloadCompleted: " + taskId);
        downloadManagerPro.notifiedTaskChecked();
        Log.d("DownloadManager", "OnDownloadCompleted: completed downloads - " + DOWNLOAD_COUNT-- + "/" + downloadUrls.size());
//        for (int i = 0; i < cdls.size(); i++) {
//            Log.d("OnDownloadCompleted", "OnDownloadCompleted: last completed" + cdls.get(i).name);
//        }
    }

    @Override
    public void connectionLost(long taskId) {
        /*if (ALLOWED_RETRIES > 0) {
            Log.d(getClass().getSimpleName(), "RETRYING[" + ALLOWED_RETRIES + "]...");
            ALLOWED_RETRIES--;
            try {
                downloadManagerPro.startQueueDownload(3, PRIORITIZATION);
            } catch (QueueDownloadInProgressException e) {
                e.printStackTrace();
            }
        }*/
    }

    ///////////////////////////////////////////////////////////////////////////
    // METHODS
    ///////////////////////////////////////////////////////////////////////////
    public void initialize() {
        downloadManagerPro = new DownloadManagerPro(this.context);
        //TEMP create dummy folder
        //initialize DownloadManagerPro
        //by default, sdCardFolderAddress is pointed to: /mnt/internal_sd/
        downloadManagerPro.init("F45/.temp", MAX_CHUNKS, this);
    }

    /**
     * DEVELOPER:       OMIPLEKEVIN<br/>
     * LAST MODIFIED:   Jun 14, 0014<br/>
     * IN CLASS:        DownloadManager<br/>
     * <br/>
     * sets prioritization to the download manager. <code>int</code> IDs are used, see {@link QueueSort} for Prioritization reference<br/>
     * Prioritization is set to {@link com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.QueueSort}.<i><code>HighToLowPriority</code></i>
     * <br/>
     *
     * @param prioritization <code>int</code> refer to {@link QueueSort}
     */
    public void setPrioritization(int prioritization) {
        PRIORITIZATION = ((prioritization >= 0 && prioritization < 6) ? prioritization : 0);
    }

    public void addToQueue(List<DownloadEvent> downloadEvents) {
        if (mappedDownloadTasks == null) {
            mappedDownloadTasks = new ConcurrentHashMap<>();
        }
        for (int i = 0; i < downloadItemList.size(); i++) {
            DownloadEvent item = downloadItemList.get(i);
            int taskId = downloadManagerPro.addTask(item.filename, item.source.replace(" ", "%20"), 6, item.savePath, item.overwrite, item.highpriority);
            downloadItemList.get(i).taskId = (long) taskId;
            mappedDownloadTasks.put((int) downloadItemList.get(i).taskId, item);
        }

        Log.d("DownloadManager", "addToQueue mappedDownloadTasks" + mappedDownloadTasks.toString());
    }

    public void startADMDownloads() {
        if (downloadManagerPro != null) {
            Log.d(getClass().getSimpleName(), "downloadManagerPro is not null!");
            try {
                downloadManagerPro.startQueueDownload(MAX_ASYNC_DOWNLOADS, PRIORITIZATION);
            } catch (Exception e) {
                Log.w(getClass().getSimpleName(), "Task is already on queue and already downloading...");
                e.printStackTrace();
            }
        } else {
            Log.d("DownloadManager", "startADMDownloads: downloadManager is null!");
        }
    }

    public void addFile(DownloadEvent downloadItem) {
        if (downloadUrls == null) {
            System.out.println("download list is null, creating new instance");
            downloadUrls = new ArrayList<>();
        }

        downloadUrls.add(downloadItem);
        downloadUrls = removeDuplicateEntries(downloadUrls);
    }

    public void addAllFiles(List<DownloadEvent> downloadItems) {
        if (downloadUrls == null) {
            downloadUrls = new ArrayList<>();
            System.out.println("download list is null, creating new instance");
        }

        downloadUrls.addAll(downloadItems);
        downloadUrls = removeDuplicateEntries(downloadUrls);
    }

    public List<DownloadEvent> prepareFiles() {
        if (downloadUrls == null) {
            Log.d("DownloadManager", "prepareFiles, returning empty list, download URL list is empty");
            return new ArrayList<>();
        } else {
            //remove duplicate files
            downloadUrls = removeDuplicateEntries(downloadUrls);
            //get download count so we can track downloaded files
            DOWNLOAD_COUNT = downloadUrls.size();
            Log.d("DownloadManager", "prepareFiles DOWNLOAD_COUNT: " + DOWNLOAD_COUNT);
            //set non-priority count since, priority count is counted inside removeDuplicateEntries()
            nonPriorityCount = DOWNLOAD_COUNT - priorityCount;
            //lets assign additional data for the download items
            downloadItemList = assignFileInformation(downloadUrls);
            Log.d("DownloadManager", "prepareFiles, priority count: " + priorityCount + ", nonPriority count: " + nonPriorityCount);
            //return true if the preparation is successful, else false
            return downloadItemList;
        }
    }

    private ArrayList<DownloadEvent> removeDuplicateEntries(List<DownloadEvent> downloadUrls) {
        ArrayList<DownloadEvent> finalItems = new ArrayList<>();
        int prioCount = 0;
        for (DownloadEvent lhs : downloadUrls) {
            boolean add = true;
            for (DownloadEvent rhs : finalItems) {
                if (lhs.source.equals(rhs.source)) {
                    System.out.println("DUPLICATE FOUND " + lhs.source);
                    add = false;
                    break;
                }
            }
            if (add) {
                if (lhs.highpriority) {
                    prioCount++;
                }
                finalItems.add(lhs);
            }
        }

        priorityCount = prioCount;

        return finalItems;
    }

    public DownloadManagerPro getDownloadManagerPro() {
        return downloadManagerPro;
    }

    /*private int doCountdown(final long taskId, final int duration, int attempts, final long timestamp) {
        Log.d("DownloadManager", "doCountdown");
        DownloadEvent event = new DownloadEvent();
        if (mappedDownloadTasks != null) {
            event = mappedDownloadTasks.get(taskId);
        }
        CountDownloadAsync countDownloadAsync = new CountDownloadAsync(new CountDownloadAsync.CountdownListener() {
            @Override
            public void onProgress(int attempt, int countdown) {
                Log.d("DownloadManager", "countdown for " + taskId + " retry download: " + countdown + ", " + attempt + " made");
            }

            @Override
            public void onCountdownDone() {
                Log.d("DownloadManager", "countdown done, should resume download...");
                try {
                    getDownloadManagerPro().startDownload((int)taskId);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, event, duration, attempts, timestamp);
        //start counting down for the download...
        countDownloadAsync.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
        return ++attempts;
    }*/

    ///////////////////////////////////////////////////////////////////////////
    // UTILITIES
    ///////////////////////////////////////////////////////////////////////////

    public static List<DownloadEvent> assignFileInformation(List<DownloadEvent> downloadUrls) {
        for (int i = 0; i < downloadUrls.size(); i++) {
            //set file extension
            downloadUrls.get(i).fileExtension = getFileExtension(downloadUrls.get(i).source);
            //set file name - this contains also the file extension
            downloadUrls.get(i).filename = Uri.parse(downloadUrls.get(i).source).getLastPathSegment();
            //remove the file extension from the file name because the downloader appends it automatically
            downloadUrls.get(i).filename = downloadUrls.get(i).filename.replace("." + downloadUrls.get(i).fileExtension, "");
            downloadUrls.get(i).filename = (downloadUrls.get(i).highpriority ? "HIGHPRIO-" : "") + downloadUrls.get(i).filename;
            //set file save path
            downloadUrls.get(i).savePath = getSavePath(downloadUrls.get(i).source);
//            Log.d("DownloadManager", "assignFileInformation: " + downloadUrls.toString());
        }
        return downloadUrls;
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
        switch (classifier(sourceUrl)) {
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
        String fileExtension = getFileExtension(sourceUrl);
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

    public static void initializeDirectories() {
        File mainFolder = new File(_DIR_MAIN);
        if (mainFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File videoFolder = new File(_DIR_VIDEO);
        if (videoFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_VIDEO + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File imageFolder = new File(_DIR_IMAGE);
        if (imageFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_IMAGE + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File musicFolder = new File(_DIR_MUSIC);
        if (musicFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_MUSIC + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File extrasFolder = new File(_DIR_EXTRAS);
        if (extrasFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_EXTRAS + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File tempFolder = new File(_DIR_TEMP);
        tempFolder.setExecutable(true);
        if (tempFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_TEMP + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File apkFolder = new File(_DIR_APK);
        apkFolder.setExecutable(true);
        if (apkFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_APK + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        File logsFolder = new File(_DIR_LOGS);

        if (logsFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_LOGS + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }

        logsFolder = new File(_DIR_LOGS);

        if (logsFolder.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_LOGS + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }


        File movementAnimations = new File(_DIR_MOVEMENT);
        if (movementAnimations.mkdir()) {
            Log.i("initializeDirectories", "created '/F45/" + _DIR_MOVEMENT + "' Folder");
        } else {
            Log.w("initializeDirectories", "either existing OR unable to create folder");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // CUSTOM CLASS
    ///////////////////////////////////////////////////////////////////////////
//    public static class CountDownloadAsync extends AsyncTask<Void, Integer, Void> {
//
//        private CountdownListener listener;
//        private int duration;
//        private int attemptsRemaining;
//        private long timestamp;
//        private DownloadEvent event;
//
//        public CountDownloadAsync(CountdownListener listener, DownloadEvent event, int duration, int remainingAttempts, long timestamp) {
//            this.listener = listener;
//            this.event = event;
//            this.duration = duration;
//            this.attemptsRemaining = remainingAttempts;
//            this.timestamp = timestamp;
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            int cDown = duration;
//            while (cDown-- > 0) {
//                publishProgress(attemptsRemaining, cDown);
//                try {
//                    Thread.currentThread().sleep(1000L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
//            if (this.listener != null) {
//                this.listener.onProgress(values[0], values[1]);
//                if (this.event != null) {
//                    EventBus.getDefault().post(this.event);
//                }
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            super.onPostExecute(aVoid);
//            if (this.listener != null) {
//                this.listener.onCountdownDone();
//            }
//        }
//
//        public interface CountdownListener {
//            void onProgress(int attempt, int countdown);
//            void onCountdownDone();
//        }
//    }
}
