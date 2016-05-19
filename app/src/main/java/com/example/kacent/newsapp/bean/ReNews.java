package com.example.kacent.newsapp.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kacent on 2016/5/17.
 */
public class ReNews implements Serializable {
    public static String RE_NEWS_URL = "http://apis.baidu.com/txapi/huabian/newtop?num=20&page=1";

    public String reNewstitle;
    public String createData;
    public String description;
    public String picUrl;
    public String commentUrl;

    public static ArrayList<ReNews> prase(JSONArray jsonArray) {
        ArrayList<ReNews> reNewses = new ArrayList<ReNews>();
        for(int j=0;j<jsonArray.length();j++) {
            ReNews reNews = new ReNews();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                reNews.setReNewstitle(jsonObject.getString("title"));
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

    public String getReNewstitle() {
        return reNewstitle;
    }

    public void setReNewstitle(String reNewstitle) {
        this.reNewstitle = reNewstitle;
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

    @Override
    public String toString() {
        return "ReNews{" +
                "reNewstitle='" + reNewstitle + '\'' +
                ", createData='" + createData + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", commentUrl='" + commentUrl + '\'' +
                '}';
    }
}
