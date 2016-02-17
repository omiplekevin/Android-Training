package android.application.com.multipledownloaderlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by OMIPLEKEVIN on Jan 19, 2016.
 */
public class CustomAdapter extends BaseAdapter{

    Context context;
    List<String> itemDownloadList;
    ConcurrentHashMap<String, AtomicBoolean> itemDownloadListMap;

    public static final String TAG = "CustomAdapter";

    public CustomAdapter(Context context, List<String> itemDownloadList) {
        this.context = context;
        this.itemDownloadList = itemDownloadList;
        itemDownloadListMap = new ConcurrentHashMap<>();
        for (String itemUrl : itemDownloadList) {
            itemDownloadListMap.put(itemUrl, new AtomicBoolean(Boolean.FALSE));
        }

    }

    @Override
    public int getCount() {
        return this.itemDownloadList.size();
    }

    @Override
    public String getItem(int position) {
        return this.itemDownloadList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_item_downloaditem_view, parent, false);
            convertView.setHasTransientState(true);
            viewHolder.filename = (TextView) convertView.findViewById(R.id.fileName);
            viewHolder.fileDownloadProgress = (ProgressBar) convertView.findViewById(R.id.fileDownloadProgress);
            viewHolder.doneStatus = (ImageView) convertView.findViewById(R.id.doneImage);
            viewHolder.textProgress = (TextView) convertView.findViewById(R.id.progressText);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final String fileName = this.itemDownloadList.get(position).substring(this.itemDownloadList.get(position).lastIndexOf("/") + 1, this.itemDownloadList.get(position).length());
        Log.e("filename", fileName);
        viewHolder.filename.setText(fileName);
        viewHolder.fileDownloadProgress.setMax(0);
        viewHolder.fileDownloadProgress.setProgress(0);
        final ViewHolder v = viewHolder;

        /*if (!v.hasDownloaded && !v.isDownloading) {
            v.isDownloading = true;
            new VideoDownloadTask(itemDownloadList.get(position),
                    v.fileDownloadProgress,
                    v.textProgress,
                    v.doneStatus, new DownloadMonitorCallback() {
                @Override
                public void onDownloadItemComplete(String url) {
                    itemDownloadListMap.put(url, new AtomicBoolean(Boolean.TRUE));
//                    Log.e("DOWNLOAD_ITEM_LIST", url + " " + itemDownloadListMap.get(url));
                    for (Map.Entry<String, AtomicBoolean> entry : itemDownloadListMap.entrySet()) {
                        if(!entry.getValue().get()){
                            break;
                        }
                        Log.e("DOWNLOAD_ITEM_LIST", "everything is done!");
                        break;
                    }
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }*/

        return convertView;
    }

    interface DownloadMonitorCallback {
        void onDownloadItemComplete(String url);
    }

    class ViewHolder {
        TextView filename;
        TextView textProgress;
        ProgressBar fileDownloadProgress;
        ImageView doneStatus;
        boolean hasDownloaded = false;
        boolean isDownloading = false;
    }

    class VideoDownloadTask extends AsyncTask<Void, Integer, Boolean> {

        String sourceUrl;
        int contentLength, currentPercentage;

        ProgressBar progressBar;
        TextView progressText;
        ImageView doneCheck;

        DownloadMonitorCallback callback;

        public VideoDownloadTask(String sourceUrl, ProgressBar progressBar, TextView progressText, ImageView doneCheck, DownloadMonitorCallback callback) {
            this.sourceUrl = sourceUrl;

            this.progressBar = progressBar;
            this.progressText = progressText;
            this.doneCheck = doneCheck;
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            final String outputDir = Environment.getExternalStorageDirectory() + "/F45";
            final String fileName = this.sourceUrl.substring(this.sourceUrl.lastIndexOf("/") + 1, this.sourceUrl.length());
            try {
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
                this.progressText.setVisibility(View.INVISIBLE);
                this.doneCheck.setVisibility(View.VISIBLE);
                this.callback.onDownloadItemComplete(this.sourceUrl);
            } else {
                new VideoDownloadTask(this.sourceUrl, this.progressBar, this.progressText, this.doneCheck, this.callback).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }
}
