package com.example.kacent.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.bean.HotNews;
import com.example.kacent.newsapp.cache.BitmapCache;
import com.example.kacent.newsapp.utils.LoadImage;

import java.util.ArrayList;

/**
 * Created by Kacent on 2016/4/20.
 */
public class HotNewsAdapter extends BaseAdapter {
    public LayoutInflater mInflater;
    public ArrayList<HotNews> hotNewsList;
    public LoadImage loadImage;
    public BitmapCache bitmapCache;
    public HotNewsAdapter(Context context, ArrayList<HotNews> hotNewsList) {
        this.mInflater = LayoutInflater.from(context);
        this.hotNewsList = hotNewsList;
        bitmapCache = new BitmapCache();
    }

    @Override

    public int getCount() {
        return hotNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return hotNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hot_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.newsImage = (NetworkImageView) convertView.findViewById(R.id.news_image);
            viewHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
            viewHolder.newsAuthor = (TextView) convertView.findViewById(R.id.news_author);
            viewHolder.newsLitleTitle = (TextView) convertView.findViewById(R.id.news_litleTitle);
            viewHolder.newsCommentCount = (TextView) convertView.findViewById(R.id.news_comment_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        HotNews hotNews = hotNewsList.get(position);
        viewHolder.newsTitle.setText(hotNews.getTitle());
        viewHolder.newsAuthor.setText(hotNews.getAuthor());
        viewHolder.newsLitleTitle.setText(hotNews.getLitleTitle());
        viewHolder.newsCommentCount.setText(hotNews.getCommentCount());


        loadImage = new LoadImage();
        loadImage.Load(viewHolder.newsImage,hotNews.imageUrl,bitmapCache);
        return convertView;
    }


    public class ViewHolder {
                 
        public NetworkImageView newsImage;
        public TextView newsTitle, newsLitleTitle, newsAuthor, newsCommentCount;
    }
}
