package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class EmotionalHealth extends Activity {
    WebView webviewDance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        webviewDance = (WebView) findViewById(R.id.web_health);
        webviewDance.loadUrl("https://www.eventbrite.com/d/md--silver-spring/emotional-health/?crt=regular&sort=best");
    }

}
