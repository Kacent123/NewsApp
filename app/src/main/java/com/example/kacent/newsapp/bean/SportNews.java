package com.example.kacent.newsapp.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kacent on 2016/5/17.
 */
public class SportNews implements Serializable {
    public static String SP_NEWS_URL = "http://apis.baidu.com/txapi/tiyu/tiyu?num=20&page=1";

    public String title;
    public String createData;
    public String description;
    public String picUrl;
    public String commentUrl;

    public static ArrayList<SportNews> prase(JSONArray jsonArray) {
        ArrayList<SportNews> reNewses = new ArrayList<SportNews>();
        for(int j=0;j<jsonArray.length();j++) {
            SportNews reNews = new SportNews();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                reNews.setTitle(jsonObject.getString("title"));
                reNews.setCreateData(jsonObject.getString("ctime"));
                reNews.setDescription(jsonObject.getString("description"));
                reNews.setPicUrl(jsonObject.getString("picUrl"));
                reNews.setCommentUrl(jsonObject.getString("url"));
                reNewses.add(reNews);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return reNewses;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getCommentUrl() {
        return commentUrl;
    }

    public void setCommentUrl(String commentUrl) {
        this.commentUrl = commentUrl;
    }


}
