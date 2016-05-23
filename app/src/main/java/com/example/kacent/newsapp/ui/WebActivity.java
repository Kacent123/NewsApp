package com.example.kacent.newsapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.bean.HotNews;
import com.example.kacent.newsapp.bean.ReNews;
import com.example.kacent.newsapp.bean.SportNews;
import com.example.kacent.newsapp.fragment.HotNewsFragment;
import com.example.kacent.newsapp.fragment.RecNewsFragment;
import com.example.kacent.newsapp.fragment.SportsNewsFragment;
import com.example.kacent.newsapp.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Kacent on 2016/4/26.
 */

public class WebActivity extends Activity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.webView_progressbar)
    ProgressBar bar;
    @Bind(R.id.share_image)
    ImageView shareImage;

    private HotNews hotnews;

    private ReNews reNews;
    private SportNews sportNews;
    private String url;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        ButterKnife.bind(this);


        //获取hotnew 发送的url
        /*hotnews = (HotNews) Config.intent.getSerializableExtra(HotNewsFragment.HOT_NEWS_KEY);*/
    /*    String ur1 = hotnews.getCoomentUrl();*/
        key = Config.intent.getStringExtra(Config.WEB_KEY);
        Log.i("webkey", key);
        switch (key) {
            case HotNewsFragment.HOT_NEWS_KEY:
                hotnews = (HotNews) Config.intent.getSerializableExtra(HotNewsFragment.HOT_NEWS_KEY);
                url = hotnews.getCoomentUrl();
                break;
            case RecNewsFragment.REC_NEWS_KEY:
                reNews = (ReNews) Config.intent.getSerializableExtra(RecNewsFragment.REC_NEWS_KEY);
                url = reNews.getCommentUrl();
                break;
            case SportsNewsFragment.SP_NEWS_KEY:
                sportNews = (SportNews) Config.intent.getSerializableExtra(SportsNewsFragment.SP_NEWS_KEY);
                url = sportNews.getCommentUrl();
                break;
        }
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        //webview顶部读条
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);

                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });






      /*  * 页面分享监听事件
        * */
        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (key) {
                    case HotNewsFragment.HOT_NEWS_KEY:
                        showShare(hotnews);
                        break;
                    case RecNewsFragment.REC_NEWS_KEY:
                        showShare(reNews);
                        break;
                    case SportsNewsFragment.SP_NEWS_KEY:
                        showShare(sportNews);
                        break;
                }

            }
        });


    }

    /*
    * 分享方法体
    * */
    private void showShare(HotNews hotnews) {
        ShareSDK.initSDK(WebActivity.this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(hotnews.getTitle());
        oks.setImageUrl(hotnews.imageUrl);
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(hotnews.getCoomentUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(hotnews.getTitle() + "---" + hotnews.getCoomentUrl());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(hotnews.getCoomentUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("河马新闻");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(hotnews.getCoomentUrl());

// 启动分享GUI
        oks.show(WebActivity.this);
    }


    private void showShare(ReNews reNews) {
        ShareSDK.initSDK(WebActivity.this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(reNews.getReNewstitle());
        oks.setImageUrl(reNews.getPicUrl());
        oks.setTitleUrl(reNews.getCommentUrl());
        oks.setText(reNews.getReNewstitle() + "---" + reNews.getCommentUrl());
        oks.setUrl(reNews.getCommentUrl());
        oks.setComment("我是测试评论文本");
        oks.setSite("河马新闻");
        oks.setSiteUrl(reNews.getCommentUrl());
// 启动分享GUI
        oks.show(WebActivity.this);
    }

    private void showShare(SportNews sportNews) {
        ShareSDK.initSDK(WebActivity.this);
        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(sportNews.getTitle());
        oks.setImageUrl(sportNews.getPicUrl());
        oks.setTitleUrl(sportNews.getCommentUrl());
        oks.setText(sportNews.getTitle() + "---" + sportNews.getCommentUrl());
        oks.setUrl(sportNews.getCommentUrl());
        oks.setComment("我是测试评论文本");
        oks.setSite("河马新闻");
        oks.setSiteUrl(sportNews.getCommentUrl());
// 启动分享GUI
        oks.show(WebActivity.this);
    }


}
