package com.omiplekevin.android.videolistanimationscrolling;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * Created by OMIPLEKEVIN on Apr 14, 2016.
 */
public class CompoundVideoItem extends FrameLayout {

    private final String TAG = getClass().getName();

    private Context context;

    //view elements
    private VideoView videoView;
    private ImageView imageHolder;
    private ImageView overlayMask;

    private CompoundViewModel viewModel;

    public CompoundVideoItem(Context context) {
        super(context);
        this.context = context;
        initializeFields();
    }

    private void initializeFields(){
        LayoutInflater.from(this.context).inflate(R.layout.listview_video_item, this, true);

        videoView = (VideoView) findViewById(R.id.videoViewItem);
        imageHolder = (ImageView) findViewById(R.id.imageViewItem);
        overlayMask = (ImageView) findViewById(R.id.overlay);

        viewModel = new CompoundViewModel();

    }

    public void setVideo(String path, boolean looping) {
        Log.e(TAG, "setVideo: " + path);
        if (viewModel != null) {
            viewModel.videoPath = path;
            viewModel.isVideoLooping = looping;
        } else {
            Log.e(TAG, "viewModel is null");
        }

        if (imageHolder != null) {
            imageHolder.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "imageHolder is null");
        }

        if (videoView != null) {
            videoView.setVideoURI(Uri.parse(path));
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e(TAG, "something happened on your video item");
                    return false;
                }
            });
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.seekTo(10);
                }
            });
            if (looping) {
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.pause();
                        mp.seekTo(0);
                        mp.start();
                    }
                });
            }
        } else {
            Log.e(TAG, "videoView is null");
        }
    }

    public void setImage(String path) {
        Log.e(TAG, "setImage: " + path);
        if (viewModel != null) {
            viewModel.imagePath = path;
        } else {
            Log.e(TAG, "viewModel is null");
        }

        if (videoView != null) {
            videoView.setVisibility(View.GONE);
        } else {
            Log.e(TAG, "videoView is null");
        }

        if (imageHolder != null) {
            imageHolder.setImageBitmap(BitmapFactory.decodeFile(path));
            imageHolder.invalidate();
        } else {
            Log.e(TAG, "imageHolder is null");
        }
    }

    public void setOverlayMask(String color) {
        if (viewModel != null) {
            viewModel.overlayColor = color;
            viewModel.hasOverlay = true;
        }

        if (overlayMask != null) {
            overlayMask.setBackgroundColor(Color.parseColor(color));
        }
    }

    /**
     * play video for this custom view
     */
    public void play(){
        if (videoView != null) {
            videoView.start();
        } else {
            Log.e(TAG, "videoView is null");
        }
    }

    /**
     * pause video for this custom view
     */
    public void pause(){
        if (videoView != null) {
            videoView.pause();
        } else {
            Log.e(TAG, "videoView is null");
        }
    }

    /**
     * pause video for this custom view
     */
    private void stop(){
        if (videoView != null) {
            videoView.stopPlayback();
        }
    }

    private class CompoundViewModel{
        String videoPath = "";
        String imagePath = "";
        String overlayColor = "";
        boolean hasOverlay = false;
        boolean showOverlay = false;
        boolean isVideoLooping = false;
    }
}
