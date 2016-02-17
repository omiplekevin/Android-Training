package com.example.omiplekevin.f45tvcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    Button startBtn, pauseBtn, stopBtn, ffBtn;
    EditText ipAddr, ffValue;
    boolean isPaused = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = (Button) findViewById(R.id.btnStart);
        pauseBtn = (Button) findViewById(R.id.btnPause);
        stopBtn = (Button) findViewById(R.id.btnStop);
        ffBtn = (Button) findViewById(R.id.ffBtn);

        ipAddr = (EditText) findViewById(R.id.ipAddr);
        ffValue = (EditText) findViewById(R.id.ffValue);
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

    public void start(View v){
        if (!ipAddr.getText().toString().isEmpty()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendHttpRequest("http://" + ipAddr.getText() + ":12345/?message=start", false, null, "GET", null);
                }
            }).start();
        }
    }

    public void pause(View v){
        if (!isPaused) {
            isPaused = true;
            pauseBtn.setText("Resume");
            if (!ipAddr.getText().toString().isEmpty()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendHttpRequest("http://" + ipAddr.getText() + ":12345/?message=pause", false, null, "GET", null);
                    }
                }).start();
            }
        } else {
            isPaused = false;
            pauseBtn.setText("Pause");
            if (!ipAddr.getText().toString().isEmpty()){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendHttpRequest("http://" + ipAddr.getText() + ":12345/?message=resume", false, null, "GET", null);
                    }
                }).start();
            }
        }
    }

    public void stop(View view) {
        if (!ipAddr.getText().toString().isEmpty()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendHttpRequest("http://" + ipAddr.getText() + ":12345/?message=stop", false, null, "GET", null);
                }
            }).start();
        }
    }

    public void ff(View view) {
        if (!ffValue.getText().toString().isEmpty() && !ipAddr.getText().toString().isEmpty()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendHttpRequest("http://" + ipAddr.getText() + ":12345/?message=" + ffValue.getText().toString(), false, null, "GET", null);
                }
            }).start();
        }
    }

    public static String sendHttpRequest(String sourceUrl, boolean enableCacheConfig, String fileName, String method, TreeMap<String, String> map){
        StringBuilder responseString = new StringBuilder();
        Log.e("CONTROLLER", "sending: " + sourceUrl);
        try {
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            //throws IOException
            HttpURLConnection httpUrlConnection = (HttpURLConnection)urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setConnectTimeout(10000);

            if (method.equals("POST")){
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");
                OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                writer.write(params.substring(0, params.length()-1));
                writer.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null){
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return responseString.toString();
    }
}
