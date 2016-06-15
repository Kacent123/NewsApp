package com.example.kacent.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.bean.ReNews;
import com.example.kacent.newsapp.cache.BitmapCache;
import com.example.kacent.newsapp.utils.LoadImage;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by kacent on 2016/5/17.
 */
public class RecNewsAdapter extends BaseAdapter {
    public ArrayList<ReNews> reNewsArrayList;
    public LayoutInflater layoutInflater;
    public LoadImage loadImage;
    public BitmapCache bitmapCache;

    public RecNewsAdapter(Context context, ArrayList<ReNews> reNewsArrayList) {
        this.reNewsArrayList = reNewsArrayList;
        layoutInflater = LayoutInflater.from(context);

        bitmapCache = new BitmapCache();
    }

    @Override
    public int getCount() {
        return reNewsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return reNewsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.re_list_item, null);
            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ReNews reNews = reNewsArrayList.get(i);
        viewHolder.recTitle.setText(reNews.getReNewstitle());
        viewHolder.recDes.setText(reNews.getDescription());
        viewHolder.recCtime.setText(reNews.getCreateData());
        loadImage = new LoadImage();
        loadImage.Load2(viewHolder.recImage, reNews.picUrl, bitmapCache);
        return view;
    }

    class ViewHolder {
        @Bind(R.id.recnews_title)
        TextView recTitle;
        @Bind(R.id.recnews_description)
        TextView recDes;
        @Bind(R.id.recnews_ctime)
        TextView recCtime;
        @Bind(R.id.recnews_image)
        NetworkImageView recImage;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
