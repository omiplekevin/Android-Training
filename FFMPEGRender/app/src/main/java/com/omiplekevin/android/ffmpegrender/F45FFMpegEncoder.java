package com.omiplekevin.android.ffmpegrender;

import android.app.IntentService;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.FFmpegLoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by OMIPLEKEVIN on Aug 05, 005.
 */

public class F45FFMpegEncoder extends IntentService {

//    public static final String ARG_INTENT_CANVAS_DETAIL = "VIDEO_MAP";
//    public static final String FIELD_CANVAS_SIZE = "CANVAS_SIZE";
//    public static final String FIELD_VIDEO_SCALE = "VIDEO_SCALE";
//    public static final String FIELD_VIDEO_POS_ARR = "VIDEO_X_Y";


    private static volatile F45FFMpegEncoder instance;
    private static F45FFMpegEncoderListener listener;
    private FFmpeg ffmpegInstance;

    public static F45FFMpegEncoder getInstance(F45FFMpegEncoderListener listener){
        if (instance == null) {
            synchronized (F45FFMpegEncoder.class) {
                if (instance == null) {
                    instance = new F45FFMpegEncoder("F45FFMpegEncoder");
                    F45FFMpegEncoder.listener = listener;
                }
            }
        }

        return instance;
    }

    public F45FFMpegEncoder(){
        super("F45FFMpegEncoder");
    }

    public F45FFMpegEncoder(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String parentDir = Environment.getExternalStorageDirectory() + "/F45/videos/";
//        HashMap<String, String> entries = (HashMap<String, String>) intent.getSerializableExtra("");
        ffmpegInstance = FFmpeg.getInstance(this);
        try {
            ffmpegInstance.loadBinary(new FFmpegLoadBinaryResponseHandler() {
                @Override
                public void onFailure() {
                    if (listener != null) {
                        listener.onEncodeInitFailure("Encoder initialization error");
                    }
                }

                @Override
                public void onSuccess() {
                }

                @Override
                public void onStart() {
                    if (listener != null) {
                        listener.onEncodeInitStart();
                    }
                }

                @Override
                public void onFinish() {
                    if (listener != null) {
                        listener.onEncodeInitFinish("Encoder initialization finished");
                    }
                }
            });
        } catch (FFmpegNotSupportedException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onEncodeFailure("Encoder initialization error");
            }
        }

        String[] command;
//        String cmd = "-i " + parentDir + "test1.mp4 -i " + parentDir + "test2.mp4 -i " + parentDir + "test3.mp4 -i " + parentDir + "test4.mp4 -i " + parentDir + "test5.mp4 -i " + parentDir + "test6.mp4 -i " + parentDir + "test7.mp4 -i " + parentDir + "test8.mp4 -i " + parentDir + "test9.mp4  -map \"[video]\" " + parentDir + "output.mp4 -filter_complex";
//        String filter_complex = "\'nullsrc=size=720x1280[base];[0:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v1];[1:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v2];[2:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v3];[3:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v4];[4:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v5];[5:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v6];[6:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v7];[7:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v8];[8:v]setpts=PTS-STARTPTS,scale=(iw/2)-10:-1[v9];[base][v1]overlay=shortest=1:x=20:y=244[t1];[t1][v2]overlay=shortest=1:x=360:y=244[t2];[t2][v3]overlay=shortest=1:x=20:y=435[t3];[t3][v4]overlay=shortest=1:x=360:y=435[t4];[t4][v5]overlay=shortest=1:x=20:y=626[t5];[t5][v6]overlay=shortest=1:x=360:y=626[t6];[t6][v7]overlay=shortest=1:x=20:y=817[t7];[t7][v8]overlay=shortest=1:x=360:y=817[t8];[t8][v9]overlay=shortest=1:x=20:y=1008[video]\'";
//        String cmd = "-y -i " + parentDir + "test1.mp4 " + parentDir + "test2.mp4";
//        String cmd = "-filter_complex \"nullsrc=size=720x1280[base];\"";
        /*command = new String[]{"-y", "-i",parentDir +"test1.mp4",
                "-i", parentDir +"test2.mp4",
                "-i", parentDir +"test3.mp4",
                "-i", parentDir +"test4.mp4",
                *//*"-i", parentDir +"test5.mp4",
                "-i", parentDir +"test6.mp4",
                "-i", parentDir +"test7.mp4",
                "-i", parentDir +"test8.mp4",
                "-i", parentDir +"test9.mp4",*//*
                "-filter_complex",
//                "nullsrc=size=720x1280 [base];[0:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v1];[1:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v2];[2:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v3];[3:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v4];[4:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v5];[5:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v6];[6:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v7];[7:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v8];[8:v] setpts=PTS-STARTPTS, scale=(iw/2)-10:-1 [v9];[base][v1] overlay=shortest=1:x=20:y=244 [t1];[t1][v2] overlay=shortest=1:x=360:y=244 [t2];[t2][v3] overlay=shortest=1:x=20:y=435 [t3];[t3][v4] overlay=shortest=1:x=360:y=435 [t4];[t4][v5] overlay=shortest=1:x=20:y=626 [t5];[t5][v6] overlay=shortest=1:x=360:y=626 [t6];[t6][v7] overlay=shortest=1:x=20:y=817 [t7];[t7][v8] overlay=shortest=1:x=360:y=817 [t8];[t8][v9] overlay=shortest=1:x=20:y=1008 [video]","-map","[video]", parentDir +"output.mp4"};
                "color=0x1f2142:size=594x680 [base];" +
                        "[0:v] setpts=PTS-STARTPTS, scale=333x225 [v1];" +
                        "[1:v] setpts=PTS-STARTPTS, scale=333x225 [v2];" +
                        "[2:v] setpts=PTS-STARTPTS, scale=333x225 [v3];" +
                        "[3:v] setpts=PTS-STARTPTS, scale=333x225 [v4];" +
                        "[base][v1] overlay=shortest=1:x=26:y=180 [t1];" +
                        "[t1][v2] overlay=shortest=1:x=361:y=180 [t2];" +
                        "[t2][v3] overlay=shortest=1:x=26:y=477 [t3];" +
                        "[t3][v4] overlay=shortest=1:x=361:y=477 [video]","-map","[video]", parentDir +"output3.mp4"};*/

        command = new String[] {
                "-y",
                "-i",
                parentDir + "F45LogoFinal.mp4",
                "-i",
                parentDir + "F45LogoFinal.mp4",
                "-i",
                parentDir + "F45LogoFinal.mp4",
                "-filter_complex",
                "color=0xFF00FF:size=1080x1491 [base];[0:v] setpts=PTS-STARTPTS, scale=766x474 [v1];[1:v] setpts=PTS-STARTPTS, scale=766x474 [v2];[2:v] setpts=PTS-STARTPTS, scale=766x474 [v3];[base][v1] overlay=shortest=1:x=284:y=33 [t1];[t1][v2] overlay=shortest=1:x=284:y=510 [t2];[t2][v3] overlay=shortest=1:x=284:y=987 [video]",
                "-crf",
                "23",
                "-map",
                "[video]",
                parentDir + "output3.mp4"
        };
//        command = cmd.split(" ");
//        command[command.length - 1] = command[command.length - 1] + " " + filter_complex;
        try {
            ffmpegInstance.execute(command, new ExecuteBinaryResponseHandler(){
                @Override
                public void onSuccess(String message) {
                    super.onSuccess(message);
                    Log.v(getClass().getName(), "onSuccess" + message);
                    if (listener != null) {
                        listener.onEncodeSuccess("Encoder initialization success");
                    }
                }

                @Override
                public void onProgress(String message) {
                    super.onProgress(message);
                    Log.v(getClass().getName(), "onProgress" + message);
                    if (listener != null) {
                        listener.onEncodeProgress(message);
                        getPid();
                    }
                }

                @Override
                public void onFailure(String message) {
                    super.onFailure(message);
                    Log.v(getClass().getName(), "onFailure" + message);
                    if (listener != null) {
                        listener.onEncodeFailure(message);
                    }
                }

                @Override
                public void onStart() {
                    super.onStart();
                    Log.v(getClass().getName(), "onStart");
                    if (listener != null) {
                        listener.onEncodeStart();
                    }
                }

                @Override
                public void onFinish() {
                    super.onFinish();
                    Log.v(getClass().getName(), "onFinish");
                    if (listener != null) {
                        listener.onEncodeFinish("encode finished...");
                    }
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            e.printStackTrace();
            if (listener != null) {
                listener.onEncodeFailure(e.getMessage());
            }
        }
    }

    private int getPid(){
        try {
            Process p = Runtime.getRuntime().exec("ps");
            p.waitFor();
            StringBuffer sb = new StringBuffer();
            InputStreamReader isr = new InputStreamReader(p.getInputStream());
            int ch;
            char [] buf = new char[1024];
            while((ch = isr.read(buf)) != -1)
            {
                sb.append(buf, 0, ch);
            }
            String[] outputs = sb.toString().split("\\n");
            for (String ln : outputs) {
                if (ln.contains("files/ffmpeg")) {
//                    Log.e("ffmpegShellExec", ln);
//                    Log.v("ffmpegShellExec", Arrays.deepToString(ln.split("[\\s]+")));
                    Log.v("ffmpegShellExec", ln.split("[\\s]+")[1]);
                }
            }
        /*for(String line : processLinesAr)
        {
            String [] comps = line.split("[\\s]+");
            if(comps.length != 9)
                return;
            //...
        }*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public interface F45FFMpegEncoderListener{
        void onEncodeSuccess(String message);
        void onEncodeProgress(String message);
        void onEncodeFailure(String message);
        void onEncodeStart();
        void onEncodeFinish(String message);

        void onEncodeInitStart();
        void onEncodeInitFinish(String message);
        void onEncodeInitFailure(String message);
    }
}
