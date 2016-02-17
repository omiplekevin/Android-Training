package com.omiplekevin.snackbarexample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by OMIPLEKEVIN on 26/11/2015.
 */
public class ListViewRecyclerAdapter extends RecyclerView.Adapter<ListViewHolder>{

    private List<String> entries;

    public ListViewRecyclerAdapter(List<String> entries) {
        this.entries = entries;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        holder.textView.setText(this.entries.get(position));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }
}
