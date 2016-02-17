package com.example.omiplekevin.customprogressdialog;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

public class MainActivity extends AppCompatActivity {

    CustomProgressDialog customProgressDialog;
    String[] DUMMY_FILENAMES = {"This is a string that contains a very loooooooooooong phrase."
            , "Small Title"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        performThreading();
    }

    private void performThreading(){
        new AsyncTask<Void, Integer, Void>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                customProgressDialog = new CustomProgressDialog(MainActivity.this);
                customProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customProgressDialog.setContentView(R.layout.custom_progress_dialog);

                customProgressDialog.setCustomTitle(DUMMY_FILENAMES[0]);

                customProgressDialog.setPrimaryProgressMax(100);
                customProgressDialog.setPrimaryProgress(0);

                customProgressDialog.setSecondaryProgressMax(100000);
                customProgressDialog.setSecondaryProgress(0);

                customProgressDialog.show();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                if(values[2] == 1){
                    customProgressDialog.setPrimaryProgress(0);
                    if((values[0]/100)%2 == 0){
                        customProgressDialog.setCustomTitle(DUMMY_FILENAMES[0]);
                        customProgressDialog.setCurrentItem(DUMMY_FILENAMES[1]);
                    } else {
                        customProgressDialog.setCustomTitle(DUMMY_FILENAMES[1]);
                        customProgressDialog.setCurrentItem(DUMMY_FILENAMES[0]);
                    }
                    customProgressDialog.setOverAllItems((values[0]/100), 100000);
                } else if(values[2] == 0){
                    customProgressDialog.setPrimaryProgress(values[1]);
                    customProgressDialog.setSecondaryProgress(values[0]);
                }
            }

            @Override
            protected Void doInBackground(Void... params) {
                int perItem = 0;
                for (int i=0; i < 100000; i++){
                    try {
                        Thread.sleep(10);
                        if((i%100) == 0){
                            perItem = 0;
                            publishProgress(i, perItem, 1);
                        } else {
                            perItem += 1;
                            publishProgress(i, perItem, 0);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
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
