package com.omiplekevin.android.openglvideoplayback;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;

import com.omiplekevin.android.openglvideoplayback.gl.VideoTextureRenderer;

import java.io.IOException;
public class MainActivity extends Activity implements TextureView.SurfaceTextureListener{

    private int[] surface = {R.id.surface1, R.id.surface2, R.id.surface3, R.id.surface4, R.id.surface5, R.id.surface6, R.id.surface7, R.id.surface8, R.id.surface9, R.id.surface10};
    private TextureView[] textureViews;
    private MediaPlayer player;
    private VideoTextureRenderer renderer;

    private int surfaceWidth;
    private int surfaceHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textureViews = new TextureView[surface.length];
        for (int i = 0; i < surface.length; i++) {
            textureViews[i] = (TextureView) findViewById(surface[i]);
            textureViews[i].setSurfaceTextureListener(this);
        }


    }
    private void startPlaying(TextureView textureView)
    {
        renderer = new VideoTextureRenderer(this, textureView.getSurfaceTexture(), surfaceWidth, surfaceHeight);
        player = new MediaPlayer();

        try
        {
            AssetFileDescriptor afd = getAssets().openFd("sample.mp4");
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setSurface(new Surface(renderer.getVideoTexture()));
            player.setLooping(true);
            player.prepare();
            renderer.setVideoSize(player.getVideoWidth(), player.getVideoHeight());
            player.start();

        }
        catch (IOException e)
        {
            throw new RuntimeException("Could not open input video!");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (TextureView textureView : textureViews) {
            if (textureView.isAvailable()) {
                startPlaying(textureView);
            }
        }
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        surfaceWidth = width;
        surfaceHeight = height;
        for (TextureView textureView : textureViews) {
            startPlaying(textureView);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }
}
