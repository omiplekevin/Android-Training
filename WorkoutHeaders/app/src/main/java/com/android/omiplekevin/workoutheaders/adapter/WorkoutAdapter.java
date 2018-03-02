package com.android.omiplekevin.workoutheaders.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.omiplekevin.workoutheaders.model.timeline.WorkoutAll;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by OMIPLEKEVIN on February 28, 2018.
 * WorkoutHeaders
 * com.android.omiplekevin.workoutheaders.adapter
 */

@RequiredArgsConstructor(staticName = "initialize")
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    @Getter
    private final Context context;
    @Getter
    private final List<WorkoutAll.Data> workoutDataList;

    public class WorkoutViewHolder extends RecyclerView.ViewHolder {

        public WorkoutViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getWorkoutDataList().size();
    }
}
