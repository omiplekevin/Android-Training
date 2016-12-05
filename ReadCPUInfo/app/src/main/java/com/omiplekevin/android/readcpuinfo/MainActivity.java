package com.omiplekevin.android.readcpuinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ScheduledFuture<?> scheduledFuture;
    ScheduledExecutorService counter;
    TextView cpuInfoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        counter = Executors.newSingleThreadScheduledExecutor();
        cpuInfoTextView = (TextView) findViewById(R.id.cpuInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        scheduledFuture = counter.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Random rand = new Random();
                        float result = (float)System.currentTimeMillis() / rand.nextFloat();
                        result = result / rand.nextFloat();
                        result = result / rand.nextFloat();
                        cpuInfoTextView.setText(getBogoMipsFromCpuInfo() + "\nDoing random stuff: " + result + "\n\n" + readCPUinfo());
                    }
                });
            }
        }, 100, 100, TimeUnit.MILLISECONDS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        scheduledFuture.cancel(true);
    }

    public static String getBogoMipsFromCpuInfo(){
        String result = "";
        String cpuInfo = readCPUinfo();
        String[] cpuInfoArray =cpuInfo.split(":");
        for( int i = 0 ; i< cpuInfoArray.length;i++){
            if(cpuInfoArray[i].contains("processor")){
                String res = cpuInfoArray[i+1];
                String[] pureCpuClock = res.split("\\n");
                if (pureCpuClock.length > 1) {
                    result = result + "proc" + pureCpuClock[0];
                }
            }

            if(cpuInfoArray[i].contains("BogoMIPS")){
                String res = cpuInfoArray[i+1];
                String[] pureCpuClock = res.split("\\n");
                if (pureCpuClock.length > 1) {
                    result = result + ":" + pureCpuClock[0] + ", ";
                }
            }
        }
        if(result.equals("")) {
            result = result.trim();
        }
        return result;
    }

    public static String readCPUinfo()
    {
        ProcessBuilder cmd;
        String result="";
        InputStream in = null;
        try{
            String[] args = {"/proc/cpuinfo"};
            cmd = new ProcessBuilder(args);
            Process process = cmd.start();
            in = process.getInputStream();
            byte[] re = new byte[1024];
            while(in.read(re) != -1){
                System.out.println(new String(re));
                result = result + new String(re);
            }
        } catch(IOException ex){
            ex.printStackTrace();
        } finally {
            try {
                if(in !=null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
