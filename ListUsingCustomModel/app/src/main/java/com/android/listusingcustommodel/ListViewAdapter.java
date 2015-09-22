package com.android.listusingcustommodel;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OMIPLEKEVIN on 17/09/2015.
 */
public class ListViewAdapter extends BaseAdapter {

    Context context;
    List<VideoMetaData> videoEntryList;

    public ListViewAdapter(Context context, List<VideoMetaData> videoEntryList){
        this.context = context;
        this.videoEntryList = videoEntryList;
    }

    @Override
    public int getCount() {
        return videoEntryList.size();
    }

    @Override
    public VideoMetaData getItem(int position) {
        return videoEntryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = View.inflate(this.context,R.layout.list_item_entry, null);
            holder.videoTitle = (TextView) convertView.findViewById(R.id.videoTitle);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.videoTitle.setText(videoEntryList.get(position).getFileName());
        return convertView;
    }

    static class ViewHolder{
        TextView videoTitle;
    }
}
