package android.application.com.multipledownloaderlist.uicomponent;

import android.application.com.multipledownloaderlist.R;
import android.application.com.multipledownloaderlist.uicomponent.helpers.AsyncThreadPoolDownloader;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OMIPLEKEVIN on 002 Mar 02, 2017.
 */

public class ParallelDownloader extends LinearLayout {

    private static final String TAG = "ParallelDownloader";
    /**
     * state when parallel downloads are still happening
     */
    private static final int _STATE_DOWNLOADING = 1;

    /**
     * state when parallel downloads are waiting to start or nothing is happening
     */
    private static final int _STATE_WAITING = 0;

    /**
     * state when some of the items in parallel downloads failed
     */
    private static final int _STATE_PART_FAILED = -1;

    /**
     * current state of the downloads
     */
    private static int _STATE = _STATE_WAITING;

    private Context context;
    private List<String> urls;
    private ItemDownloadState itemDownloadStateListener;
    private ParallelDownloadProgressListener parallelDownloadListener;
    private View view;
    private LinearLayout parentLinearView;

    public ParallelDownloader(Context context) {
        super(context);
        this.context = context;
        initializeViews();
    }

    public ParallelDownloader(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeViews();
    }

    public ParallelDownloader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeViews();
    }

    private void initializeViews() {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.paralleldownloader_main, this);
        parentLinearView = (LinearLayout) findViewById(R.id.parentLinearLayout);
    }

    public void setItemDownloadStateListener(ParallelDownloadProgressListener parallelDownloadListener) {
        this.parallelDownloadListener = parallelDownloadListener;
    }

    public void setDownloadUrlList(List<String> urlList) {
        this.urls = urlList;
    }

    private void setItemDownloadListener() {
        itemDownloadStateListener = new ItemDownloadState() {
            @Override
            public void onProgress(String fileName, int bytesDone, int bytesPerSec) {
                Log.d(TAG, "onProgress: " + fileName + ", " + Formatter.formatFileSize(context, bytesDone));
            }

            @Override
            public void onCompleted(String fileName) {
                if (parallelDownloadListener != null) {
                    parallelDownloadListener.onParallelDownloadFinished();
                }
            }

            @Override
            public void onFailed(String fileName) {

            }
        };
    }

    public int getParallelDownloadState() {
        return _STATE;
    }

    public boolean startDownloadQueue() {
        setItemDownloadListener();
        try {
            for (int i = 0; i < this.urls.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.listview_item_downloaditem_view, this, false);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        50));
                new AsyncThreadPoolDownloader(context,
                        this.urls.get(i),
                        view,
                        itemDownloadStateListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                Log.d(TAG, "creating DL view" + view + " for: " + this.urls.get(i));
                this.parentLinearView.addView(view);
                this.parentLinearView.invalidate();
            }
            _STATE = _STATE_DOWNLOADING;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            _STATE = _STATE_PART_FAILED;
            return false;
        }
    }

    public interface ParallelDownloadProgressListener {
        void onParallelDownloadStarted();

        void onParallelDownloadProgress(List<String> downloadingFiles);

        void onParallelDownloadFinished();

        void onParallelDownloadFailed(List<String> failedFiles);
    }

    public interface ItemDownloadState {
        void onProgress(String filename, int bytes, int bytesPerSec);
        void onCompleted(String filename);
        void onFailed(String filename);
    }
}
