package com.hansung.android.projectandroid2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by USER on 2017-12-10.
 */

public class anim extends AppCompatActivity{

    ImageView mFirework;
    int mScreenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anum);

        mFirework = (ImageView) findViewById(R.id.fire2);
    }

    protected void onResume() {
        super.onResume();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        mScreenHeight = displaymetrics.heightPixels;

        startFireTweenAnimation();
//
//        Intent maps = new Intent(getApplicationContext() , MapActivity.class);
//        startActivity(maps);

    }

    private void startFireTweenAnimation() {
        Animation fire_anim = AnimationUtils.loadAnimation(this, R.anim.tower);
        mFirework.startAnimation(fire_anim);
        fire_anim.setAnimationListener(animationListener);
    }


    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Log.i("Test", "onAnimationStart");
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Log.i("Test", "onAnimationEnd");
            finish();
            startActivity(new Intent(getApplicationContext(), MapActivity.class));
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Log.i("Test", "onAnimationRepeat");
        }
    };


}
