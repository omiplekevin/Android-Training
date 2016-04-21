package com.example.omiplekevin.f45tvcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Dashboard extends AppCompatActivity {


    EditText ipAddress2, customMessageCommand, forwardValueTxt;
    ImageButton bbCommand, playPauseStopCommand, ffCommand, sendCustomMessage;
    boolean isPaused2 = false;
    boolean stopped = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initializeVariables();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void initializeVariables(){

        ipAddress2 = (EditText) findViewById(R.id.ipAddress2);
        customMessageCommand = (EditText) findViewById(R.id.customMessageCommand);
        sendCustomMessage = (ImageButton) findViewById(R.id.sendCustomMessage);
        sendCustomMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageCommand();
            }
        });

        playPauseStopCommand = (ImageButton) findViewById(R.id.playPauseStopCommand);
        playPauseStopCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stopped){
                    start();
                    playPauseStopCommand.setImageResource(R.drawable.pause);
                    playPauseStopCommand.invalidate();
                } else {
                    if (!isPaused2) {
                        playPauseStopCommand.setImageResource(R.drawable.play);
                        playPauseStopCommand.invalidate();
                    } else {
                        playPauseStopCommand.setImageResource(R.drawable.pause);
                        playPauseStopCommand.invalidate();
                    }
                    pause();
                }
            }
        });

        playPauseStopCommand.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                playPauseStopCommand.setImageResource(R.drawable.play);
                playPauseStopCommand.invalidate();
                stop();

                return true;
            }
        });

        ffCommand = (ImageButton) findViewById(R.id.ffCommand);
        ffCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendffCommand();
            }
        });

        bbCommand = (ImageButton) findViewById(R.id.bbCommand);
        bbCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendbbCommand();
            }
        });

        forwardValueTxt = (EditText) findViewById(R.id.forwardValue);

    }

    public void start() {
        if (!ipAddress2.getText().toString().isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=start", false, null, "GET", null);
                }
            }).start();
        }
    }

    public void stop() {
        stopped = true;
        if (!ipAddress2.getText().toString().isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isPaused2 = false;
                    sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=stop", false, null, "GET", null);
                }
            }).start();
        }
    }

    public void pause() {
        if (!isPaused2) {
            isPaused2 = true;
            if (!ipAddress2.getText().toString().isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=pause", false, null, "GET", null);
                    }
                }).start();
            }
        } else {
            isPaused2 = false;
            if (!ipAddress2.getText().toString().isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=resume", false, null, "GET", null);
                    }
                }).start();
            }
        }
    }

    public void messageCommand() {
        if (!customMessageCommand.getText().toString().isEmpty() && !ipAddress2.getText().toString().isEmpty()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=" + customMessageCommand.getText().toString(), false, null, "GET", null);
                }
            }).start();
        }
    }

    public void sendffCommand(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=ff|" + forwardValueTxt.getText(), false, null, "GET", null);
            }
        }).start();
    }

    public void sendbbCommand(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendHttpRequest("http://" + ipAddress2.getText() + ":12345/?message=ff|-" + forwardValueTxt.getText(), false, null, "GET", null);
            }
        }).start();
    }
    public static String sendHttpRequest(String sourceUrl, boolean enableCacheConfig, String fileName, String method, TreeMap<String, String> map) {
        StringBuilder responseString = new StringBuilder();
        Log.e("CONTROLLER", "sending: " + sourceUrl);
        try {
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            //throws IOException
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setConnectTimeout(10000);

            if (method.equals("POST")) {
                StringBuilder params = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");
                OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                writer.write(params.substring(0, params.length() - 1));
                writer.flush();
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString.toString();
    }
}
