package com.omiplekevin.android.videolistanimationscrolling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements AbsListView.OnScrollListener {

    ListView listView;

    Button nextBtn, prevBtn;

    final String parentDir = Environment.getExternalStorageDirectory() + "/F45/";
    List<String> videoFileNames = new ArrayList<>();
    VideoArrayAdapter videoArrayAdapter;
    int ndx = 0;
    String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.videoListView);

        nextBtn = (Button) findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextVideo(ndx);
                ndx++;
            }
        });
        prevBtn = (Button) findViewById(R.id.prevBtn);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ndx--;
                prevVideo(ndx);
            }
        });

        /*videoFileNames.add(parentDir + "Back-Rolls-Standard-Back-Roll.mp4");
        videoFileNames.add(parentDir + "F45-Row-3615-852.mp4");*/
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        videoFileNames.add(parentDir + "images/angrybirds.png");
        setupListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Runnable run = new Runnable() {
            @Override
            public void run() {
                int target = 18;
                nextVideo(target);
                ndx = target;
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(run, 3000);
    }

    private void setupListView() {
        listView.setVisibility(View.VISIBLE);
        videoArrayAdapter = new VideoArrayAdapter(this, videoFileNames);
        listView.setAdapter(videoArrayAdapter);
        listView.setOnScrollListener(this);
    }

    public void nextVideo(int indexReference) {
//        scrollViewHolder.smoothScrollTo(0, 800);
        listView.setOnScrollListener(MainActivity.this);
        if (Math.abs(indexReference - ndx) > listView.getChildCount()) {
            Log.e(TAG, "doing smartJump()");
            smartJump(indexReference);
        } else {
            Log.e(TAG, "doing normal stuff...");
            int childCount = listView.getChildCount();
            int firstVisiblePosition = listView.getFirstVisiblePosition();
            int totalCount = listView.getAdapter().getCount();
            if ((childCount + indexReference) > totalCount) {
                listView.smoothScrollToPositionFromTop(indexReference, 0, 1000);
                doManualFadingAnimation(indexReference - firstVisiblePosition);
            } else {
                listView.smoothScrollToPositionFromTop(indexReference, 0, 1000);
            }
        }
    }

    /**
     * this function jumps the list to the desired item on list, not scrolling it but correctly displaying it.
     */
    public void smartJump(final int jumpToReference) {
        Log.e(TAG, "im in smartJump()...");

        //lets reset the child views' overlay to alpha 1
        for (int i = 0; i < listView.getChildCount(); i++) {
            Log.e(TAG, "reset child: " + i);
            View v = listView.getChildAt(i);
            ((VideoArrayAdapter.ViewHolder) v.getTag()).overlay.animate().alpha(1).setDuration(500).start();
        }
        listView.smoothScrollToPositionFromTop(jumpToReference, 0, 1000);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    Log.e(TAG, "SCROLL_STATE_IDLE " + listView.getFirstVisiblePosition() + " " + jumpToReference);
                    doManualFadingAnimation(jumpToReference - listView.getFirstVisiblePosition());
                } else {
                    Log.e(TAG, "SCROLL_STATE_SCROLL");
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

    }

    public void prevVideo(int indexReference) {
        listView.setOnScrollListener(MainActivity.this);
        int childCount = listView.getChildCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int totalCount = listView.getAdapter().getCount();
//        final int posIndex = videoArrayAdapter.getPositionOnList(videoFileNames.get(ndx));
        if ((childCount + indexReference) > totalCount) {
            Log.e(TAG, "do manual fading here... " + childCount + " " + firstVisiblePosition + " " + totalCount + " ndx: " + indexReference);
            doManualFadingAnimation(indexReference - firstVisiblePosition);
            listView.smoothScrollToPositionFromTop(indexReference, 0, 1000);
        } else {
            listView.smoothScrollToPositionFromTop(indexReference, 0, 1000);
        }
    }

    public void doManualFadingAnimation(final int childIndex) {
        /*Log.e(TAG, "doing fading Animation");
        View t_topView = listView.getChildAt(childIndex - 1);
        View t_View = listView.getChildAt(childIndex);
        View t_bottomView = null;

        if ((childIndex + 1) <= (listView.getAdapter().getCount() - 1)) {
            t_bottomView = listView.getChildAt(childIndex + 1);
        }
        if (t_topView != null) {
            ((VideoArrayAdapter.ViewHolder) t_topView.getTag()).overlay.animate().alpha(1).setDuration(500).start();
        } else {
            Log.e(TAG, "t_topView is null");
        }
        if (t_View != null) {
            ((VideoArrayAdapter.ViewHolder) t_View.getTag()).overlay.animate().alpha(0).setDuration(500).start();
        } else {
            Log.e(TAG, "t_View is null");
        }
        if (t_bottomView != null) {
            ((VideoArrayAdapter.ViewHolder) t_bottomView.getTag()).overlay.animate().alpha(1).setDuration(500).start();
        } else {
            Log.e(TAG, "t_bottomView is null");
        }*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<listView.getChildCount();i++) {
                    View v = listView.getChildAt(i);
                    Log.e(TAG, "child: " + i + ", target: " + childIndex);
                    VideoArrayAdapter.ViewHolder viewHolder = ((VideoArrayAdapter.ViewHolder) v.getTag());
                    if (i == childIndex) {
                        Log.e(TAG, "found");
                        if (viewHolder.overlay.getAlpha() != 0F) {
                            Log.e(TAG, "alpha " + viewHolder.overlay.getAlpha());
                            viewHolder.overlay.animate().alpha(0).setDuration(200).start();
                            viewHolder.overlay.setAlpha(0F);
                        }
                        v.invalidate();
                    } else {
                        Log.e(TAG, "skip");
                        if (viewHolder.overlay.getAlpha() < 0.9F) {
                            Log.e(TAG, "alpha " + viewHolder.overlay.getAlpha());
                            viewHolder.overlay.animate().alpha(1).setDuration(200).start();
                            viewHolder.overlay.setAlpha(1F);
                        }
                        v.invalidate();
                    }
                }
            }
        });
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

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE) {
            Log.e(TAG, "CC: " + view.getChildCount() + ", NDX: " + ndx + ", FVI: " + view.getFirstVisiblePosition() + ", TIC: " + view.getAdapter().getCount());
        } else {
            Log.e(TAG, "scrolling...");
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            //child 1
            float top = (float) Math.abs(listView.getChildAt(0).getTop());
            float height = (float) listView.getChildAt(0).getHeight();
            float alphaCompute = (((height - top)) / height);
            //child 2
            float top2 = (float) Math.abs(listView.getChildAt(1).getTop());
            float height2 = (float) listView.getChildAt(1).getHeight();
            float alphaCompute2 = (((height2 - top2)) / height2);
                    /*Log.e(TAG, String.format("" +
                                    "\nh: %.2f, t: %.2f, top_alpha: %.2f, h-t: %.2f, (h-t)/h: %.2f" +
                                    "\nh2: %.2f, t2: %.2f, top_alpha2: %.2f, h2-t2: %.2f, (h2-t2)/h2: %.2f",
                            height, top, 1 - alphaCompute, (height - top), (height - top) / height, height2, top2, 1 - alphaCompute2, (height2 - top2), (height2 - top) / height2));*/
            View v = listView.getChildAt(0);
            VideoArrayAdapter.ViewHolder viewHolder = (VideoArrayAdapter.ViewHolder) v.getTag();
            viewHolder.overlay.setAlpha(1 - alphaCompute);

            View v2 = listView.getChildAt(1);
            VideoArrayAdapter.ViewHolder viewHolder2 = (VideoArrayAdapter.ViewHolder) v2.getTag();
            viewHolder2.overlay.setAlpha(1 - alphaCompute2);
        } catch (Exception e) {
            Log.e(TAG, "child at 0 is not yet drawn");
            e.printStackTrace();
        }
    }

    public class VideoArrayAdapter extends BaseAdapter {

        Context context;
        List<String> videoList;

        public VideoArrayAdapter(Context context, List<String> videoList) {
            this.context = context;
            this.videoList = videoList;
        }

        @Override
        public int getCount() {
            return this.videoList.size();
        }

        @Override
        public String getItem(int position) {
            return this.videoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(this.context).inflate(R.layout.listview_video_item, parent, false);
                viewHolder.overlay = (ImageView) convertView.findViewById(R.id.overlay);
                viewHolder.videoView = (VideoView) convertView.findViewById(R.id.videoViewItem);
                viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageViewItem);
                viewHolder.ndx = (TextView) convertView.findViewById(R.id.ndx);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            Log.e("VideoArrayAdapter", this.videoList.get(position));
            viewHolder.ndx.setText(String.format("%d", position));
            if (classifier(this.videoList.get(position)) == 1) {
                viewHolder.videoView.setVisibility(View.VISIBLE);
                viewHolder.imageView.setVisibility(View.GONE);

                viewHolder.videoView.setVideoURI(Uri.parse(this.videoList.get(position)));
                viewHolder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.pause();
                        mp.seekTo(0);
                        mp.start();
                    }
                });
                viewHolder.videoView.start();
            } else {
                viewHolder.videoView.setVisibility(View.GONE);
                viewHolder.imageView.setVisibility(View.VISIBLE);

                viewHolder.imageView.setImageBitmap(BitmapFactory.decodeFile(this.videoList.get(position)));
            }
            return convertView;
        }

        public class ViewHolder {
            public ImageView overlay;
            public VideoView videoView;
            public ImageView imageView;
            public TextView ndx;
        }
    }

    /**
     * classify the given filename depending on its file extension
     * <p/>
     * <ul>
     * <li>1 - video file classification</li>
     * <li>2 - image file classification</li>
     * <li>3 - music file classification</li>
     * <li>0 - Unlisted / Unknown classification</li>
     * </ul>
     *
     * @param filename filename of the file
     * @return returns the file extension
     */
    public static int classifier(String filename) {
        String fileExtension = FilenameUtils.getExtension(filename);
        fileExtension = fileExtension.toUpperCase(Locale.getDefault());

        switch (fileExtension) {
            case "MP4":
            case "3GP":
            case "MOV":
            case "M4A":
                //video file classification
                return 1;
            case "JPG":
            case "PNG":
            case "BMP":
                //image file classification
                return 2;
            case "MP3":
            case "OGG":
            case "WMV":
                //music file classification
                return 3;
            default:
                //unknown / unlisted classification
                return 0;
        }
    }
}
