package android.application.com.multipledownloaderlist;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    DownloadMonitorCallback downloadMonitorCallback;

    public static final String TAG = "CustomAdapter";

    public CustomAdapter(Context context, List<String> itemDownloadList, DownloadMonitorCallback downloadMonitorCallback) {
        this.context = context;
        this.itemDownloadList = itemDownloadList;
        this.downloadMonitorCallback = downloadMonitorCallback;
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
        final View fConvertView = convertView;
        final ViewHolder v = viewHolder;

        if (!v.hasDownloaded && !v.isDownloading) {
            v.isDownloading = true;
            /*new AsyncThreadPoolDownloader(context, itemDownloadList.get(position),
                    v.filename,
                    v.fileDownloadProgress,
                    v.textProgress,
                    v.doneStatus, new DownloadMonitorCallback() {
                @Override
                public void onDownloadItemComplete(String url) {
                    itemDownloadListMap.put(url, new AtomicBoolean(Boolean.TRUE));
                    Log.e("DOWNLOAD_ITEM_LIST", url + " " + itemDownloadListMap.get(url));
                    for (Map.Entry<String, AtomicBoolean> entry : itemDownloadListMap.entrySet()) {
                        if(!entry.getValue().get()){
                            break;
                        }
                        Log.e("DOWNLOAD_ITEM_LIST", "everything is done!, setting to GONE " + entry.getKey());
                        break;
                    }
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/
        }

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
}
