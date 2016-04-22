package com.example.kacent.newsapp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.NewFragmentAdapter;
import com.example.kacent.newsapp.fragment.HotNewsFragment;
import com.example.kacent.newsapp.fragment.RecNewsFragment;
import com.example.kacent.newsapp.fragment.SportsNewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {


    @Bind(R.id.viewPager)
    ViewPager viewPager;
    private List<Fragment> listFramgnet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
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

    }
}
