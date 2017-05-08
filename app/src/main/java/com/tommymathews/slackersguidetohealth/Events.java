package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.util.HashMap;
import java.util.Map;

public class Events extends ActivityWithMenu {

//    private static final Map<String, String> states = new HashMap<String, String>();
//    static {
//        states.put("Alabama","AL");
//        states.put("Alaska","AK");
//        states.put("Arizona","AZ");
//        states.put("Arkansas","AR");
//        states.put("California","CA");
//        states.put("Colorado","CO");
//        states.put("Connecticut","CT");
//        states.put("Delaware","DE");
//        states.put("District Of Columbia","DC");
//        states.put("Florida","FL");
//        states.put("Georgia","GA");
//        states.put("Hawaii","HI");
//        states.put("Idaho","ID");
//        states.put("Illinois","IL");
//        states.put("Indiana","IN");
//        states.put("Iowa","IA");
//        states.put("Kansas","KS");
//        states.put("Kentucky","KY");
//        states.put("Louisiana","LA");
//        states.put("Maine","ME");
//        states.put("Maryland","MD");
//        states.put("Massachusetts","MA");
//        states.put("Michigan","MI");
//        states.put("Minnesota","MN");
//        states.put("Mississippi","MS");
//        states.put("Missouri","MO");
//        states.put("Montana","MT");
//        states.put("Nebraska","NE");
//        states.put("Nevada","NV");
//        states.put("New Hampshire","NH");
//        states.put("New Jersey","NJ");
//        states.put("New Mexico","NM");
//        states.put("New York","NY");
//        states.put("North Carolina","NC");
//        states.put("North Dakota","ND");
//        states.put("Ohio","OH");
//        states.put("Oklahoma","OK");
//        states.put("Oregon","OR");
//        states.put("Pennsylvania","PA");
//        states.put("Rhode Island","RI");
//        states.put("South Carolina","SC");
//        states.put("South Dakota","SD");
//        states.put("Tennessee","TN");
//        states.put("Texas","TX");
//        states.put("Utah","UT");
//        states.put("Vermont","VT");
//        states.put("Virginia","VA");
//        states.put("Washington","WA");
//        states.put("West Virginia","WV");
//        states.put("Wisconsin","WI");
//        states.put("Wyoming","WY");
//    }

    private final String[] STATES = {
            "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL", "IN",
            "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
            "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "TN", "TX", "UT", "VT",
            "VA", "WA", "WV", "WI", "WY"
    };

    public ImageView emotional, mental, physical;
    public static String yourState="";
    public static String yourCity="";
    private EditText sy, cy;
    private AutoCompleteTextView stateAbbreviationAutoFill;
    public static final int REQUEST_CODE_EVENT_WEBSITE= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        sy = (EditText) findViewById( R.id.states );
        cy = (EditText) findViewById(R.id.city);

//        stateAbbreviationAutoFill = ( AutoCompleteTextView ) findViewById( R.id.states_list );
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, STATES );
//        stateAbbreviationAutoFill.setAdapter(adapter);

        emotional = (ImageView) findViewById(R.id.emotional);
        mental = (ImageView) findViewById(R.id.mental);
        physical = (ImageView) findViewById(R.id.physical);

        emotional.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //necessary because the system won't recognize changes unless you
                //update the variables again
                yourState=sy.getText().toString();
//                yourState=stateAbbreviationAutoFill.getText().toString();
                yourCity=cy.getText().toString().replaceAll(" ","-");
                startActivityForResult(new Intent(Events.this, EmotionalHealth.class), REQUEST_CODE_EVENT_WEBSITE);
                overridePendingTransition(0,0);
            }
        });

        mental.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //necessary because the system won't recognize changes unless you
                //update the variables again
                yourState=sy.getText().toString();
//                yourState=stateAbbreviationAutoFill.getText().toString();
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
                yourState=sy.getText().toString();
//                yourState=stateAbbreviationAutoFill.getText().toString();
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
        new Handler().postDelayed(new Runnable() {
            public void run() {

                new AlertDialog.Builder(Events.this)
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
        }, 1000);

    }

}