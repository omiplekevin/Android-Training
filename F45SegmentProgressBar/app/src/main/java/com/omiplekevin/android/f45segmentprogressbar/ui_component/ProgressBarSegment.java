package com.omiplekevin.android.f45segmentprogressbar.ui_component;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.omiplekevin.android.f45segmentprogressbar.R;

/**
 * Created by OMIPLEKEVIN on Mar 11, 2016.
 */
public class ProgressBarSegment extends LinearLayout {

    private static final String TAG = "PBS";

    private final int STOP = 0;
    private final int START = 1;
    private final int PAUSE = 2;

    private int duration = 0;
    private int state = STOP;
    private long currentAnimationTime = 0;
    private int max = 0;

    private ProgressBar mProgressBar;
    private ObjectAnimator mObjectAnimator;

    public ProgressBarSegment(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.ui_component_progressbar, this, true);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void setMaxProgress(int max) {
        this.max = max;
        mProgressBar.setMax(max);
    }

    public int getMax() {
        return this.max;
    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void startAnimation() {
        Log.e(TAG, "startAnimation, DURATION: " + this.duration);
        try {
            if (state == STOP) {
                mObjectAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", this.duration);
                if (!mObjectAnimator.isRunning()) {
                    mObjectAnimator.setInterpolator(new LinearInterpolator());
                    mObjectAnimator.setDuration(duration);
                    mObjectAnimator.start();
                    state = START;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseAnimation() {
        Log.e(TAG, "pauseAnimation");
        if (state == START) {
            if (mObjectAnimator != null) {
                currentAnimationTime = mObjectAnimator.getCurrentPlayTime();
                mObjectAnimator.cancel();
            }
            state = PAUSE;
        }
    }

    public void resumeAnimation() {
        Log.e(TAG, "resumeAnimation");
        if (state == PAUSE) {
            if (mObjectAnimator != null) {
                mObjectAnimator.start();
                mObjectAnimator.setCurrentPlayTime(currentAnimationTime);
                currentAnimationTime = 0;
            }
            state = START;
        }
    }

    public void stopAnimation() {
        Log.e(TAG, "stopAnimation");
        if (state == START || state == PAUSE) {
            if (mObjectAnimator != null) {
                mObjectAnimator.cancel();
                mProgressBar.setProgress(0);
            }
            state = STOP;
        }
    }

}
