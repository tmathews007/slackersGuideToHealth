package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

public class Events extends ActivityWithMenu {

    public ImageView emotional, mental, physical;
    public static String yourState="";
    public static String yourCity="";
    EditText st, cy;
    public static final int REQUEST_CODE_EVENT_WEBSITE= 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        st= (EditText) findViewById(R.id.state);
        cy = (EditText) findViewById(R.id.city);

        emotional = (ImageView) findViewById(R.id.emotional);
        mental = (ImageView) findViewById(R.id.mental);
        physical = (ImageView) findViewById(R.id.physical);

        emotional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //necessary because the system won't recognize changes unless you
                //update the variables again
                yourState=st.getText().toString();
                yourCity=cy.getText().toString().replaceAll(" ","-");
//                String url = "https://www.eventbrite.com/d/" + yourState + "--" + yourCity + "/emotional-health/?crt=regular&sort=best";
//                Intent intent = new Intent( Intent.ACTION_VIEW );
//                intent.setData( Uri.parse( url ) );
//                startActivity( intent );
                startActivityForResult(new Intent(Events.this, EmotionalHealth.class), REQUEST_CODE_EVENT_WEBSITE);
                overridePendingTransition(0,0);
            }
        });

        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //necessary because the system won't recognize changes unless you
                //update the variables again
                yourState=st.getText().toString();
                yourCity=cy.getText().toString().replaceAll(" ","-");
                startActivityForResult(new Intent(Events.this, MentalHealth.class), REQUEST_CODE_EVENT_WEBSITE);
                overridePendingTransition(0,0);
            }
        });

        physical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //necessary because the system won't recognize changes unless you
                //update the variables again
                yourState=st.getText().toString();
                yourCity=cy.getText().toString().replaceAll(" ","-");
                startActivityForResult(new Intent(Events.this, PhysicalHealth.class), REQUEST_CODE_EVENT_WEBSITE);
                overridePendingTransition(0,0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_EVENT_WEBSITE && resultCode == Activity.RESULT_CANCELED)
            dialog();
    }


    public void dialog(){
        new AlertDialog.Builder(this)
                .setTitle("Give Your Thoughts").setMessage("Did you like any events that you viewed?")
                .setNegativeButton("no", null).setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp = getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);
                String username = sp.getString(DbSchema.EMAIL, null);
                DependencyFactory.getUserService(getApplicationContext()).incrementEventsProgress(username);
            }
        }).create().show();
    }

}