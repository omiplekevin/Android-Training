package com.example.omiplekevin.ndudemoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by OMIPLEKEVIN on 11/12/2015.
 *
 */
public class ItemListAdapter extends BaseAdapter {

    private Context context;
    private List<String> items;

    public ItemListAdapter(Context context, List<String> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return this.items.size();
    }

    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView text = (TextView) LayoutInflater.from(this.context).inflate(R.layout.listview_item, null);
        text.setText(this.items.get(position));

        return text;
    }


}
