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
import com.example.kacent.newsapp.bean.SportNews;
import com.example.kacent.newsapp.cache.BitmapCache;
import com.example.kacent.newsapp.utils.LoadImage;

import java.util.ArrayList;

/**
 * Created by kacent on 2016/5/17.
 */
public class SpNewsAdapter extends BaseAdapter {
    public ArrayList<SportNews> spNewsArrayList;
    public LayoutInflater layoutInflater;
    public LoadImage loadImage;
    public BitmapCache bitmapCache;

    public SpNewsAdapter(Context context, ArrayList<SportNews> spNewsArrayList) {
        this.spNewsArrayList = spNewsArrayList;
        layoutInflater = LayoutInflater.from(context);

        bitmapCache = new BitmapCache();
    }
    @Override
    public int getCount() {
        return spNewsArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return spNewsArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.sp_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.recTitle = (TextView) view.findViewById(R.id.recnews_title);
            viewHolder.recDes = (TextView) view.findViewById(R.id.recnews_description);
            viewHolder.recCtime = (TextView) view.findViewById(R.id.recnews_ctime);
            viewHolder.recImage = (NetworkImageView) view.findViewById(R.id.recnews_image);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        SportNews sportNews = spNewsArrayList.get(i);
        viewHolder.recTitle.setText(sportNews.getTitle());
        viewHolder.recDes.setText(sportNews.getDescription());
        viewHolder.recCtime.setText(sportNews.getCreateData());
        loadImage = new LoadImage();
        loadImage.Load2(viewHolder.recImage,sportNews.picUrl,bitmapCache);
        return view;
    }

    class  ViewHolder{
        TextView recTitle,recDes, recCtime;
        NetworkImageView recImage;

    }
}
