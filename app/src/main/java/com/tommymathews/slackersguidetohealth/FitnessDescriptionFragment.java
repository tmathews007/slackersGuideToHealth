package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Fitness;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessDescriptionFragment  extends Fragment{

    Fitness fitness;
    TextView title;
    ImageView image;
    TextView description;
    Button lookAtFitnessButton;

    public static Fragment newInstance() {
        Fragment fragment = new FitnessDescriptionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_description_fragment, container, false);

        title = (TextView) view.findViewById(R.id.description_title);
        image = (ImageView) view.findViewById(R.id.description_image);
        description = (TextView) view.findViewById(R.id.description_text);

        /*
        Implement when you actually use this
        title.setText("This is a test title");
        image.setImageBitmap();
        description.setText("this is the discription");
        */

        lookAtFitnessButton = (Button) view.findViewById(R.id.look_at_fitness_button);
        lookAtFitnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DisplayFitnessSteps.class));
            }
        });

        return view;
    }
}
