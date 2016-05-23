package com.example.kacent.newsapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.NewFragmentAdapter;
import com.example.kacent.newsapp.fragment.HotNewsFragment;
import com.example.kacent.newsapp.fragment.RecNewsFragment;
import com.example.kacent.newsapp.fragment.SportsNewsFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{


    @Bind(R.id.viewPager)
    ViewPager viewPager;
    @Bind(R.id.hot_news)
    TextView textView;
    @Bind(R.id.recreation_news)
   TextView textView2;
    @Bind(R.id.sports_news)
  TextView textView3;

    private List<Fragment> listFramgnet;
    private ScaleAnimation scaleAnimation;
    private ScaleAnimation scaleAnimation2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAnimation();
        initView();
        viewPager.setOnPageChangeListener(this);
       textView2.setOnClickListener(this);
        textView.setOnClickListener(this);
        textView3.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        ButterKnife.bind(this);

        HotNewsFragment hotNewsFragment = new HotNewsFragment();
        RecNewsFragment recNewsFragment = new RecNewsFragment();
        SportsNewsFragment sportsNewsFragment = new SportsNewsFragment();

        listFramgnet = new ArrayList<Fragment>();
        listFramgnet.add(hotNewsFragment);
        listFramgnet.add(recNewsFragment);
        listFramgnet.add(sportsNewsFragment);
        NewFragmentAdapter newFragmentAdapter = new NewFragmentAdapter(getSupportFragmentManager(), listFramgnet);
        viewPager.setAdapter(newFragmentAdapter);
        onPageSelected(0);
        viewPager.setCurrentItem(0);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {




    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                textView.startAnimation(scaleAnimation);
                if (textView2.getAnimation() != null) {
                    textView2.startAnimation(scaleAnimation2);
                }
                textView3.clearAnimation();
                break;
            case 1:
                if (textView.getAnimation() != null) {
                    textView.startAnimation(scaleAnimation2);
                }
                textView2.startAnimation(scaleAnimation);
                if (textView3.getAnimation() != null) {
                    textView3.startAnimation(scaleAnimation2);
                }

                break;
            case 2:
                textView2.startAnimation(scaleAnimation2);
                textView3.startAnimation(scaleAnimation);
                textView.clearAnimation();
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void initAnimation() {
        scaleAnimation  = new ScaleAnimation(1f, 1.3f, 1f, 1.3f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setDuration(500);
        scaleAnimation.setFillAfter(true);

        scaleAnimation2=new ScaleAnimation(1.3f, 1f, 1.3f, 1f,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation2.setDuration(500);
        scaleAnimation2.setFillAfter(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hot_news:
                onPageSelected(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.recreation_news:
                onPageSelected(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.sports_news:
                onPageSelected(2);
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
