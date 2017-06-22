package com.omiplekevin.android.f45testbench.downloadmanagerpro.demo;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omiplekevin.android.f45testbench.ADMDemo;
import com.omiplekevin.android.f45testbench.R;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.TaskStates;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadEvent;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ConcurrentHashMap;

/**
 * DEVELOPER:       OMIPLEKEVIN<br/>
 * LAST MODIFIED:   June 12, 2017<br/>
 * IN CLASS:        com.omiplekevin.android.f45testbench.downloadmanagerpro.fragment_demo<br/>
 * <br/>
 * //todo insert definition here...
 * <br/>
 **/
public class FragmentOne extends Fragment {

    public static final String TAG = "FragmentOne";
    private LinearLayout rootView;
    private DownloadManager downloadManager;
    private ConcurrentHashMap<Integer, ViewHolder> taskIdView = new ConcurrentHashMap<>();

    public static FragmentOne newInstance() {
        Bundle args = new Bundle();
        FragmentOne fragment = new FragmentOne();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadManager = ((ADMDemo) getActivity()).getDownloadManager();
        Log.w(TAG, "onCreate: downloadManager: " + downloadManager);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        rootView = (LinearLayout) view.findViewById(R.id.rootView);
        return view;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDownloadMessageEvent(DownloadEvent payload) {
        Log.d("FragmentOne", "onDownloadMessageEvent " + payload.filename);
        switch (payload.state) {
            case TaskStates.INIT:
                Log.d("FragmentOne", "onDownloadMessageEvent TASK STATE: INIT");
                break;
            case TaskStates.READY:
                Log.d("FragmentOne", "onDownloadMessageEvent TASK STATE: READY");
                break;
            case TaskStates.DOWNLOADING:
                //add view here...
                Log.d("FragmentOne", "onDownloadMessageEvent content: " + payload.toString());
                if (taskIdView != null) {
                    ViewHolder viewHolder = taskIdView.get((int) payload.taskId);
                    if (viewHolder == null) {
                        Log.d("FragmentOne", "onDownloadMessageEvent adding view: " + payload.taskId);
                        viewHolder = generateDownloadView(payload.filename);
                        taskIdView.put((int) payload.taskId, viewHolder);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        viewHolder.parentView.setLayoutParams(params);
                        viewHolder.labelTextView.setText(payload.filename);
                        viewHolder.labelTextView.setTextColor(Color.parseColor("#FFFFFF"));
                        viewHolder.progressBar.setProgress((int) payload.percentage);
                        rootView.addView(viewHolder.parentView);
                    } else {
                        Log.d("FragmentOne", "onDownloadMessageEvent updating progress: " + payload.percentage);
                        viewHolder.labelTextView.setText(payload.filename);
                        viewHolder.progressBar.setIndeterminate(false);
                        viewHolder.progressBar.setProgress((int) payload.percentage);
                    }
                } else {
                    Log.d("FragmentOne", "onDownloadMessageEvent taskIDView is null!");
                }
                break;
            case TaskStates.PAUSED:
                Log.d("FragmentOne", "onDownloadMessageEvent TASK STATE: PAUSED");
                if (taskIdView != null) {
                    ViewHolder viewHolder = taskIdView.get((int) payload.taskId);
                    if (viewHolder != null) {
                        String labelText = viewHolder.labelTextView.getText().toString();
                        viewHolder.labelTextView.setText(labelText + " (retrying in " + (payload.countdown % 3 == 0 ? "..." : (payload.countdown % 2 == 0 ? ".." : ".")));
                    }
                }
//                downloadManager.getDownloadManagerPro().dispose();
                //manipulate view that it is paused
                break;
            case TaskStates.DOWNLOAD_FINISHED:
                Log.d("FragmentOne", "onDownloadMessageEvent TASK STATE: DOWNLOAD_FINISHED");
                //remove view from the list
                if (taskIdView != null) {
                    ViewHolder viewHolder = taskIdView.get((int) payload.taskId);
                    if (viewHolder != null) {
                        viewHolder.parentView.setVisibility(View.GONE);
                        if (downloadManager != null) {
//                            if (downloadManager.getDownloadManagerPro().delete((int) payload.taskId, false)) {
//                                Log.d("FragmentOne", "onDownloadMessageEvent deleted entry " + payload.taskId + " from database");
//                            }
                        }
                    }
                }
                break;
            case TaskStates.END:
                Log.d("FragmentOne", "onDownloadMessageEvent TASK STATE: END");
                break;
        }
    }

    private ViewHolder generateDownloadView(String nameLabel) {
        Log.d("FragmentOne", "generateDownloadView generating download view...");
        ViewHolder viewHolder = new ViewHolder();
        //this the parent view
        LinearLayout dlParentView = new LinearLayout(getActivity());
        LinearLayout.LayoutParams parentParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        parentParams.setMargins(10, 10, 10, 10);
        dlParentView.setOrientation(LinearLayout.VERTICAL);

        //this is PBs and Label - add more if want - OR create method that will make use of a layout template
        LinearLayout.LayoutParams childParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        parentParams.setMargins(5, 5, 5, 5);

        //progress bar
        ProgressBar progressBar = new ProgressBar(getActivity(), null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setLayoutParams(childParams);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setIndeterminate(true);

        //label text
        TextView labelText = new TextView(getActivity());
        labelText.setLayoutParams(childParams);
        labelText.setText(nameLabel);

        //now add this to the childViewParent
        dlParentView.addView(progressBar);
        dlParentView.addView(labelText);
        viewHolder.parentView = dlParentView;
        viewHolder.progressBar = progressBar;
        viewHolder.labelTextView = labelText;

        return viewHolder;
    }

    /*public FragmentOne setUpdateListener(Subject subject) {
        this.subject = subject;
        this.subject.attach(this);
        return this;
    }

    @Override
    public void downloadState() {

    }*/

    private class ViewHolder {
        public LinearLayout parentView;
        public ProgressBar progressBar;
        public TextView labelTextView;
    }

}
