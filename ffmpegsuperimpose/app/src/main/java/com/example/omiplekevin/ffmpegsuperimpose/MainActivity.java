package com.example.omiplekevin.ffmpegsuperimpose;

import android.media.MediaMetadataRetriever;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.netcompss.ffmpeg4android.CommandValidationException;
import com.netcompss.ffmpeg4android.GeneralUtils;
import com.netcompss.loader.LoadJNI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String _DIR_MAIN = Environment.getExternalStorageDirectory() + "/F45";
    private static final String _DIR_TEMP = _DIR_MAIN + "/.temp";
    private static final String _CACHE_COMPILATION_RENDER_RANDOM = "7a3b5f4b721930778aa471bc016bc2fa_9961.mp4";
    private static final String _CACHE_COMPILATION_RENDER = "result.mp4";
    private static final String _DIR_FFMPEG_RENDERS = _DIR_MAIN + "/renders";


    public static LoadJNI vk;
    private HashMap<String, Object> videoDetails;


    private static final String ARG_INTENT_CANVAS_DETAIL = "VIDEO_MAP";
    private static final String FIELD_CANVAS_SIZE = "CANVAS_SIZE";
    private static final String FIELD_VIDEO_SCALE = "VIDEO_SCALE";
    private static final String FIELD_VIDEO_POS_ARR = "VIDEO_X_Y";
    private static final String FIELD_VIDEO_COUNT = "VIDEO_COUNT";
    private static final String FIELD_VIDEO_MAX_TIME = "MIN_TIME";
    private static final int MINIMUM_VIDEO_LENGTH = 10 * 1000;
    private static final long MINIMUM_VIDEO_SIZE = 800L * 1000L;
    private static final int MAX_RENDER_ATTEMPTS = 4;
    private int renderAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoDetails = new HashMap<>();
        HashMap<String, String> coord = new HashMap<>();
        coord.put("Video1", "x=22:y=234|/mnt/internal_sd/F45/videos/F45-Sledgehammer-Overhead-Diagonal-Woodchop-3443-912.mp4");
        coord.put("Video2", "x=362:y=234|/mnt/internal_sd/F45/videos/F45-Mb-Russian-Twists-3619-720.mp4");
        coord.put("Video3", "x=22:y=541|/mnt/internal_sd/F45/videos/Smartstep-back Foot Shuffle-2665.mp4");
        coord.put("Video4", "x=362:y=541|/mnt/internal_sd/F45/videos/F45-Side-Burpees-3502-898.mp4");

        videoDetails.put("MIN_TIME", 18875);
        videoDetails.put("VIDEO_X_Y", coord);
        videoDetails.put("VIDEO_COUNT", "4");
        videoDetails.put("VIDEO_SCALE", "336x189");
        videoDetails.put("CANVAS_SIZE", "720x994");

        String[] command = {"ffmpeg","-y"
                ,"-i" ,"/mnt/internal_sd/F45/videos/F45-Sledgehammer-Overhead-Diagonal-Woodchop-3443-912.mp4"
                ,"-i" ,"/mnt/internal_sd/F45/videos/F45-Mb-Russian-Twists-3619-720.mp4"
                ,"-i" ,"/mnt/internal_sd/F45/videos/Smartstep-back Foot Shuffle-2665.mp4"
                ,"-i" ,"/mnt/internal_sd/F45/videos/F45-Side-Burpees-3502-898.mp4"
                ,"-filter_complex" ,"color=0x1f2142:size=720x994 [base];[0:v] setpts=PTS-STARTPTS, scale=336x189 [v1];[1:v] setpts=PTS-STARTPTS, scale=336x189 [v2];[2:v] setpts=PTS-STARTPTS, scale=336x189 [v3];[3:v] setpts=PTS-STARTPTS, scale=336x189 [v4];[base][v1] overlay=shortest=1:x=22:y=234 [t1];[t1][v2] overlay=shortest=1:x=362:y=234 [t2];[t2][v3] overlay=shortest=1:x=22:y=541 [t3];[t3][v4] overlay=shortest=1:x=362:y=541 [video]"
                ,"-t" ,"18" ,"-ab" ,"128kb" ,"-vcodec" ,"mpeg4" ,"-b" ,"1200k" ,"-mbd" ,"2" ,"-cmp" ,"2" ,"-subcmp" ,"2" ,"-map" ,"[video]" ,"/mnt/internal_sd/F45/.temp/_7a3b5f4b721930778aa471bc016bc2fa_9961.mp4"};
        startEncode(18875, command);
    }

    private class NativeRenderAsync extends AsyncTask<String[], Void, Void> {

        LoadJNI vk;
        int longestVideoLength = 0;
        String[] videoList = null;

        public NativeRenderAsync(LoadJNI vk, String vkLogPath, final int longestVideoLength, final String[] videoList) {
            this.vk = vk;
            this.longestVideoLength = longestVideoLength;
            this.videoList = videoList;
        }

        @Override
        protected void onPreExecute() {
            Log.i("NativeRenderAsync", "onPreExecute");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String[]... params) {
            GeneralUtils.checkForPermissionsMAndAbove(MainActivity.this, true);
            vk = new LoadJNI();
            HashMap<String, String> demuxedVideos = createDemuxedFile(vk, longestVideoLength, videoList);
            Log.e(TAG, "doInBackground demuxedVideo" + demuxedVideos.toString());
            try {
                //map struct:
                //(KEY)original file, (VALUE)demuxfile
                performRendering(demuxedVideos, params);
            } catch (Throwable e) {
                Log.e("ffmpeg4android", "vk run exception.", e);
                File vkLogFile = new File(_DIR_MAIN + "/vk.log");
                File ffmpegLicenseFile = new File(_DIR_MAIN + "/ffmpeglicense.lic");
                if (vkLogFile.delete()) {
                    Log.w("ffmpeg4android", "deleting vk.log");
                } else {
                    Log.e("ffmpeg4android", "unable to delete vk.log file");
                }

                if (ffmpegLicenseFile.delete()) {
                    Log.w("ffmpeg4android", "deleting ffmpeglicense.lic");
                } else {
                    Log.e("ffmpeg4android", "unable to delete ffmpeglicense.lic file");
                }
                try {
                    performRendering(demuxedVideos, params);
                } catch (CommandValidationException ex) {
                    ex.printStackTrace();
                    Log.wtf(TAG, "fatal, can't render video - please manually remove vk.log and ffmpeglicense.lic files from " + _DIR_MAIN);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.i("NativeRenderAsync", "onPostExecute");
        }

        private void performRendering(HashMap<String, String> demuxedVideos, String[]... params) throws CommandValidationException {
            //get commands from passed primitive array
            /*String[] complexCommand = params[0];
            if (demuxedVideos.size() > 0) {
                for (Map.Entry<String, String> entry : demuxedVideos.entrySet()) {
                    for (int i = 0; i < complexCommand.length; i++) {
                        Log.d(TAG, "performRendering " + entry.getValue() + " vs " + complexCommand[i]);
                        if (entry.getKey().equals(complexCommand[i])) {
                            Log.w(TAG, "performRendering: replacing " + complexCommand[i] + " with " + entry.getValue());
                            complexCommand[i] = entry.getValue();
                        }
                    }
                }
            }
            long renderTimeLookout = System.currentTimeMillis();
            if (MainActivity.this == null) {
                throw new CommandValidationException();
            }*/
//            String[] coms = {"ffmpeg", "-y", "-i", _DIR_MAIN + "/videos/Seal Jack 2.mp4", "-vf", "unsharp=9:9:1.5:9:9:1.5", "-b:v", "65536k", "-minrate", "65536k", _DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM};
            String[] coms = {"ffmpeg", "-y", "-i", _DIR_MAIN + "/videos/Seal Jack 2.mp4", "-vcodec", "mpeg4", "-b:v", "2600k", "-b:a", "128k", "-f", "mp4", _DIR_MAIN + "/videos/test.mp4"};
            Log.i("ffmpeg4android", "FIRST PASS START");
            vk.run(coms, _DIR_MAIN, MainActivity.this);
            Log.i("NativeRenderAsync", "ffmpeg4android finished successfully");
            /*String[] coms = {"ffmpeg", "-y", "-i", _DIR_TEMP + "/_" + _CACHE_COMPILATION_RENDER_RANDOM*//*"0.mp4"*//*, "-vf", "unsharp=9:9:1.5:9:9:1.5", "-b:v", "65536k", "-minrate", "65536k", _DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM};

            *//*Log.i("ffmpeg4android", "clear down, deleting demuxed videos...");
            for (Map.Entry<String, String> entry : demuxedVideos.entrySet()) {
                boolean confirmDelete = new File(entry.getValue()).delete();
                if (confirmDelete) {
                    Log.i("ffmpeg4android", "deleted: " + entry.getValue());
                }
            }*//*

            Log.i("ffmpeg4android", "SECOND PASS START");
            vk.run(coms, _DIR_MAIN, MainActivity.this);
            if ((System.currentTimeMillis() - renderTimeLookout) <= 1000) {
                //impossible! throw exception
                throw new CommandValidationException();
            }
            Log.i("ffmpeg-native", "ffmpeg4android SECOND PASS finished successfully");

            if (new File(_DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM).exists()) {
                Log.v("ffmpeg-native", "" + _DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM + " exist!");
                transferFile(_DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM,
                        _DIR_FFMPEG_RENDERS + "/" + _CACHE_COMPILATION_RENDER,
                        "MOVE");
            } else {
                Log.i("ffmpeg-native", _DIR_TEMP + "/" + _CACHE_COMPILATION_RENDER_RANDOM + " does NOT exist!");
            }*/
        }

        /**
         * creates demuxed files for short video to be repeated
         *
         * @param longestVideoLength longest video on the videos on the timeline
         * @param videoList          contains video that needs to be demuxed
         * @return
         */
        private HashMap<String, String> createDemuxedFile(LoadJNI vk, int longestVideoLength, String[] videoList) {
            HashMap<String, String> demuxedVideos = new HashMap<>();
            for (int i = 0; i < videoList.length; i++) {
                String filePath = (videoList[i].split("\\|"))[1];
                int length = getVideoFileLength(new File(filePath));
                if (length > 0 && length < longestVideoLength) {
                    Log.w(TAG, "createDemuxedFile " + filePath + " with " + length + " SEC long, limit: " + longestVideoLength);
                    //lets create demuxe script for videos
                    String content = createDemuxContent(filePath, length, longestVideoLength);
                    //create just the file name
                    String fname = createMd5Hash(filePath);
                    //create hash and make it as file name for script
                    String filename = fname + ".txt";
                    //write to file
                    writeToCacheFile(content,
                            filename,
                            false);
                    //create demuxed video
                    transferFile(_DIR_MAIN + "/" + filename, _DIR_TEMP + "/" + filename, "MOVE");
                    String[] demuxCmd = new String[]{"ffmpeg", "-y", "-f", "concat", "-i", _DIR_TEMP + "/" + filename, "-c", "copy", _DIR_TEMP + "/" + fname + ".mp4"};
                    Log.w(TAG, "createDemuxedFile: " + Arrays.deepToString(demuxCmd));
                    try {
                        vk.run(demuxCmd, _DIR_MAIN, MainActivity.this);
                    } catch (CommandValidationException e) {
                        e.printStackTrace();
                    }
                    demuxedVideos.put(filePath, _DIR_TEMP + "/" + fname + ".mp4");
                } else {
                    Log.w(TAG, "NOT createDemuxedFile " + filePath + " with " + length + "SEC long, limit: " + longestVideoLength);
                }
            }
            Log.w(TAG, "createDemuxedFile " + demuxedVideos.toString());
            return demuxedVideos;
        }

        private String createDemuxContent(String videoPath, int length, int longestVideoLength) {
            String content = "file '" + videoPath + "'";
            do {
                if (length > longestVideoLength) {
                    break;
                }
                length += length;
                content += "\r\n";
                Log.d(TAG, "createDemuxContent: writing content: " + content);
                content += content;
            } while (true);
            Log.w(TAG, "createDemuxContent: demux content: " + content);
            return content;
        }
    }

    private int getVideoFileLength(File sourceFile) {
        if (sourceFile.exists()) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(sourceFile.getAbsolutePath());
            return Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
        } else {
            return 0;
        }
    }

    private long getVideoFileSize(File sourceFile) {
        return sourceFile.length();
    }

    /**
     * transfer file to and from the specified path
     *
     * @param inputPath  from / source path (eg: <code>inputpath + "/" + fileName</code> )
     * @param outputPath to / target path (eg: <code>outputPath + "/" + fileName</code> )
     * @param method     <b>MOVE</b> or <b>COPYGIFWITHOUTDASH</b>
     */
    public static void transferFile(String inputPath, String outputPath, String method) {
        try {
            // delete file from the temp_dir to avoid duplicate
            if (method.toUpperCase().equals("MOVE")) {
                FileInputStream inStream = new FileInputStream(inputPath);
                FileOutputStream outStream = new FileOutputStream(outputPath);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();

                File delFile = new File(inputPath);
                if (delFile.delete()) {
                    Log.i(TAG, "======= MOVED to " + outputPath + " =======");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will write passed content to specified file and requires append modes<br/><br/>
     * <i><b>Append modes</b></i><br/>
     * <code>true</code> content will be appended to the existing file, <code>false</code> otherwise
     *
     * @param content      content when writing <code>String</code> to the file
     * @param filename     filename of the target file
     * @param enableAppend append mode, <code>true</code> or <code>false</code>
     * @return <code>true</code> if cache was written successfully, <code>false</code> otherwise
     */
    public static boolean writeToCacheFile(String content, String filename, boolean enableAppend) {
        try {
            if (enableAppend) {
                String cacheFileContent = readCacheFile(_DIR_MAIN + "/" + filename);
                Log.i(TAG, cacheFileContent);
                File cacheFile = new File(_DIR_MAIN + "/" + filename);
                if (TextUtils.isEmpty(cacheFileContent)) {
                    if (!cacheFile.isFile() || !cacheFile.exists()) {
                        if (cacheFile.createNewFile()) {
                            Log.i(TAG, "created new file " + filename);
                        }
                    }
                } else {
                    content = "||" + content;
                }

                FileOutputStream fileOutputStream = new FileOutputStream(cacheFile, true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                outputStreamWriter.append(content);
                outputStreamWriter.close();
                fileOutputStream.close();

                Log.i(TAG, "CONTENT: " + readCacheFile(_DIR_MAIN + "/" + filename));
                return true;
            } else {
                File cacheFile = new File(_DIR_MAIN + "/" + filename);
                if (cacheFile.delete()) {
                    Log.i(TAG, "DELETE " + filename + " file");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();

                Log.i(TAG, "CONTENT: " + readCacheFile(_DIR_MAIN + "/" + filename));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * read cache / precache file from storage directory
     *
     * @param pathToFile source file of cached document
     * @return <b>String</b> content of the cache file
     */
    public static String readCacheFile(String pathToFile) {
        StringBuilder stringBuilder = new StringBuilder();
        File cacheFile = new File(pathToFile);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(cacheFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
            stringBuilder = new StringBuilder("");
        }

        return stringBuilder.toString();

    }

    public static final String createMd5Hash(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * this is feakin' long for a code
     */
    public void startEncode(int longestVideoLength, String[] command) {
        File checkFile = new File(_DIR_FFMPEG_RENDERS + "/" + _CACHE_COMPILATION_RENDER);

        int checkFileLength = 0;
        long checkFileSize = 0;
        if (checkFile.isFile()) {
            try {
                checkFileLength = getVideoFileLength(checkFile);
                checkFileSize = getVideoFileSize(checkFile);
                Log.e(getClass().getSimpleName(), "video time " + _DIR_FFMPEG_RENDERS + "/" + _CACHE_COMPILATION_RENDER + " = " + checkFileLength);
            } catch (IllegalStateException e) {
                e.printStackTrace();
                checkFile = null;
            }
        } else {
            Log.w(TAG, "is not file: " + _DIR_FFMPEG_RENDERS + "/" + _CACHE_COMPILATION_RENDER);
        }

        if (renderAttempts >= MAX_RENDER_ATTEMPTS) {
            if (checkFile != null && checkFile.isFile()) {
                boolean removedInvalidFile = checkFile.delete();
                Log.e(TAG, "remove invalid/attempt file: " + removedInvalidFile);
            }
            Log.e(getClass().getSimpleName(), "rollback to original videos");
        } else if (checkFile != null && (checkFile.isFile() || checkFile.exists()) && checkFileLength >= MINIMUM_VIDEO_LENGTH && checkFileSize > MINIMUM_VIDEO_SIZE) {
            renderAttempts = 0;
            Log.v(getClass().getSimpleName(), "reset renderAttempts: " + renderAttempts);
        } else {
            GeneralUtils.checkForPermissionsMAndAbove(MainActivity.this, true);
            vk = new LoadJNI();

            File preRenderedFile = new File(_DIR_FFMPEG_RENDERS + "/" + _CACHE_COMPILATION_RENDER);
            if (!preRenderedFile.isFile() || checkFileLength < longestVideoLength || checkFileLength < MINIMUM_VIDEO_LENGTH) {
                HashMap<String, String> videoMap = (HashMap<String, String>) videoDetails.get(FIELD_VIDEO_POS_ARR);
                String[] videoList = new String[videoMap.size()];
                //copy to array the list of videos
                for (int x = 0; x < videoMap.size(); x++) {
                    videoList[x] = videoMap.get("Video" + String.valueOf(x + 1));
                }

                NativeRenderAsync nativeRenderAsync = new NativeRenderAsync(
                        vk,
                        _DIR_MAIN + "/vk.log",
                        longestVideoLength, videoList);
                nativeRenderAsync.execute(command);
                renderAttempts++;
                Log.w(getClass().getSimpleName(), "renderAttempts: " + renderAttempts);
            } else {
                renderAttempts = 0;
                Log.v(getClass().getSimpleName(), "reset renderAttempts: " + renderAttempts);
            }
        }
    }
}
