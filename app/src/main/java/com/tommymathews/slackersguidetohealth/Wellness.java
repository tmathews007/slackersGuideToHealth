package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class Wellness extends Activity {

    WebView webviewWellness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellness);

        webviewWellness = (WebView) findViewById(R.id.web_wellness);
        webviewWellness.loadUrl("https://www.eventbrite.com/e/health-wellness-expo-bronx-ny-tickets-32014487193?aff=es2");
    }

}
