package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tommymathews.slackersguidetohealth.model.Fitness;

import java.io.File;
import java.util.List;

/**
 * Created by gregs on 5/8/2017.
 */

public class FitnessCreateFragment extends Fragment {
    public final static String FITNESS_ID = "FITNESS_ID";
    private Fitness fitness;

    private Button cancelButton;
    private Button submitButton;
    private Button nextButton;
    private Button backButton;

    private String name;
    private int bodyPart;
    private double reps;
    private String description;
    private File image;
    private List<File> images;
    private List<String> steps;
    private int counter;

    EditText stepText;
    ImageView stepImage;


    public static FitnessBacklogFragment newInstance() {
        FitnessBacklogFragment fragment = new FitnessBacklogFragment();

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        counter = 0;
        name = getActivity().getIntent().getStringExtra("NAME");
        bodyPart = getActivity().getIntent().getIntExtra("BODYPART", 0);
        reps = getActivity().getIntent().getDoubleExtra("REPS", 1.0);
        description = getActivity().getIntent().getStringExtra("DESCRIPTION");
        image = new File(getActivity().getIntent().getStringExtra("PHOTO"));

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fitness_backlog, container, false);



        stepText = (EditText)view.findViewById(R.id.fitness_edit_description);
        if (fitness != null) {
            stepText.setText(fitness.getFitnessName());
        }

        backButton= (Button) view.findViewById(R.id.last_step_creation_button);
        submitButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }

        });

        nextButton = (Button) view.findViewById(R.id.next_step_creation_button);
        submitButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!stepText.getText().toString().isEmpty() ||
                    !(stepImage.getDrawable() == null)) {

                    if (counter < 10) {
                        if (counter <= images.size() && counter <= steps.size()) {
                            stepText.setText(steps.get(counter));
                            stepImage.setImageBitmap(BitmapFactory.decodeFile(images.get(counter).getPath()));
                        } else {
                            stepText.setText("");
                            stepImage.setImageDrawable(null);
                        }
                        ++counter;
                    } else {

                    }
                }
            }

        });

        cancelButton = (Button) view.findViewById(R.id.cancel_steps_button);
        cancelButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }

        });

        submitButton = (Button) view.findViewById(R.id.save_fitness_button);
        submitButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                fitness.setSteps(steps);

                DependencyFactory.getFitnessService(getActivity()).addFitness(fitness);
                Intent intent = new Intent(getActivity(), FitnessPlaylistActivity.class);
                intent.putExtra("ID", fitness.getId());
                getActivity().finish();
                startActivity(intent);
            }

        });

        return view;
    }
}
