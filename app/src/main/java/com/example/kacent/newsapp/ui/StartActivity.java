package com.example.kacent.newsapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.example.kacent.newsapp.R;



/**
 * Created by kacent on 2016/5/23.
 */


public class StartActivity extends Activity {
    ImageView welcomeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
        welcomeImage = (ImageView) findViewById(R.id.start_image);


        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setDuration(3000);
        welcomeImage.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new AnimationImpl());
    }

    class startHandler implements Runnable {
        @Override
        public void run() {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            StartActivity.this.finish();
        }
    }

    class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Handler x = new Handler();
            x.postDelayed(new startHandler(), 2000);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
