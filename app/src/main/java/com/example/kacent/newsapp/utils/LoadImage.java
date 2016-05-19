package com.example.kacent.newsapp.utils;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.cache.BitmapCache;
import com.example.kacent.newsapp.fragment.HotNewsFragment;
import com.example.kacent.newsapp.fragment.RecNewsFragment;

/**
 * Created by Kacent on 2016/4/22.
 */
public class LoadImage {

    public void Load(NetworkImageView view, String url,BitmapCache bitmapCache) {

        ImageLoader imageLoader = new ImageLoader(HotNewsFragment.mQueue, bitmapCache);
        view.setDefaultImageResId(R.drawable.config_image);
        view.setErrorImageResId(R.drawable.hema);
        view.setImageUrl(url,imageLoader);

    }
    public void Load2(NetworkImageView view, String url,BitmapCache bitmapCache) {

        ImageLoader imageLoader = new ImageLoader(RecNewsFragment.mQueue, bitmapCache);
        view.setDefaultImageResId(R.drawable.config_image);
        view.setErrorImageResId(R.drawable.hema);
        view.setImageUrl(url,imageLoader);

    }
}
