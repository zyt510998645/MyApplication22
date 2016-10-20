package com.example.myapplication22;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by 51099 on 2016/10/17.
 */
public class WebActivity extends BaseActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle pineapple){
        super.onCreate(pineapple);
        setContentView(R.layout.web_activity);

        webView=(WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com");
    }
}
