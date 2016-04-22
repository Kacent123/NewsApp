package com.example.kacent.newsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.HotNewsAdapter;
import com.example.kacent.newsapp.bean.HotNews;
import com.example.kacent.newsapp.utils.NetWorkRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Kacent on 2016/4/18.
 */
public class HotNewsFragment extends Fragment {
    public ArrayList<HotNews> hotNewsList;
    public JSONObject mjsonObject;

    public ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        NetWorkRequest netWorkRequest = new NetWorkRequest();
        View view=inflater.inflate(R.layout.hot_news, container, false);
        listView= (ListView) view.findViewById(R.id.hotnews_listview);
/*
        listView = (ListView) inflater.inflate(R.id.hotnews_listview, null);
*/
        RequestQueue mQueue = netWorkRequest.getQueue(getContext());
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


        return view;
    }
}
