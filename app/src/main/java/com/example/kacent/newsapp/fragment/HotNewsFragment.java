package com.example.kacent.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.HotNewsAdapter;
import com.example.kacent.newsapp.bean.HotNews;
import com.example.kacent.newsapp.ui.WebActivity;
import com.example.kacent.newsapp.utils.Config;
import com.example.kacent.newsapp.utils.NetWorkRequest;
import com.example.kacent.newsapp.view.ReFlashListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Kacent on 2016/4/18.
 */
public class HotNewsFragment extends Fragment implements ReFlashListView.ReFlashListener{
    public ArrayList<HotNews> hotNewsList;
    public JSONObject mjsonObject;
    public ReFlashListView listView;
    public static RequestQueue mQueue;
    public View view;
    JsonObjectRequest jsonObjectRequest;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.hot_news, container, false);
        listView = (ReFlashListView) view.findViewById(R.id.hotnews_listview);
        initView();

        return view;
    }


    public void initView() {
        NetWorkRequest netWorkRequest = new NetWorkRequest();
        mQueue = netWorkRequest.getQueue(getContext());

         jsonObjectRequest = new JsonObjectRequest(HotNews.HOT_NEWS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mjsonObject = jsonObject;
                try {
                    hotNewsList = HotNews.prase(mjsonObject.getJSONArray("posts"));
                    HotNewsAdapter adapter = new HotNewsAdapter(getContext(), hotNewsList);
                    listView.setAdapter(adapter);
                    itemtOnClick();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "访问数据错误，请稍后再重新尝试刷新", Toast.LENGTH_SHORT).show();
            }
        });
        mQueue.add(jsonObjectRequest);

        listView.setReFlashInterface(this);
    }


    public void itemtOnClick() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotNews hotNews = hotNewsList.get(position);
                String url = hotNews.getCoomentUrl();


                Config.intent = new Intent(getContext(), WebActivity.class);
                Config.intent.putExtra("url", url);
                startActivity(Config.intent);
            }
        });
    }

    @Override
    public void onReFlash() {

                mQueue.start();

                listView.reflashComplete();


    }
}
