package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MentalHealth extends Activity {

    WebView webviewWellness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        webviewWellness = (WebView) findViewById(R.id.web_health);
        webviewWellness.loadUrl("https://www.eventbrite.com/d/md--silver-spring/mental-health/?crt=regular&sort=best");
    }

}
