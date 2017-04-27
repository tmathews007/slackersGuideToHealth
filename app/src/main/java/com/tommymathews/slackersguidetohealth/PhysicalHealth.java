package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PhysicalHealth extends Activity {

    WebView webViewHealth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_health);

        webViewHealth = (WebView) findViewById(R.id.web_health);
        webViewHealth.loadUrl("https://www.eventbrite.com/d/md--silver-spring/physical-health/?crt=regular&sort=best");
    }

}
