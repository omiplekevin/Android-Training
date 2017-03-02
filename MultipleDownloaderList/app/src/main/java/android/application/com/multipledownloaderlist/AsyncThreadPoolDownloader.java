package android.application.com.multipledownloaderlist;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by OMIPLEKEVIN on 001 Mar 01, 2017.
 */

public class AsyncThreadPoolDownloader extends AsyncTask<Void, Integer, Boolean> {

    private String sourceUrl;
    private int contentLength, currentPercentage;
    private Context context;
    private View parentView;
    private ProgressBar progressBar;
    private TextView progressText;
    private TextView filename;
    private ImageView doneCheck;

    public AsyncThreadPoolDownloader(Context context,
                                     String sourceUrl,
                                     View parentView,
                             TextView filename,
                             ProgressBar progressBar,
                             TextView progressText,
                             ImageView doneCheck) {
        this.context = context;
        this.sourceUrl = sourceUrl;
        this.parentView = parentView;
        this.filename = filename;
        this.progressBar = progressBar;
        this.progressText = progressText;
        this.doneCheck = doneCheck;
//            this.callback = callback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        final String outputDir = Environment.getExternalStorageDirectory() + "/F45/.temp";
        final String fileName = this.sourceUrl.substring(this.sourceUrl.lastIndexOf("/") + 1, this.sourceUrl.length());
        try {
            if (!(new File(outputDir + "/" + fileName)).exists()) {
                URL url = new URL(this.sourceUrl);
                URLConnection urlConnection = url.openConnection();
                contentLength = urlConnection.getContentLength();
                publishProgress(contentLength, 0);
                urlConnection.connect();

                InputStream inputStream = new BufferedInputStream(url.openStream(), 4096);
                OutputStream outputStream = new FileOutputStream(outputDir + "/" + fileName);

                byte data[] = new byte[4096];
                int count;
                int currentTotal = 0;
                while ((count = inputStream.read(data)) != -1) {
                    currentTotal += count;
                    outputStream.write(data, 0, count);
                    //                    final int percentage = (int)(((float)currentTotal / (float)contentLength) * 100);
                    //                    Log.e("PROGRESS", sourceUrl + " - " + percentage + "");
                    publishProgress(count, 1, currentTotal);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } else {
                Log.d("AsyncDownloader", "already present: " + fileName);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        try {
            if (this.progressBar != null) {
                if (values[1] == 0) {
                    this.progressBar.setMax(values[0]);
                } else if (values[1] == 1) {
                    this.progressBar.setProgress(this.progressBar.getProgress() + values[0]);
                    this.progressBar.invalidate();
                    this.progressText.setText(Formatter.formatFileSize(context, values[2]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            this.doneCheck.setVisibility(View.VISIBLE);
        } else {
            this.doneCheck.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_error));
//                new VideoDownloadTask(this.sourceUrl, this.progressBar, this.progressText, this.doneCheck, this.callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
        this.doneCheck.invalidate();
        Handler transistion = new Handler();
        transistion.postDelayed(new Runnable() {
            @Override
            public void run() {
                collapse(parentView);
            }
        }, 1000);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(200);
        a.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(a);
    }
}
