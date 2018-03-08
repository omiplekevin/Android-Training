package com.android.omiplekevin.workoutheaders.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import com.android.omiplekevin.workoutheaders.R;
import com.android.omiplekevin.workoutheaders.helpers.Constants;
import com.android.omiplekevin.workoutheaders.model.timeline.TimelineModel;
import com.android.omiplekevin.workoutheaders.network.RequestService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by OMIPLEKEVIN on 12/11/2015.
 */
public class Utilities {

    private static final String TAG = "Utilities";

    public static void deleteSubFolders(String uri) {
        File currentFolder = new File(uri);
        File files[] = currentFolder.listFiles();

        if (files == null) {
            return;
        }
        for (File f : files) {
            if (f.isDirectory()) {
                deleteSubFolders(f.toString());
            }
            f.delete();
        }
    }

    /**
     * Request Configuration from <i>configuration source</i>
     *
     * @param sourceUrl                 source url where we can get our configuration
     * @param enableCacheConfig         <code>true</code> if caching is enabled, <code>false</code> otherwise
     * @param fileName                  cache filename
     * @param method                    POST/GET
     * @param map                       Parameters for the POST method
     * @param rememberCookiesForSession if cookies will be remembered
     * @return <b>responseString</b> result of request
     */
    public static String sendHttpRequest(String sourceUrl, boolean enableCacheConfig, String fileName, String method, TreeMap<String, String> map, boolean rememberCookiesForSession) {
        StringBuilder responseString = new StringBuilder();
        Log.e(TAG, "SEND HTTP REQUEST ================ START\n" +
                "SOURCEURL: " + sourceUrl + "\n" +
                "ENABLECACHECONFIG: " + enableCacheConfig + "\n" +
                "FILENAME: " + fileName + "\n" +
                "METHOD: " + method + "\n" +
                "PARAMS: " + map + "\n" +
                "REMEMBERCOOKIESFORSESSION: " + rememberCookiesForSession + "\n" +
                "SEND HTTP REQUEST ================ END");
        try {
            //cookie for current url request
            String cookies = CookieManager.getInstance().getCookie(sourceUrl);
            //throws MalformedURLException
            URL urlSource = new URL(sourceUrl);
            //throws IOException
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlSource.openConnection();
            httpUrlConnection.setRequestMethod(method.toUpperCase(Locale.getDefault()));
            httpUrlConnection.setConnectTimeout(10000);

            if (method.equals("GET")) {
                if (map != null) {
                    {
                        for (Map.Entry<String, String> entry : map.entrySet()) {
                            httpUrlConnection.addRequestProperty(entry.getKey(), entry.getValue());
                        }
                    }
                }
            }

            if (rememberCookiesForSession) {
                if (cookies != null) {
                    httpUrlConnection.setRequestProperty("Cookie", cookies);
                }
            }

            if (method.equals("POST")) {
                StringBuilder params = new StringBuilder();

                httpUrlConnection.setDoOutput(true);
                httpUrlConnection.setInstanceFollowRedirects(false);
                httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                httpUrlConnection.setRequestProperty("charset", "utf-8");

                if (map != null) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                    }

                    OutputStreamWriter writer = new OutputStreamWriter(httpUrlConnection.getOutputStream());
                    writer.write(params.substring(0, params.length() - 1));
                    writer.flush();
                }
            }

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                responseString.append(line);
            }

            httpUrlConnection.getInputStream().close();

            if (enableCacheConfig && fileName != null) {
                writeToCacheFile(responseString.toString(), fileName, false);
            }
        } catch (Exception e) {
//            Log.e("Utilities", "Exception @ Utilities | " + sourceUrl);
            e.printStackTrace();
        }

        Log.d(TAG, "RESPONSE: " + responseString.toString());
        return responseString.toString();
    }

    //      Parse json data and save as LinkedHashMap to display in FMRadio
   /* public static LinkedHashMap<String, List<AudioData>> parseFmRadioMusicList(String jsonResult) {

        List<AudioData> audioDataLists=null;
        LinkedHashMap<String, List<AudioData>> linkedHashMap = new LinkedHashMap<>();

        try {
            JSONObject jsonObject = new JSONObject(jsonResult).getJSONObject("f45fm");
            Iterator<String> stringIterator = jsonObject.keys();

            while (stringIterator.hasNext()) {
                String dateValue = stringIterator.next();
                Log.d(TAG, "parseFmRadioMusicList: " + dateValue);
                JSONArray arr=  jsonObject.getJSONArray(dateValue);
                Log.d(TAG, "parseFmRadioMusicList: json array size: "+arr.length());

                for (int i=0; i< arr.length(); i++) {
                    audioDataLists= new ArrayList<>();
                    AudioData audioData= new AudioData();
                    JSONObject obj= arr.getJSONObject(i);

                    audioData.cover= obj.getString("cover");
                    audioData.link= obj.getString("link");
                    audioData.music= obj.getString("music");
                    audioData.musicDuration= obj.getString("duration");
                    audioData.workout= obj.getString("workout");

                    audioDataLists.add(audioData);
                }


                *//*  JSONObject innerObj = jsonObject.getJSONObject(value);
                Iterator<String> innerKeyIterator = innerObj.keys();
                while (innerKeyIterator.hasNext()) {
                    String innerKey = stringIterator.next();
                    String value1= innerObj.getString(innerKey);
                    Log.d(TAG, "parseFmRadioMusicList: key = "+value+" value = "+value1);
                }*//*

                linkedHashMap.put(dateValue,audioDataLists);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return linkedHashMap;
    }*/


   /* //   Parse json data and save as LinkedHashMap to display in FMRadio
    public static FMRadioModel parseJsonToFmRadioModel(String json) {
        JSONObject secondLvl;
        FMRadioModel model = new FMRadioModel();
        LinkedHashMap<String, List<PlaylistContent>> stringListLinkedHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, Playlist> playlistLinkedHashmap = new LinkedHashMap<>();

        try {

            secondLvl = new JSONObject(json).getJSONObject("data");
            Iterator<String> iterator = secondLvl.keys();
            while (iterator.hasNext()) {

                String value = iterator.next();
                Log.d(TAG, "parseJsonToFmRadioModel: " + value);
                JSONObject thirdLvl = secondLvl.getJSONObject(value);
                Playlist playlist = new Playlist();
                playlist.cover = thirdLvl.getString("cover");
                playlist.description = thirdLvl.getString("description");
                playlist.djName = thirdLvl.getString("dj_name");
                playlist.lastUpload = thirdLvl.getString("last_upload");
                playlist.name = thirdLvl.getString("name");
                playlist.workOut = thirdLvl.getString("workout");
                playlist.stringListLinkedHashMap = stringListLinkedHashMap;

                playlistLinkedHashmap.put(value, playlist);     // ("Aspirationnal" , Playlist Object)

                JSONObject fourthLvl = thirdLvl.getJSONObject("playlist");
                Iterator<String> fourthIterator = fourthLvl.keys();

                while (fourthIterator.hasNext()) {

                    List<PlaylistContent> playlistContentList = new ArrayList<>();
                    String datelinkedhashmapValue = fourthIterator.next();
                    Log.d(TAG, "parseJsonToFmRadioModel: datelinkedhashmapValue " + datelinkedhashmapValue);
                    JSONArray fifthLvlJsonArr = fourthLvl.getJSONArray(datelinkedhashmapValue);

                    for (int i = 0; i < fifthLvlJsonArr.length(); i++) {
                        PlaylistContent playlistContent = new PlaylistContent();

                        JSONObject fifthJsonObject = fifthLvlJsonArr.getJSONObject(i);
                        playlistContent.date = fifthJsonObject.getString("date");
                        playlistContent.dateCreated = fifthJsonObject.getString("date_created");
                        playlistContent.category = fifthJsonObject.getString("category");
                        playlistContent.music = fifthJsonObject.getString("music");
                        playlistContent.link = fifthJsonObject.getString("link");
                        playlistContent.playlistContentCover = fifthJsonObject.getString("cover");
                        playlistContent.coverName = fifthJsonObject.getString("cover_name");
                        playlistContent.playlistContentDescription = fifthJsonObject.getString("description");
                        playlistContent.playlistContentWorkout = fifthJsonObject.getString("workout");
                        playlistContent.playlistContentDuration = fifthJsonObject.getString("duration");
                        playlistContent.playlistContentDjName = fifthJsonObject.getString("dj_name");
                        playlistContentList.add(playlistContent);

                        // add to list
                        stringListLinkedHashMap.put(datelinkedhashmapValue, playlistContentList);
                    }
                }

            }

            model.data = playlistLinkedHashmap;
            model.success = new JSONObject(json).getBoolean("success");
            model.operation = new JSONObject(json).getString("operation");
            model.errors = new JSONObject(json).getString("errors");
            model.userData = new JSONObject(json).getString("userData");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return model;
    }*/


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

        Log.e(TAG, "read cache: " + stringBuilder.toString());

        return stringBuilder.toString();

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
                String cacheFileContent = readCacheFile(Constants.DIR_MAIN + "/" + filename);
                Log.e(TAG, cacheFileContent);
                File cacheFile = new File(Constants.DIR_MAIN + "/" + filename);
                if (TextUtils.isEmpty(cacheFileContent)) {
                    if (!cacheFile.isFile() || !cacheFile.exists()) {
                        if (cacheFile.createNewFile()) {
                            Log.e(TAG, "created new file " + filename);
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

                Log.e(TAG, "CONTENT: " + readCacheFile(Constants.DIR_MAIN + "/" + filename));
                return true;
            } else {
                File cacheFile = new File(Constants.DIR_MAIN + "/" + filename);
                if (cacheFile.delete()) {
                    Log.e(TAG, "DELETE " + filename + " file");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(cacheFile);
                fileOutputStream.write(content.getBytes());
                fileOutputStream.close();

                Log.e(TAG, "CONTENT: " + readCacheFile(Constants.DIR_MAIN + "/" + filename));
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * determines the type of connection
     *
     * @param context the application context
     * @return <b>String</b> the connection type
     */
    public static String getConnectionType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null) {
            return ni.getTypeName();
        }

        return null;
    }

    public static void postLogs(final String message, String from) {
        Log.e("POST_LOGS", message + " " + from);
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    TreeMap<String, String> map = new TreeMap<>();
                    map.put("gym_id", Constants.USER_ACCOUNT_MODEL.gymAccessCode);
                    map.put("message", message);
                    map.put("screen", "Username: " + Constants.USER_ACCOUNT_MODEL.name); //instead of screen id, username, since it's not per screen activity;
                    map.put("sent_at", (System.currentTimeMillis() / 1000) + "");

                    String result = sendHttpRequest(Constants._TIMELOG, false, null, "POST", map, false);
//                    Log.e(TAG, "result : " + result);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File getCacheFolder(Context context) {
        File cacheDir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
            if (!cacheDir.isDirectory()) {
                cacheDir.mkdirs();
            }
        }

        if (!cacheDir.isDirectory()) {
            cacheDir = context.getCacheDir(); //get system cache folder
        }

        return cacheDir;
    }

    public static String[] parseAndGetProgramInfo(String playbookPrograms) {
        String[] programInfo = null;
        try {
            programInfo = new String[3];
            JSONObject data = new JSONObject(playbookPrograms);
            JSONObject workoutAsset = data.getJSONObject("data").getJSONObject("workout_video_assets");
            JSONArray jsonArray = workoutAsset.getJSONArray("sizes");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if (obj.getString("width").equals("640")) {
                    programInfo[0] = obj.getString("link");
                    break;
                }
            }

            programInfo[1] = data.getJSONObject("data").getString("workout_video_url");
            programInfo[2] = data.getJSONObject("data").getString("movement_image_filename");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return programInfo;
    }

    public static String getLogoPath(String workout) {
        String file = "";
        if (Constants.TIMELINE_MODEL.json.workout_info != null) {
            for (TimelineModel.Json.WorkOutInfo info : Constants.TIMELINE_MODEL.json.workout_info) {
                if (info.name.equalsIgnoreCase(workout)) {
                    file = info.logo.large;
                    break;
                }
            }
        } else {
            Log.e(TAG, "Constants.TIMELINE_MODEL.json.workout_info is null.");
        }

        return file;
    }

    public static String getHeaderTagFromCall(Call<ResponseBody> call, String headerField) {
        if (call != null) {
            String tagContent = call.request().header(headerField);
            if (tagContent == null || tagContent.isEmpty()) {
                Log.w(TAG, "getHeaderTagFromCall: Unknown header '" + tagContent + "'");
                return "";
            } else {
                return call.request().header(headerField);
            }
        } else {
            Log.w(TAG, "getHeaderTagFromCall: call is " + call);
            return "";
        }
    }

    /**
     * get current config session information by day
     *
     * @param playbookTimeline source timeline
     * @return jsonObject of selected day
     */
    public static JSONObject getWeekdayConfigInformation(Context context, JSONObject playbookTimeline, int weekday) {
        String[] days = context.getResources().getStringArray(R.array.playbook_week);
        String currDay = days[weekday - 1];
        Log.d(TAG, "getWeekdayConfigInformation: weekday[" + weekday + "] " + currDay);
        try {
            JSONObject data = playbookTimeline.getJSONObject("data").getJSONObject("weeklyTimeline").getJSONObject(currDay);
            Log.d(TAG, "REQUEST: current day timeline info: " + data.toString());
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}