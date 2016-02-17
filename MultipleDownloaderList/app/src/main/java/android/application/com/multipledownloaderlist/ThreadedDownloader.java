package android.application.com.multipledownloaderlist;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by OMIPLEKEVIN on Jan 27, 2016.
 *
 */
public class ThreadedDownloader extends IntentService {

    private static volatile ThreadedDownloader instance;
    private static ThreadPoolExecutor taskExecutor;
    private static List<String> urls;

    private final static int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private final static long TIMEOUT_EXECUTION = 60L;
    private final static String OUTPUT_DIR = Environment.getExternalStorageDirectory() + "/F45";
    private final String TAG = "ThreadedDownloader";

    public ThreadedDownloader(){
        super("ThreadDownloader");
    }

    public static ThreadedDownloader getInstance() {
        if (instance == null) {
            synchronized (ThreadedDownloader.class) {
                if (instance == null) {
                    instance = new ThreadedDownloader();
                    taskExecutor = new ThreadPoolExecutor(NUMBER_OF_CORES,
                            NUMBER_OF_CORES,
                            TIMEOUT_EXECUTION,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<Runnable>());
                }
            }
        }

        return instance;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        performDownloadQueue();
    }

    public static void setDownloadableList(List<String> url) {
        ThreadedDownloader.urls = url;
    }

    private void performDownloadQueue() {
        for (final String url : urls) {
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    Log.e(TAG, "DOWNLOAD_QUEUE: " + url);
                    downloadQueueItem(url, new DownloadItemCallback() {
                        @Override
                        public void onDownloadQueue(int fileSize) {

                        }

                        @Override
                        public void onDownloadProgress(int downloaded, int streamDownloadedSize) {
                            Log.e(TAG, "callback: " + url + " " + downloaded + " " + streamDownloadedSize);
                        }

                        @Override
                        public void onDownloadComplete() {

                        }

                        @Override
                        public void onDownloadError(String errMessage) {

                        }
                    });
                }
            });
        }
    }

    private void downloadQueueItem(String sourceUrl, DownloadItemCallback callback) {
        try {
            int contentLength = 0;
            String fileName = sourceUrl.substring(sourceUrl.lastIndexOf("/") + 1, sourceUrl.length());

            URL url = new URL(sourceUrl);
            URLConnection urlConnection = url.openConnection();
            contentLength = urlConnection.getContentLength();
            urlConnection.connect();

            if (callback != null) {
                callback.onDownloadQueue(contentLength);
            }


            InputStream inputStream = new BufferedInputStream(url.openStream(), 4096);
            OutputStream outputStream = new FileOutputStream(OUTPUT_DIR + "/" + fileName);

            byte data[] = new byte[4096];
            int count;
            int currentTotal = 0;
            while ((count = inputStream.read(data)) != -1) {
                currentTotal += count;
                outputStream.write(data, 0, count);
                Log.e(TAG, sourceUrl + " " + currentTotal);
                if (callback != null) {
                    callback.onDownloadProgress(currentTotal, count);
                }
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            if (callback != null) {
                callback.onDownloadComplete();
            }


        } catch (IOException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.onDownloadError(e.getMessage());
            }

        }
    }
}
