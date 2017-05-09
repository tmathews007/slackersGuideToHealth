package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    String id;
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

        id = getActivity().getIntent().getStringExtra("ID");

        Log.d("Fitness Id is", id);

        Fitness temp = DependencyFactory.getFitnessService(getActivity()).getFitnessById(id);

        title = (TextView) view.findViewById(R.id.description_title);
        title.setText(temp.getFitnessName());

        image = (ImageView) view.findViewById(R.id.description_image);
        image.setImageBitmap(temp.getImage());

        description = (TextView) view.findViewById(R.id.description_text);
        description.setText(temp.getInstructions());

        lookAtFitnessButton = (Button) view.findViewById(R.id.look_at_fitness_button);
        lookAtFitnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent temp = new Intent(getActivity(), DisplayFitnessSteps.class);
                temp.putExtra("ID", id);
                startActivity(temp);
            }
        });

        return view;
    }
}
