package com.example.kacent.newsapp.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;


/**
 * Created by Kacent on 2016/4/22.
 */
public class BitmapCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {


    public static int getDefaultLruCache() {
        int maxmemory = (int) Runtime.getRuntime().maxMemory() / 1024;
        int cache = maxmemory / 8;
        return cache;
    }

    public BitmapCache() {
        this(getDefaultLruCache());
    }

    public BitmapCache(int maxSize) {
        super(maxSize);
    }


    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
