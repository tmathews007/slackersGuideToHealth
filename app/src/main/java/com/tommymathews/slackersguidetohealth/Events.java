package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Events extends ActivityWithMenu {

    private TextView mTextMessage;
    public ListView listViewEvents;
    public TextView emotional, mental, physical;
    public static String yourState="";
    public static String yourCity="";
    EditText st, cy;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        st= (EditText) findViewById(R.id.state);
        cy = (EditText) findViewById(R.id.city);

        yourState=st.getText().toString();
        yourCity=cy.getText().toString();
        yourCity=yourCity.replaceAll(" ","-");

        emotional = (TextView) findViewById(R.id.emotionalHealth);
        mental = (TextView) findViewById(R.id.mentalHealth);
        physical = (TextView) findViewById(R.id.physicalHealth);

        emotional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourState=st.getText().toString();
                yourCity=cy.getText().toString();
                yourCity=yourCity.replaceAll(" ","-");
                startActivity(new Intent(Events.this, EmotionalHealth.class));
                overridePendingTransition(0,0);
            }
        });

        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourState=st.getText().toString();
                yourCity=cy.getText().toString();
                yourCity=yourCity.replaceAll(" ","-");
                startActivity(new Intent(Events.this, MentalHealth.class));
                overridePendingTransition(0,0);
            }
        });

        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yourState=st.getText().toString();
                yourCity=cy.getText().toString();
                yourCity=yourCity.replaceAll(" ","-");
                startActivity(new Intent(Events.this, PhysicalHealth.class));
                overridePendingTransition(0,0);
            }
        });
    }

}
