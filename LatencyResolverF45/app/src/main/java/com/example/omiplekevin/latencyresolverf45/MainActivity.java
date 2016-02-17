package com.example.omiplekevin.latencyresolverf45;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Currency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    //0 - timer
    //1 - workout
    public static final int SCREEN_TYPE = 1;
    public static int CURRENT_TICK = 0;
    public static TimerUpdate timerUpdate;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.timerValue);

        if(SCREEN_TYPE == 1){
            doPolling();
        } else {
            runTimer();
        }

        timerUpdate = new TimerUpdate() {
            @Override
            public void onTimeUpdateReceived(final String sentence) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*actualLastMessageTime = getCurrentTime();
                        lastMessageTime = actualLastMessageTime;
                        new android.os.Handler().postDelayed(
                                new Runnable() {
                                    public void run() {
                                        int curr = getCurrentTime();
                                        if (curr-actualLastMessageTime < 10000) {
                                            Log.i("tag", "This'll run 300 milliseconds later");
                                            if (getCurrentTime() - lastMessageTime > 950) {
                                                sentence = sentence + 1;
                                                textView.setText(sentence + "");
                                                lastMessageTime = getCurrentTime();
                                                // If you update, then you need to reset the timer

                                            }
                                        }else{
                                            // Maybe display error message
                                        }
                                    }
                                },
                                1000);*/

                        textView.setText(sentence + "");
                    }
                });
            }
        };

        MatrixScreenConnectionService matrixScreenConnectionService = new MatrixScreenConnectionService();
        Intent intent = new Intent(this, matrixScreenConnectionService.getClass());
        startService(intent);
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

    private void runTimer(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                CURRENT_TICK++;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(CURRENT_TICK + "");
                    }
                });
            }
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    private void doPolling(){
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                MatrixScreenConnectionService.mscConnection.onRequestTime();
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }

    public static int getCurrentTime(){
        return CURRENT_TICK;
    }

    public interface TimerUpdate{
        void onTimeUpdateReceived(String setence);
    }
}
