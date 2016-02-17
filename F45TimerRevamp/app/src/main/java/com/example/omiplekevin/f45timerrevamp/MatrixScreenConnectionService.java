package com.example.omiplekevin.f45timerrevamp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public class MatrixScreenConnectionService extends Service {
    boolean START = false;
    static ConcurrentHashMap<Integer, String> ipMapList;
    boolean TIMER_ACTIVE = false;
    long[] packetlog = new long[60];
    long[] pinglog = new long[60];
    private static final String TAG = "MSC";
    public static MatrixScreenResponseCallback matrixScreenResponseCallback;
    public static MatrixScreenRequestCallback matrixScreenRequestCallback;

    // TODO: 15/10/2015 this is temporary for now, we have to get the list from the server
    public static String[] ipList = {"192.168.2.25","192.168.2.24","192.168.2.71"};

    public MatrixScreenConnectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        ipMapList = new ConcurrentHashMap<>();

        // TODO: 08/10/2015 do callbacks for request here
        matrixScreenRequestCallback = new MatrixScreenRequestCallback() {
            @Override
            public void onRequest() {
//                requestTimeUDP();
            }

            @Override
            public void onBroadcast(String message) {
                Log.e(TAG + "BROADCAST", "broadcastTime " + message);
                broadcastMessageUDP(message);
            }
        };
        listeningUDP();
       /* if(!(Constants._SCREEN_ID.equals("4"))){
            requestTimeUDP();
        }*/
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

    public void broadcastMessageUDP(final String message){
        Thread broadcastThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i=0;i<ipList.length;i++) {
                        Log.e(TAG, "sending to: " + ipList[i]);
                        Calendar cal = Calendar.getInstance();
                        int second = cal.get(Calendar.SECOND);
                        packetlog[second] = System.currentTimeMillis();
                        DatagramSocket clientSocket = new DatagramSocket();
                        //send out request to timer screen
                        InetAddress IPAddress = InetAddress.getByName(ipList[i]);
                        byte[] sendData = new byte[1024];
                        byte[] receiveData = new byte[1024];
                        String sentence = message;
                        sendData = sentence.getBytes();
                        DatagramPacket sendPacket = new DatagramPacket(
                                sendData, sendData.length, IPAddress, 9876);
                        Log.e(TAG + "send", sendPacket.getAddress() + " " + sendPacket.getPort());
                        clientSocket.send(sendPacket);
                        clientSocket.close();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        });
        broadcastThread.start();
    }

    public void listeningUDP() {
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
                        Log.e(TAG + "LISTENING", sentence + "");
                        if(Constants._SCREEN_ID.equals("4")){
//                            Log.e(TAG, "response for workout test"+ sentence);
                            String response = "";
                            response = response + sentence; //initual request number
                            // TODO: 08/10/2015 get current time of the ticker
                            response = response + "|" + MainActivity.getTime(); // Send back current timer position

                            // Then send whatever other variables you want
                            response = response + "|" + "XYZ"; //confighash - should be cached dont do often cause of CPU cycles
                            response = response + "|" + "10-10-20-1"; //timerconfig - should be cached dont do often cause of CPU cycles
                            try {
                                //DatagramSocket clientSocket = new DatagramSocket();
                                // Reply to same IP that just messaged you
                                if (!Constants._SCREEN_ID.equalsIgnoreCase("4")) {
                                    InetAddress IPAddress = receivePacket.getAddress();
                                    int port = receivePacket.getPort();
                                    sendData = response.getBytes();
                                    DatagramPacket sendPacket = new DatagramPacket(
                                            sendData, sendData.length, IPAddress, 9876);
                                    serverSocket.send(sendPacket);
                                }
//                                Log.e(TAG, "response for workoutasdf test" + response+" at ip "+receivePacket.getAddress()+" at port" + port + " length " + sendData.length);
                            } catch (IOException ioe) {
                                ioe.printStackTrace();
                            }
                        }else{
                            //Log.e(TAG, "response from timer");
                            String[] res = sentence.split("\\|");
                            Log.e(TAG, sentence);
                            // Get the latency
                            long totaltime = System.currentTimeMillis() - packetlog[Integer.parseInt(res[0])];
                            pinglog[Integer.parseInt(res[0])] = totaltime;

                            double minicounterworkout;

                            double average = 0;
                            int reallength = 0;
                            for (int i = 0; i < pinglog.length; i++) {
                                if( pinglog[i] > 0)
                                {
                                    reallength++;
                                    average = average + pinglog[i];
                                }
                            };
                            if (reallength > 0) {
                                average = average / reallength;
                            }

                            double dif = Math.abs(totaltime - average) / average;
                            //double dif = Math.abs(totaltime - averageLatency) / averageLatency;

                            if (dif > 4 && reallength > 10) //This is the number we play with
                            {
                                //ignore this message, the difference is too much
//                                Log.e(TAG, "ignore: " + totaltime + "," + average + "," + dif);


                            } else {
                                minicounterworkout = Double.valueOf(res[1]) + (average / 1000 / 2) + .02;//account for the transfer time + processing time
//                                Log.e(TAG,"Counter: "+minicounterworkout);
                                // Probably something like 47
                                // TODO: 15/10/2015 we should bring this back to UI for visuals
//                                if(matrixScreenResponseCallback != null){
//                                    matrixScreenResponseCallback.onResponseReceived(minicounterworkout + "");
//                                }
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
}
