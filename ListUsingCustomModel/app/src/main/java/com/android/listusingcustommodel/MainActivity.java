package com.android.listusingcustommodel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        List<VideoMetaData> videoMetaDataList = new ArrayList<>();

        for (int i=0;i<10;i++){
            VideoMetaData data;
            if (i == 0){
                data = new VideoMetaData();
                String fileName = "ASUS_compressed.mp4";
                data.setFileName(fileName);
                data.setFilePath(Environment.getExternalStorageDirectory() + "/Download/" + fileName);
            } else {
                data  = new VideoMetaData();
                String fileName = "ENTRY " + i + ".mp4";
                data.setFileName(fileName);
                data.setFilePath(Environment.getExternalStorageDirectory() + "/Download/" + fileName);
            }
            videoMetaDataList.add(data);
        }

        final ListViewAdapter adapter = new ListViewAdapter(this, videoMetaDataList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VideoPlayer videoPlayer = new VideoPlayer();
                Log.e("TAG", adapter.getItem(position).getFilePath());
                Intent intent = new Intent(getApplicationContext(), videoPlayer.getClass());
                intent.putExtra("VIDEO_PATH", adapter.getItem(position).getFilePath());
                startActivity(intent);
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
