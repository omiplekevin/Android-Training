package com.android.project.jobschedulertest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.project.jobschedulertest.jobs.DownloadJob;
import com.evernote.android.job.JobManager;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;

public class MainActivity extends AppCompatActivity {

    FirebaseJobDispatcher dispatcher;
    int jobid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.startJob)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jobid = DownloadJob.scheduleJob();
                Log.d("MainActivity", "onClick just ran job " + jobid);
            }
        });

        ((Button)findViewById(R.id.cancelJobs)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobManager.instance().cancel(jobid);
                Log.d("MainActivity", "onClick cancel " + jobid);
            }
        });

    }
}
