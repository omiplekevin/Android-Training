package com.omiplekevin.autoupdateapktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    private AutoUpdateApk autoUpdateApk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoUpdateApk = new AutoUpdateApk(this, "http://192.168.1.11/f45/post_sample.php");
        autoUpdateApk.addObserver(this);
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

    @Override
    public void update(Observable observable, Object data) {
        if( ((String)data).equalsIgnoreCase(AutoUpdateApk.AUTOUPDATE_GOT_UPDATE) ) {
            android.util.Log.i("AutoUpdateApkActivity", "Have just received update!");
        }
        if( ((String)data).equalsIgnoreCase(AutoUpdateApk.AUTOUPDATE_HAVE_UPDATE) ) {
            android.util.Log.i("AutoUpdateApkActivity", "There's an update available!");
        }
    }
}
