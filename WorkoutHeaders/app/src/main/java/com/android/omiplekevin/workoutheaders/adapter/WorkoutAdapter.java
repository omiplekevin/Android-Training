package com.android.omiplekevin.workoutheaders.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.omiplekevin.workoutheaders.R;
import com.android.omiplekevin.workoutheaders.model.timeline.WorkoutAll;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created by OMIPLEKEVIN on February 28, 2018.
 * WorkoutHeaders
 * com.android.omiplekevin.workoutheaders.adapter
 */

@RequiredArgsConstructor(staticName = "initialize")
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutViewHolder> {

    private static final String TAG = "WorkoutAdapter";

    @Getter
    private final Context context;
    @Getter
    private final List<WorkoutAll.Data> workoutDataList;

    @Override
    public WorkoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View workoutItemLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_workout_data_item, parent, false);
        return new WorkoutViewHolder(workoutItemLayout);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        WorkoutAll.Data data = workoutDataList.get(position);
        holder.workoutDate.setText(data.getFormatted_day_label());
        Picasso.with(context).load(Uri.parse(data.getWorkoutUrl())).resize(100, 100).into(holder.workoutLogo);
    }

    @Override
    public int getItemCount() {
        return getWorkoutDataList().size();
    }
}
