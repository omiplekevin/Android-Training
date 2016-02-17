package com.omiplekevin.snackbarexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by OMIPLEKEVIN on 26/11/2015.
 */
public class ListViewHolder extends RecyclerView.ViewHolder {

    protected TextView textView;

    public ListViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.textView);
    }
}
