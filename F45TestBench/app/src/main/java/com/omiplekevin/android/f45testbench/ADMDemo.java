package com.omiplekevin.android.f45testbench;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.omiplekevin.android.f45testbench.downloadmanagerpro.com.golshadi.majid.core.enums.QueueSort;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.demo.FragmentOne;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadEvent;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.DownloadManager;
import com.omiplekevin.android.f45testbench.downloadmanagerpro.wrapper.Subject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ADMDemo extends FragmentActivity {

    private static final String TAG = "ADMDemo";
    private Subject mainSubject;
    private List<DownloadEvent> downloadItemList;
    private DownloadManager downloadManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admdemo);
        downloadManager = new DownloadManager(ADMDemo.this, mainSubject);
        //TEMPORARY!!!!
        DownloadManager.initializeDirectories();
        Button startDownload = (Button) findViewById(R.id.startDownloadBtn);
        startDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager.initialize();
                downloadManager.setPrioritization(QueueSort.HighToLowPriority);

                downloadItemList = new ArrayList<DownloadEvent>(){{
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity_new.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/warm-up.png", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/The%20Attention%20Mover%20full%20Body%20Wake%20Up%203048.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/F45-Hi-Knee-Run-On-The-Spot-3254-661.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/N%20a%20plyolunge%20Ground%20Touch%203204.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Lower%20Ballistics%20shuffle%20And%20Aduction%203067.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Hamstring-Stretch-Hammi-Swings.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Pushups-and-Mountain-Climbers-Push-Up-Glute-Pyramid.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Floor%20Or%20Lumbarlying%20Hip%20Flexor%20Pulse%203073.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/High-Knee-Shuffle-Feet-In-Dif-Directions.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Explosive%20fast%20Feet%20Tempo%203084.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/F45-Tuck-Jumps-3528-948.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/others/hydrate.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/greatjob.png", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/challenge_ads/CHAL_AD_MAY_2.jpg", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/challenge_ads/CHAL_AD_MAY_1.jpg", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_athletica.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_hollywood.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_22.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_romans.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_wingman.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_brooklyn.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_panthers.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_pipeline.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_firestorm.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_3peat.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_renegades.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_docklands.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_templars.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_gravity.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_foxtrot_new.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_quarterbacks.png", false, false));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/slideshow/logo_angrybird_v2.png", false, false));
                    add(new DownloadEvent("https://s3-eu-west-1.amazonaws.com/f45fm/mixes/c7I1DrPHSZSspWkxIV6S_2%20am%20Anthems_DJ%20Chris%20Dominick_June%209%202017.mp3?response-content-disposition=attachment&response-content-type=application%2Foctet-stream&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAJO2BTD6STFMSAB4A%2F20170615%2Feu-west-1%2Fs3%2Faws4_request&X-Amz-Date=20170615T025410Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Signature=57c5d2b6e22a0fccf210a407afdf402f0f65545099def7ae3621e8ab0d161a7a", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_stay_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_hydrate_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_move_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_left_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_mid_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_right_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_horizontal.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_stay.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_v5.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_green_pod_single.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_group_pods.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_red_pod_single.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement_pulse.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_pod.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_single.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_round_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_round_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_stay_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_move_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_pod.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_pod.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_round_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_stay_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_stay_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/angrybirdies.png", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Bosu-bosu%20Jump%20On%20Off%20Into%20Hands%20On%20Burpee-2223.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/High%20Kness%20Heel%20Tap-1858.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Smartstep-fast%20Feet%20180-2664.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Mountain-Climbers-3623-732.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Row-3615-852.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Db-Maneater-3298-603.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Bodyweight%20Pelvic%20Floor%20Pulse%203321.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Sledgehammer-The-Destroyer-3449-916.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Shuttle-Sprints-With-Lateral-Jumps-3906-895.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity_timer.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/small/small_varsity.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));
                }};
                downloadManager.addAllFiles(downloadItemList);

                //prepare files
                List<DownloadEvent> downloadEventList = downloadManager.prepareFiles();
                //add downloadables to the ADM queue
                downloadManager.addToQueue(downloadEventList);

                if (downloadEventList != null) {
                    Log.d("ADMDemo", "onClick invoke start download");
                    downloadManager.startADMDownloads();
                } else {
                    Log.w(TAG, "onClick:  downloadEventList is null!");
                }
            }
        });
        Button clearCacheBtn = (Button) findViewById(R.id.clearCache);
        clearCacheBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = ADMDemo.this.getCacheDir();
                deleteDir(dir);
            }
        });

        mainSubject = new Subject();
        setupFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public void setupFragments() {
        getFragmentManager().beginTransaction().replace(R.id.frame1, FragmentOne.newInstance()).commit();
    }

    ///////////////////////////////////////////////////////////////////////////
    // GETTERS and SETTERS
    ///////////////////////////////////////////////////////////////////////////

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }




    /*add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity_new.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/warm-up.png", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/The%20Attention%20Mover%20full%20Body%20Wake%20Up%203048.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/F45-Hi-Knee-Run-On-The-Spot-3254-661.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/N%20a%20plyolunge%20Ground%20Touch%203204.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Lower%20Ballistics%20shuffle%20And%20Aduction%203067.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Hamstring-Stretch-Hammi-Swings.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Pushups-and-Mountain-Climbers-Push-Up-Glute-Pyramid.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Floor%20Or%20Lumbarlying%20Hip%20Flexor%20Pulse%203073.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/High-Knee-Shuffle-Feet-In-Dif-Directions.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/Explosive%20fast%20Feet%20Tempo%203084.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/videos/GYM_LOW_RES/F45-Tuck-Jumps-3528-948.mp4", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/others/hydrate.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/greatjob.png", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/challenge_ads/CHAL_AD_MAY_2.jpg", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/challenge_ads/CHAL_AD_MAY_1.jpg", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_athletica.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_hollywood.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_22.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_romans.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_wingman.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_brooklyn.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_panthers.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_pipeline.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_firestorm.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_3peat.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_renegades.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_docklands.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_templars.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_gravity.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_foxtrot_new.png", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/slideshow/logo_quarterbacks.png", false, false));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/slideshow/logo_angrybird_v2.png", false, false));
                    add(new DownloadEvent("https://s3-eu-west-1.amazonaws.com/f45fm/mixes/c7I1DrPHSZSspWkxIV6S_2%20am%20Anthems_DJ%20Chris%20Dominick_June%209%202017.mp3?response-content-disposition=attachment&response-content-type=application%2Foctet-stream&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAJO2BTD6STFMSAB4A%2F20170615%2Feu-west-1%2Fs3%2Faws4_request&X-Amz-Date=20170615T025410Z&X-Amz-SignedHeaders=host&X-Amz-Expires=604800&X-Amz-Signature=57c5d2b6e22a0fccf210a407afdf402f0f65545099def7ae3621e8ab0d161a7a", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_stay_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_hydrate_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_move_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_round_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/docklands_stay_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_left_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_mid_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_right_move_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hydrate_move_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_horizontal.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_stay.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_knob_animated_v5.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_green_pod_single.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_group_pods.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/movement_vertical_red_pod_single.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_complete_movement_pulse.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/panthers_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_pod.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_move_movement_single.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_round_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_stay_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_complete_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/wingman_switch_movement.gif", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/brooklyn_round_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_stay_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_move_movement_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_pod.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_horizontal_pod.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_round_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_stay_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/angrybirds_movement_stay_complete.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_move_complete_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/hollywood_stay_complete_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/angrybirdies.png", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Bosu-bosu%20Jump%20On%20Off%20Into%20Hands%20On%20Burpee-2223.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/High%20Kness%20Heel%20Tap-1858.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Smartstep-fast%20Feet%20180-2664.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Mountain-Climbers-3623-732.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Row-3615-852.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Db-Maneater-3298-603.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/Bodyweight%20Pelvic%20Floor%20Pulse%203321.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Sledgehammer-The-Destroyer-3449-916.mp4", false, true));
                    add(new DownloadEvent("http://cdn.f45.info/F45-Shuttle-Sprints-With-Lateral-Jumps-3906-895.mp4", false, true));
                    add(new DownloadEvent("https://f45tv.s3.amazonaws.com/class-logos/varsity_timer.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/class-logos/small/small_varsity.png", false, true));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement_v2.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_right_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_mid_movement.gif", false, false));
                    add(new DownloadEvent("http://f45tv.s3.amazonaws.com/movement-gifs/pipeline_left_movement.gif", false, false));*/
}
