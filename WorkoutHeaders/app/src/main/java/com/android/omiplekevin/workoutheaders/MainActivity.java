package com.android.omiplekevin.workoutheaders;

import android.os.Binder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.omiplekevin.workoutheaders.adapter.WorkoutAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    //VIEWS
    @BindView(R.id.workoutRecyclerView) RecyclerView workoutRecyclerView;

    //ADAPTERS

    //LAYOUT MANAGER
    private RecyclerView.LayoutManager workoutLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        //initialize layout manager
        workoutLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
