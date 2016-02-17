package com.omiplekevin.android.alarmmanagerscheduling;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AlarmManagerBroadcastReceiver alarmManagerBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManagerBroadcastReceiver = new AlarmManagerBroadcastReceiver();
        alarmManagerBroadcastReceiver.setAlarmReceiverCallback(new AlarmManagerBroadcastReceiver.AlarmReceiverCallback() {
            @Override
            public void onReceiveAlarmTask(String message) {
                Toast.makeText(MainActivity.this, "" + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void scheduleTime(View view) {
        if (alarmManagerBroadcastReceiver != null) {
            Log.e("MAINACTIVITY", "Click!");
            alarmManagerBroadcastReceiver.setAlarm(this, 10000);
        }
    }
}
