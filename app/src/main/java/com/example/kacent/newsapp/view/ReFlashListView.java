package com.example.kacent.newsapp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.example.kacent.newsapp.R;

/**
 * Created by kacent on 2016/5/4.
 */
public class ReFlashListView extends ListView {
    View header;
    public ReFlashListView(Context context) {
        super(context);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ReFlashListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    public void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        header = inflater.inflate(R.layout.header_flash, null);
        this.addHeaderView(header);

    }
}
