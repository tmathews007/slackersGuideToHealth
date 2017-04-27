package com.tommymathews.slackersguidetohealth;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShouldersFragment extends Fragment {

    private TextView shouldersWorkoutTitle;
    private TextView shouldersWorkout;

    public static ShouldersFragment newInstance() {
        ShouldersFragment fragment = new ShouldersFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shoulders, container, false);

        // This will be the title of the page. The title will be the workout name.
        shouldersWorkoutTitle = ( TextView ) view.findViewById( R.id.shoulders_workout_title );

//        // This will be where the workout directions are. It will be in the third textview, while
//        // the picture of the workout will be in the above textview.
//        shouldersWorkout = ( TextView ) view.findViewById( R.id.shoulders_workout );
//        shouldersWorkout.setText( R.array.shoulder_workout_array );

        return view;
    }

}
