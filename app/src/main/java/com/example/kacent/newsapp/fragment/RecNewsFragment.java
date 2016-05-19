package com.example.kacent.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.adapter.RecNewsAdapter;
import com.example.kacent.newsapp.bean.ReNews;
import com.example.kacent.newsapp.ui.WebActivity;
import com.example.kacent.newsapp.utils.Config;
import com.example.kacent.newsapp.utils.NetWorkRequest;
import com.example.kacent.newsapp.utils.Utils;
import com.example.kacent.newsapp.view.ReFlashListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kacent on 2016/4/18.
 */
public class RecNewsFragment extends Fragment implements ReFlashListView.ReFlashListener{
    public View view;
    public ReFlashListView listView;
    public static RequestQueue mQueue;
    public ArrayList<ReNews> reNewsArrayList;
    public String tempLodingUrl;
    public JsonObjectRequest jsonObjectRequest;
    public static final String REC_NEWS_KEY = "com.recnews.bean";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.recreation_news,container,false);
        listView = (ReFlashListView) view.findViewById(R.id.recNews_listview);
        initView();
        listView.setReFlashInterface(this);
        return view;
    }

    public void initView() {
        NetWorkRequest request = new NetWorkRequest();
        mQueue = request.getQueue(getContext());
     jsonObjectRequest=new JsonObjectRequest(ReNews.RE_NEWS_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    reNewsArrayList = ReNews.prase(jsonObject.getJSONArray("newslist"));
                    RecNewsAdapter adapter = new RecNewsAdapter(getContext(),reNewsArrayList);
                    listView.setAdapter(adapter);

                    itemOnClick();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getContext(), "访问数据错误，请稍后再重新尝试刷新", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "76d4797ad1a87eebf9128b33d7e8e9a6");
                return headers;
            }
        };

        mQueue.add(jsonObjectRequest);
    }


    /*
    * 项 点击事件
    * */
    public void itemOnClick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ReNews reNews = reNewsArrayList.get(i);
                Bundle bundle = new Bundle();
                bundle.putSerializable(REC_NEWS_KEY,reNews);
                Config.intent = new Intent(getContext(), WebActivity.class);
                Config.intent.putExtra(Config.WEB_KEY, REC_NEWS_KEY);
                Config.intent.putExtras(bundle);
                startActivity(Config.intent);
            }
        });
    }

    @Override
    public void onReFlash() {
        mQueue.start();
        listView.reflashComplete();
    }

    @Override
    public void onFootLoding() {
        if (tempLodingUrl == null) {
            tempLodingUrl = Utils.updateUrlString(ReNews.RE_NEWS_URL);
        }else {
            tempLodingUrl = Utils.updateUrlString(tempLodingUrl);
        }

        jsonObjectRequest = new JsonObjectRequest(tempLodingUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {

                try {
                    ArrayList<ReNews> tempHotNewsList = ReNews.prase(jsonObject.getJSONArray("newslist"));
                    for (int j = 0; j < tempHotNewsList.size(); j++) {
                        reNewsArrayList.add(tempHotNewsList.get(j));
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
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "76d4797ad1a87eebf9128b33d7e8e9a6");
                return headers;
            }
        };
        mQueue.add(jsonObjectRequest);

        listView.reFlashFootComplete();
    }
}
