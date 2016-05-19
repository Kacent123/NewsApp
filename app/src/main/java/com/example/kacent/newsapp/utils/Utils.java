package com.example.kacent.newsapp.utils;


/**
 * Created by kacent on 2016/5/15.
 */
public class Utils {

    public static String updateUrlString(String urlString) {
        StringBuffer sb = new StringBuffer(urlString);
        String haha = sb.substring(sb.length() - 1);

        int i = Integer.parseInt(haha);
        sb.delete(sb.length() - 1, sb.length());
        sb.append(String.valueOf(i+1));
        String temp = sb.toString();
        return temp;
    }
}
