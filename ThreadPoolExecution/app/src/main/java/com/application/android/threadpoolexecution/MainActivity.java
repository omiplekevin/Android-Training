package com.application.android.threadpoolexecution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initiateThreadExecution();

    }

    private void initiateThreadExecution(){
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                NUMBER_OF_CORES*2,
                NUMBER_OF_CORES*2,
                60L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()
        );
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(10000);
                        Log.e("THREAD", "EXEC ID: " + i + " slept for 10 Sec...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(3000);
                        Log.e("THREAD", "EXEC ID: " + i + " slept for 3 Sec...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(5000);
                        Log.e("THREAD", "EXEC ID: " + i + " slept for 5 Sec...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(1000);
                        Log.e("THREAD", "EXEC ID: " + i + " slept for 1 Sec...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        executor.execute(new Runnable() {
            public void run() {
                for (int i = 0; i < 20; i++) {
                    try {
                        Thread.sleep(700);
                        Log.e("THREAD", "EXEC ID: " + i + " slept for 0.7 Sec...");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
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
}
