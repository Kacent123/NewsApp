package com.example.kacent.newsapp.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Kacent on 2016/4/20.
 */
public class NetWorkRequest {

    public RequestQueue getQueue(Context context) {
        return Volley.newRequestQueue(context);
    }
}
