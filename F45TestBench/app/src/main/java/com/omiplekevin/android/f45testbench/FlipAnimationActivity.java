package com.omiplekevin.android.f45testbench;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FlipAnimationActivity extends Activity {

    TextView flipTextView1;
    TextView flipTextView2;
    ValueAnimator valueAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_animation);

        flipTextView1 = (TextView) findViewById(R.id.flipTextView1);
        flipTextView2 = (TextView) findViewById(R.id.flipTextView2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        doAnimation();
    }

    public void doAnimation(){
        valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.addUpdateListener(new FlipListener(flipTextView1, flipTextView2));
    }

    public class FlipListener implements ValueAnimator.AnimatorUpdateListener {

        private final View mFrontView;
        private final View mBackView;
        private boolean mFlipped;

        public FlipListener(final View front, final View back) {
            this.mFrontView = front;
            this.mBackView = back;
            this.mBackView.setVisibility(View.GONE);
        }

        @Override
        public void onAnimationUpdate(final ValueAnimator animation) {
            final float value = animation.getAnimatedFraction();
            final float scaleValue = 0.625f + (1.5f * (value - 0.5f) * (value - 0.5f));

            if(value <= 0.5f){
                this.mFrontView.setRotationX(180 * value);
//                this.mFrontView.setScaleX(scaleValue);
//                this.mFrontView.setScaleY(scaleValue);
                if(mFlipped){
                    setStateFlipped(false);
                }
            } else {
                this.mBackView.setRotationX(-180 * (1f- value));
//                this.mBackView.setScaleX(scaleValue);
//                this.mBackView.setScaleY(scaleValue);
                if(!mFlipped){
                    setStateFlipped(true);
                }
            }
        }

        private void setStateFlipped(boolean flipped) {
            mFlipped = flipped;
            this.mFrontView.setVisibility(flipped ? View.GONE : View.VISIBLE);
            this.mBackView.setVisibility(flipped ? View.VISIBLE : View.GONE);
        }
    }

    public void flip(View view) {
        valueAnimator.start();
    }

    public void reverse(View view) {
        valueAnimator.reverse();
    }

}
