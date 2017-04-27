package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class EmotionalHealth extends Activity {
    WebView webViewEmotional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        webViewEmotional = (WebView) findViewById(R.id.web_health);
        String urlPart1 = "https://www.eventbrite.com/d/";
        String urlPart2 = "/emotional-health/?crt=regular&sort=best";
        String location="md--silver-spring";
        webViewEmotional.loadUrl(urlPart1+location+urlPart2);
    }

}