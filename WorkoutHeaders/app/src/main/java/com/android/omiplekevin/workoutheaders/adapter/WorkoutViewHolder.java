package com.android.omiplekevin.workoutheaders.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.omiplekevin.workoutheaders.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by OMIPLEKEVIN on March 02, 2018.
 * WorkoutHeaders
 * com.android.omiplekevin.workoutheaders.adapter
 */

public class WorkoutViewHolder extends RecyclerView.ViewHolder {

    /**
     * VIEWS
     */
    @BindView(R.id.workoutDate) public TextView workoutDate;
    @BindView(R.id.rawData) public TextView rawData;
    @BindView(R.id.workoutLogo) public ImageView workoutLogo;

    public WorkoutViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
