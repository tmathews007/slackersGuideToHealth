package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Danceathon extends Activity {
    WebView webviewDance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danceathon);

        webviewDance = (WebView) findViewById(R.id.webview_dance);
        webviewDance.loadUrl("http://www.silverspring5k.com/");
    }

}
