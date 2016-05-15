package com.example.kacent.newsapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    /*
    *测试json连接自增加1
    * */
    @Test
    public void testString() {
        StringBuffer HOT_NEWS_URL = new StringBuffer();
        HOT_NEWS_URL.append("http://jandan.net/?oxwlxojflwblxbsapi=get_recent_posts&include=url,date,tags,author,title,comment_count,custom_fields&custom_fields=thumb_c,views&dev=1&page=1");
        String j=HOT_NEWS_URL.substring(HOT_NEWS_URL.length() - 1);
        System.out.println(HOT_NEWS_URL);
        System.out.println(j);
        int i = Integer.parseInt(j);
        HOT_NEWS_URL.delete(HOT_NEWS_URL.length()-1, HOT_NEWS_URL.length());
        System.out.println(HOT_NEWS_URL);
        int b = i+1;
        HOT_NEWS_URL.append(String.valueOf(b));

        System.out.println(HOT_NEWS_URL);

    }


}