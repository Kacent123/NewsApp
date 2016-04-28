package com.example.kacent.newsapp.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.kacent.newsapp.R;
import com.example.kacent.newsapp.utils.Config;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kacent on 2016/4/26.
 */

public class WebActivity extends Activity {
    @Bind(R.id.webView)
    WebView webView;
    @Bind(R.id.webView_progressbar)
    ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_activity);
        ButterKnife.bind(this);


        //获取hotnew 发送的url
        Bundle extras =  Config.intent.getExtras();
        String ur1 = (String) extras.get("url");


        webView.loadUrl(ur1);
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
    }


}
