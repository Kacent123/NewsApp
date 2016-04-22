package com.example.kacent.newsapp.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kacent on 2016/4/20.
 */
public class HotNews {
    public static String HOT_NEWS_URL = "http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=";


    public int id;
    public String title;
    public String coomentUrl;
    public String litleTitle;
    public String author;
    public String imageUrl;
    public String commentCount;

    public static ArrayList<HotNews> prase(JSONArray jsonArray) {
        ArrayList<HotNews> hotNewsList = new ArrayList<HotNews>();

        for (int i = 0; i < jsonArray.length(); i++) {
            HotNews hotNews = new HotNews();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                hotNews.setId(jsonObject.getInt("id"));
                hotNews.setCoomentUrl(jsonObject.getString("url"));
                hotNews.setTitle(jsonObject.getString("title"));
                hotNews.setCommentCount(jsonObject.getString("comment_count"));
                JSONArray jsonArray1 = jsonObject.getJSONArray("tags");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    hotNews.setLitleTitle(jsonObject1.getString("title"));
                }
                JSONObject jsonObject2 = jsonObject.getJSONObject("author");
                hotNews.setAuthor(jsonObject2.getString("name"));

                JSONObject jsonObject3 = jsonObject.getJSONObject("custom_fields");
                JSONArray jsonArray2 = jsonObject3.getJSONArray("thumb_c");
                hotNews.setImageUrl((String) jsonArray2.get(0));

                hotNewsList.add(hotNews);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        return hotNewsList;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoomentUrl() {
        return coomentUrl;
    }

    public void setCoomentUrl(String coomentUrl) {
        this.coomentUrl = coomentUrl;
    }

    public String getLitleTitle() {
        return litleTitle;
    }

    public void setLitleTitle(String litleTitle) {
        this.litleTitle = litleTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
