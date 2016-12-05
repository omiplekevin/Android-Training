package com.omiplekevin.android.f45testbench;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class F45Animations extends Activity {

    private ImageView rhsImageView, lhsImageView, handStop, heartBeat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_f45_animations);

        rhsImageView = (ImageView) findViewById(R.id.rhsArrow);
        lhsImageView = (ImageView) findViewById(R.id.lhsArrow);
        handStop = (ImageView) findViewById(R.id.handStop);
        heartBeat = (ImageView) findViewById(R.id.heartBeat);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        switchStation();
        handPulseAnimation();
        heartPulseAnimation();
    }

    private void switchStation() {
        Animation r_TO_l_animate = AnimationUtils.loadAnimation(this, R.anim.l_to_r);
        r_TO_l_animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation hydrate = AnimationUtils.loadAnimation(F45Animations.this, R.anim.l_to_r);
                hydrate.setAnimationListener(this);
                rhsImageView.setAnimation(hydrate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rhsImageView.startAnimation(r_TO_l_animate);

        Animation l_TO_r_animate = AnimationUtils.loadAnimation(this, R.anim.r_to_l);
        l_TO_r_animate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation hydrate = AnimationUtils.loadAnimation(F45Animations.this, R.anim.r_to_l);
                hydrate.setAnimationListener(this);
                lhsImageView.setAnimation(hydrate);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lhsImageView.startAnimation(l_TO_r_animate);
    }

    private void handPulseAnimation(){
        Animation handPulseAnimation = AnimationUtils.loadAnimation(this, R.anim.hand_stop);
        handPulseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.setAnimationListener(this);
                handStop.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        handStop.startAnimation(handPulseAnimation);
    }

    private void heartPulseAnimation(){
        Animation heartPulseAnimation = AnimationUtils.loadAnimation(this, R.anim.pulse);
        heartPulseAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                animation.setAnimationListener(this);
                heartBeat.startAnimation(animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        heartBeat.startAnimation(heartPulseAnimation);
        /*ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(heartBeat,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();*/
    }

}
