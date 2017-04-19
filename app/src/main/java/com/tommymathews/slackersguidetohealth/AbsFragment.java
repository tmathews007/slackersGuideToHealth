package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbsFragment extends android.support.v4.app.Fragment {

    private TextView absWorkoutTitle;
    private TextView absWorkout;

    public static AbsFragment newInstance() {
        AbsFragment fragment = new AbsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_abs, container, false);

        // This will be the title of the page. The title will be the workout name.
        absWorkoutTitle = ( TextView ) view.findViewById( R.id.abs_workout_title );

        // This will be where the workout directions are. It will be in the third textview, while
        // the picture of the workout will be in the above textview.
        absWorkout = ( TextView ) view.findViewById( R.id.abs_workout );
        absWorkout.setText( R.array.abs_workout_array );

        return view;
    }
}
