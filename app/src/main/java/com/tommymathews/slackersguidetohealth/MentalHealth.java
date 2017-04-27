package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class MentalHealth extends Activity {

    WebView webViewMental;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);

        webViewMental = (WebView) findViewById(R.id.web_health);
        String urlPart1 = "https://www.eventbrite.com/d/";
        String urlPart2 = "/mental-health/?crt=regular&sort=best";
        String location="md--silver-spring";
        webViewMental.loadUrl(urlPart1+location+urlPart2);
    }

}