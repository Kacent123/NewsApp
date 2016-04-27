package com.example.kacent.newsapp.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.HotNewsAdapter;
import com.example.kacent.newsapp.bean.HotNews;
import com.example.kacent.newsapp.ui.WebActivity;
import com.example.kacent.newsapp.utils.NetWorkRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Kacent on 2016/4/18.
 */
public class HotNewsFragment extends Fragment {
    public ArrayList<HotNews> hotNewsList;
    public JSONObject mjsonObject;


public ListView listView;
    public  static  RequestQueue mQueue;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        NetWorkRequest netWorkRequest = new NetWorkRequest();
        View view=inflater.inflate(R.layout.hot_news, container, false);
        /*ButterKnife.bind((Activity) getContext());*/
        listView= (ListView) view.findViewById(R.id.hotnews_listview);
/*
        listView = (ListView) inflater.inflate(R.id.hotnews_listview, null);
*/
        mQueue = netWorkRequest.getQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(HotNews.HOT_NEWS_URL,null,new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.i("newwork", jsonObject.toString());
                mjsonObject = jsonObject;
                try {
                    hotNewsList = HotNews.prase(mjsonObject.getJSONArray("posts"));
                    HotNewsAdapter adapter = new HotNewsAdapter(getContext(), hotNewsList);
                    listView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        mQueue.add(jsonObjectRequest);


        new Thread(){

            @Override
            public void run() {


                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HotNews hotNews = hotNewsList.get(position);
                Intent intent = new Intent(getContext(),WebActivity.class);

                startActivity(intent);
                        Log.i("web", "位置：+"+position+"地址" + hotNews.getCoomentUrl()+"");
                    }
                });
            }
        }.run();

        return view;
    }
}
