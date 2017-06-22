package android.application.com.multipledownloaderlist;

import android.app.Activity;
import android.application.com.multipledownloaderlist.uicomponent.ParallelDownloader;
import android.application.com.multipledownloaderlist.uicomponent.helpers.AsyncThreadPoolDownloader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity {

    List<String> url;
    LinearLayout parentLinearLayout;
    ParallelDownloader parallelDownloader;
    HashMap<String, Boolean> itemDownloadCompletionState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parallelDownloader = (ParallelDownloader) findViewById(R.id.parallelDownloader);
        itemDownloadCompletionState = new HashMap<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        url = new ArrayList<>();
        createDownloadList();

        parallelDownloader.setDownloadUrlList(url);
        parallelDownloader.startDownloadQueue();

        /*for (int i = 0; i < url.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.listview_item_downloaditem_view, null, false);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    50));
            TextView fileName = (TextView) view.findViewById(R.id.fileName);
            fileName.setText(Uri.parse(url.get(i)).getLastPathSegment());
            TextView progressText = (TextView) view.findViewById(R.id.progressText);
            ProgressBar fileDownloadProgress = (ProgressBar) view.findViewById(R.id.fileDownloadProgress);
            ImageView doneDownload = (ImageView) view.findViewById(R.id.doneImage);
            new AsyncThreadPoolDownloader(this,
                    url.get(i),
                    view,
                    fileName,
                    fileDownloadProgress,
                    progressText,
                    doneDownload).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            Log.d("Main", "creating DL view" + view + " for: " + url.get(i));
            parentLinearLayout.addView(view);
            earLayout.invalidate();
        }*/
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

    private void createDownloadList() {
        url.add("http://cdn.f45.info/F45LogoFinal.mp4");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/gravity.png");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/warm-up.png");
        url.add("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/The Attention Mover full Body Wake Up 3048.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/F45-Hi-Knee-Run-On-The-Spot-3254-661.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/N a plyolunge Ground Touch 3204.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Lower Ballistics shuffle And Aduction 3067.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Hamstring-Stretch-Hammi-Swings.mp4");
        url.add("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Pushups-and-Mountain-Climbers-Push-Up-Glute-Pyramid.mp4");
        url.add("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Floor Or Lumbarlying Hip Flexor Pulse 3073.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/High-Knee-Shuffle-Feet-In-Dif-Directions.mp4");
        url.add("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Explosive fast Feet Tempo 3084.mp4");
        url.add("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/F45-Tuck-Jumps-3528-948.mp4");
        url.add("http://f45tv.s3.amazonaws.com/others/hydrate.png");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/greatjob.png");
        url.add("http://f45tv.s3.amazonaws.com/challenge_ads/2017_CHAL_AD2.png");
        url.add("http://f45tv.s3.amazonaws.com/challenge_ads/2017_CHAL_AD1.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_athletica.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_hollywood.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_22.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_romans.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_wingman.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_brooklyn.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_panthers.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_pipeline.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_firestorm.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_3peat.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_angrybirds.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_renegades.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_docklands.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_templars.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_gravity.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_foxtrot_new.png");
        url.add("http://f45tv.s3.amazonaws.com/slideshow/logo_quarterbacks.png");
        url.add("http://matrix.f45.info/v1/djs/fm_download/aHR0cHM6Ly9mNDVmbS5zMy5hbWF6b25hd3MuY29tL21peGVzLzEyQkR6RzFQVHVHV2pqS3pXYjZKX01WUF9ESiBDaHJpcyBEb21pbmlja19NYXIgMSAyMDE3Lm1wMw==");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_stay_movement_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_hydrate_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_move_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_move_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_left_move_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_mid_move_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_right_move_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_2.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_horizontal.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_stay.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_v5.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_green_pod_single.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_group_pods.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_red_pod_single.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement_pulse.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_left_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_mid_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_right_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_pod.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_single.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_round_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_complete_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_round_movement_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_stay_movement_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_move_movement_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_pod.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_pod.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_round_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_stay_movement.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_stay_complete.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement_v2.gif");
        url.add("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement_v2.gif");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/angrybirdies.png");
    }

    /**
     * transfer file to and from the specified path
     *
     * @param fileName   filename of the source
     * @param inputPath  from / source path (eg: <code>inputpath + "/" + fileName</code> )
     * @param outputPath to / target path (eg: <code>outputPath + "/" + fileName</code> )
     * @param method     <b>MOVE</b> or <b>COPYGIFWITHOUTDASH</b>
     */
    public static void transferFile(String fileName, String inputPath, String outputPath, String method) {
        try {
            // delete file from the temp_dir to avoid duplicate
            if (method.toUpperCase().equals("MOVE")) {
                FileInputStream inStream = new FileInputStream(inputPath + "/" + fileName);
                FileOutputStream outStream = new FileOutputStream(outputPath + "/" + fileName);
                FileChannel inChannel = inStream.getChannel();
                FileChannel outChannel = outStream.getChannel();
                inChannel.transferTo(0, inChannel.size(), outChannel);
                inStream.close();
                outStream.close();

                File delFile = new File(inputPath + "/" + fileName);
                if (delFile.delete()) {
                    Log.i("transferFile", "======= MOVED to " + outputPath + " =======");
                }
            } else if (method.equalsIgnoreCase("COPYGIFWITHOUTDASH")) {
                File delFile = new File(outputPath + "/" + fileName.split("\\-")[0] + ".gif");
                if (delFile.exists() && delFile.delete()) {
                    Log.i("transferFile", "======= deleted file " + outputPath + " =======");
                }

                InputStream in = new FileInputStream(inputPath + "/" + fileName);
                OutputStream out = new FileOutputStream(outputPath + "/" + fileName.split("\\-")[0] + ".gif");

                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();

                Log.i("transferFile", "======= COPIED to " + outputPath + "=======");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
