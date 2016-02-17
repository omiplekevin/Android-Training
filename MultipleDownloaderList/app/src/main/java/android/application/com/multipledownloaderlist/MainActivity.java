package android.application.com.multipledownloaderlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    GridView gridDownloadList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridDownloadList = (GridView) findViewById(R.id.gridDownloadList);
        List<String> url = new ArrayList<>();
        url.add("http://cdn.f45.info/F45LogoFinal.mp4");
        url.add("https://f45tv.s3.amazonaws.com/class-logos/athletica.png");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/warm-up.png");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/High-Knee-High-Knees-Normal.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Bum-Kicks-Bum-Kicks-Normal.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/F45-Prisoner-Squat-Jumps-3492-764.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Back-Rolls-Standard-Back-Roll.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Side-To-Sides-Lumbar-Side-To-Side.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Push-Ups.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Mountain-Climbers.mp4");
        url.add("http://f45tv.s3-website-ap-southeast-2.amazonaws.com/videos/GYM_LOW_RES/Backslaps-Old-School-Shoulder-Rolls.mp4");
        url.add("http://cdn.f45.info/F45-Box-Jumps-And-Burpee-3437-558.mp4");
        url.add("http://cdn.f45.info/F45-Db-Swiss-Ball-Shoulder-Press-3238-625.mp4");
        url.add("http://cdn.f45.info/F45-Barbell-Bicep-Curl-3067-494.mp4");
        url.add("http://cdn.f45.info/F45-Hanging-Knee-Raise-3980-657.mp4");
        url.add("http://cdn.f45.info/F45-Kb-Bent-Over-Row-3266-677.mp4");
        url.add("http://cdn.f45.info/F45-Kb-Bent-Over-Row-3266-677.mp4");
        url.add("http://cdn.f45.info/F45-Tricep-Extension-With-Barbell-3633-945.mp4");
        url.add("http://cdn.f45.info/F45-Front-Raise-3248-652.mp4");
        url.add("http://cdn.f45.info/F45-Plank-3604-756.mp4");
        url.add("http://cdn.f45.info/F45-Squat-Pulse-3965-929.mp4");
        url.add("http://cdn.f45.info/F45-Ice-Skater-3259-667.mp4");
        url.add("http://cdn.f45.info/F45-Rip-60-4x-Shuffle-&-2x-Drop-Lunge-3185-822.mp4");
        url.add("http://cdn.f45.info/F45-Incline-Dumbell-Chest-Press-3124-669.mp4");
        url.add("http://cdn.f45.info/F45-Kb-Rack-Squat-3271-684.mp4");
        url.add("http://cdn.f45.info/F45-Tricep-Extension-With-Barbell-3633-945.mp4");
        url.add("http://cdn.f45.info/F45-Chinups-Underhand-Grip-3986-584.mp4");
        url.add("http://cdn.f45.info/F45-Forward-Lunge-3247-648.mp4");
        url.add("http://cdn.f45.info/F45-Box-Push-Ups-3595-561.mp4");
        url.add("http://cdn.f45.info/F45-Lateral-Raise-3294-700.mp4");
        url.add("http://cdn.f45.info/F45-Mb-Russian-Twists-3619-720.mp4");
        url.add("http://cdn.f45.info/F45-Sandbag-Push-Up-Drag-3640-870.mp4");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/grabadrink.png");
        url.add("http://f45tv.s3.amazonaws.com/class-logos/greatjob.png");

        CustomAdapter customAdapter = new CustomAdapter(this, url);
        gridDownloadList.setAdapter(customAdapter);

        ThreadedDownloader threadedDownloader = ThreadedDownloader.getInstance();
        ThreadedDownloader.setDownloadableList(url);
        Intent threadDownloadIntent = new Intent(this, threadedDownloader.getClass());
        this.startService(threadDownloadIntent);
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
}
