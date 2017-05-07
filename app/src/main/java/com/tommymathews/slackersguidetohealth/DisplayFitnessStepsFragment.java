package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tommymathews.slackersguidetohealth.model.Fitness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gregs on 4/26/2017.
 */

public class DisplayFitnessStepsFragment extends android.support.v4.app.Fragment {
    /*FitnessService serve = DependencyFactory. getFitnessService(getActivity());*/
    Fitness fitness;

    Bitmap images[] ={null, null, null};
    List<String> steps;
    Button nextButton;
    Button previousButton;
    TextView stepText;
    ImageView stepImage;
    int stepCounter = 0;


    public static DisplayFitnessStepsFragment newInstance() {
        DisplayFitnessStepsFragment fragment = new DisplayFitnessStepsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        String id = getActivity().getIntent().getStringExtra("ID");
        fitness = DependencyFactory.getFitnessService(getContext()).getFitnessById(id);

        //testing temp file
        Drawable step_1 = getResources().getDrawable(R.drawable.chest);
        Bitmap step1 = ((BitmapDrawable) step_1).getBitmap();
        Drawable step_2 = getResources().getDrawable(R.drawable.abs);
        Bitmap step2 = ((BitmapDrawable) step_2).getBitmap();
        Drawable step_3 = getResources().getDrawable(R.drawable.back);
        Bitmap step3 = ((BitmapDrawable) step_3).getBitmap();
        images[0] = step1;
        images[1] = step2;
        images[2] = step3;

        steps = fitness.getSteps();

        View view = inflater.inflate(R.layout.display_fitness_steps_fragment, container, false);

        stepImage = (ImageView) view.findViewById(R.id.step_image);
        stepText = (TextView) view.findViewById(R.id.step_text);

        stepImage.setImageBitmap(images[stepCounter]);
        stepText.setText(steps.get(stepCounter));

        Button nextButton = (Button) view.findViewById(R.id.next_step_button);
        final Button previousButton = (Button) view.findViewById(R.id.previous_step_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ++stepCounter;
                if (stepCounter < steps.size()) {
                    stepImage.setImageBitmap(images[stepCounter]);
                    stepText.setText(steps.get(stepCounter));
                    previousButton.setVisibility(View.VISIBLE);
                } else {
                    startActivity(new Intent(getActivity(), FitnessFinishedActivity.class));
                }
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                --stepCounter;
                stepImage.setImageBitmap(images[stepCounter]);
                stepText.setText(steps.get(stepCounter));
                if (stepCounter <= 0) {
                    previousButton.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }
}
