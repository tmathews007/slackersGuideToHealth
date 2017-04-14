package com.tommymathews.slackersguidetohealth;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Ashwin on 4/14/2017.
 */

public class AbsFragment extends Fragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=null; //inflate with fragment_abs.xml

        //TODO the stuff below
        //button/etc absWorkoutsButton;
        /*absWorkoutsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), AbsActivity.class);
                startActivity(i);
            }
        });*/
        return view;
    }
}
