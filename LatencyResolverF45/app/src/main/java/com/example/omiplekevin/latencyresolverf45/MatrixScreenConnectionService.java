package com.example.omiplekevin.latencyresolverf45;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.Date;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MatrixScreenConnectionService extends Service {

    JSONObject ipList;
    boolean START = false;
    static ConcurrentHashMap<Integer, String> ipMapList;
    boolean TIMER_ACTIVE = false;
    long[] packetlog = new long[60];
    long[] pinglog = new long[60];
    private static final String TAG = "MSC";
    public static MSCConnection mscConnection;

    public MatrixScreenConnectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ipMapList = new ConcurrentHashMap<>();

        mscConnection = new MSCConnection() {
            @Override
            public void onRequestTime() {
                requestTimeUDP();
            }

            @Override
            public void onResponseTime() {

            }
        };
        listeninigUDP();
        if(!(MainActivity.SCREEN_TYPE == 0)){
            requestTimeUDP();
        }
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        START = false;
        super.onDestroy();
    }

    /*public void broadcastTimeUDP(final String message) {
        for (Map.Entry<Integer, String> ipMapEntry : ipMapList.entrySet()) {
            final Map.Entry<Integer, String> ipMap = ipMapEntry;
            Runnable run = new Runnable() {

                @Override
                public void run() {
                    try {
                        DatagramSocket clientSocket = new DatagramSocket();
                        InetAddress IPAddress = InetAddress.getByName(ipMap
                                .getValue());
                        byte[] sendData = new byte[1024];
                        byte[] receiveData = new byte[1024];
                        String sentence = message;
                        sendData = sentence.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                sendData, sendData.length, IPAddress, 9876);
                        //send packet to recipient
                        clientSocket.send(sendPacket);
                        clientSocket.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            };

            Thread sendThread = new Thread(run);
            sendThread.start();
        }
    }*/

    public void requestTimeUDP(){
        Log.e(TAG, "request");
        Runnable run = new Runnable() {

            @Override
            public void run() {
                try {
                    Calendar cal = Calendar.getInstance();
                    int second = cal.get(Calendar.SECOND);
                    packetlog[second] = System.currentTimeMillis();

                    String message = String.valueOf(second);
                    DatagramSocket clientSocket = new DatagramSocket();
                    //send out request to timer screen
                    InetAddress IPAddress = InetAddress.getByName("192.168.2.6");
                    byte[] sendData = new byte[1024];
                    byte[] receiveData = new byte[1024];
                    String sentence = message;
                    Log.e(TAG, "request " + message);
                    sendData = sentence.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(
                            sendData, sendData.length, IPAddress, 9876);
                    //send packet to recipient
                    clientSocket.send(sendPacket);
                    DatagramPacket receivePacket = new DatagramPacket(
                            receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    String modifiedSentence = new String(
                            receivePacket.getData());
                    Log.e(TAG, modifiedSentence.trim() + "");
                    MainActivity.timerUpdate.onTimeUpdateReceived(modifiedSentence.trim());
                    clientSocket.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        };

        Thread sendThread = new Thread(run);
        sendThread.start();
    }

    public void listeninigUDP() {
        Log.e(TAG, "listeningUDP");
        Runnable listeningThread = new Runnable() {

            @Override
            public void run() {
                try {
                    DatagramSocket serverSocket = new DatagramSocket(9876);
                    byte[] receiveData = new byte[1024];
                    byte[] sendData = new byte[1024];
                    while (true) {
                        DatagramPacket receivePacket = new DatagramPacket(
                                receiveData, receiveData.length);
                        final DatagramPacket frp = receivePacket;
                        serverSocket.receive(receivePacket);
                        final String sentence = new String(receiveData, 0,
                                frp.getLength());

                        if(MainActivity.SCREEN_TYPE == 0){
                            Log.e(TAG, "listeningUDP");
                            String response = "";
                            response = response + sentence; //initual request number
                            response = response + "|" + MainActivity.getCurrentTime(); // Send back current timer position

                            // Then send whatever other variables you want
                            response = response + "|" + "XYZ"; //confighash - should be cached dont do often cause of CPU cycles
                            response = response + "|" + "10-10-20-1"; //timerconfig - should be cached dont do often cause of CPU cycles
                            try {
                                DatagramSocket clientSocket = new DatagramSocket();
                                // Reply to same IP that just messaged you
                                InetAddress IPAddress = receivePacket.getAddress();
                                int port = receivePacket.getPort();
                                sendData = response.getBytes();
                                DatagramPacket sendPacket = new DatagramPacket(
                                        sendData, sendData.length, IPAddress, port);
                                serverSocket.send(sendPacket);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }else{
                            String[] res = sentence.split("|");
                            Log.e(TAG, sentence);
                            // Get the latency
                            long totaltime = System.currentTimeMillis() - packetlog[Integer.valueOf(res[0])];

                            double minicounterworkout;

                            double average = 0;
                            int reallength = 0;
                            for (int i = 0; i < pinglog.length; i++) {
                                if(pinglog[i] > 0)
                                {
                                    reallength++;
                                    average = average + pinglog[i];
                                }
                            };
                            average = average / reallength;

                            double dif = Math.abs(totaltime - average) / average;
                            //double dif = Math.abs(totaltime - averageLatency) / averageLatency;

                            if (dif > .10) //This is the number we play with
                            {
                                //ignore this message, the difference is too much

                            } else {
                                minicounterworkout = Float.valueOf(res[1]) + (average / 1000 / 2) + .02;//account for the transfer time + processing time
                                // Probably something like 47
                            }
                        }
                    }
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                }
            }
        };
        Thread t = new Thread(listeningThread);
        t.setPriority(Thread.MAX_PRIORITY);
        t.start();
    }

    public interface MSCConnection{
        void onRequestTime();

        void onResponseTime();
    }
}
