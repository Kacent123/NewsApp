package com.example.kacent.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.kacent.newsapp.utils.Utils;
import com.example.kacent.newsapp.view.ReFlashListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Kacent on 2016/4/18.
 */
public class HotNewsFragment extends Fragment implements ReFlashListView.ReFlashListener {
    public ArrayList<HotNews> hotNewsList;
    public JSONObject mjsonObject;
    public ReFlashListView listView;
    public static RequestQueue mQueue;
    public View view;
    public String tempLodingUrl;
    public final static String HOT_NEWS_KEY = "com.hotnews.bean";


    public HotNewsAdapter adapter;
    JsonObjectRequest jsonObjectRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.hot_news, container, false);
        listView = (ReFlashListView) view.findViewById(R.id.hotnews_listview);
        initView();

        return view;
    }

    /*
    * 初始化数据列表
    * */

    public void initView() {
        NetWorkRequest netWorkRequest = new NetWorkRequest();
        mQueue = netWorkRequest.getQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(HotNews.HOT_NEWS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mjsonObject = jsonObject;
                try {
                    hotNewsList = HotNews.prase(mjsonObject.getJSONArray("posts"));
                    adapter = new HotNewsAdapter(getContext(), hotNewsList);
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

    /*
    * 项点击事件 跳转webview
    * */
    public void itemtOnClick() {


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HotNews hotNews = hotNewsList.get(position);
                String url = hotNews.getCoomentUrl();
                Bundle b = new Bundle();
                b.putSerializable(HOT_NEWS_KEY, hotNews);
                Config.intent = null;
                Config.intent = new Intent(getContext(), WebActivity.class);
                Config.intent.putExtra(Config.WEB_KEY, HOT_NEWS_KEY);
                Config.intent.putExtras(b);
                startActivity(Config.intent);
            }
        });
    }


    /*
    * 下拉刷新 执行方法    * */
    @Override
    public void onReFlash() {

        mQueue.start();

        listView.reflashComplete();


    }


    /*
    * 实现底部读取数据
    * */
    @Override
    public void onFootLoding() {

        if (tempLodingUrl == null) {
            tempLodingUrl = Utils.updateUrlString(HotNews.HOT_NEWS_URL);
        }else {
            tempLodingUrl = Utils.updateUrlString(tempLodingUrl);
        }

        jsonObjectRequest = new JsonObjectRequest(tempLodingUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                mjsonObject = jsonObject;
                try {
                    ArrayList<HotNews> tempHotNewsList = HotNews.prase(mjsonObject.getJSONArray("posts"));
                    for (int j = 0; j < tempHotNewsList.size(); j++) {
                        hotNewsList.add(tempHotNewsList.get(j));
                    }
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

        listView.reFlashFootComplete();
    }

}
