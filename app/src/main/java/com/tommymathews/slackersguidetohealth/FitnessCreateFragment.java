package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import java.io.IOException;
import java.util.ArrayList;
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
    private String image;
    private List<String> images;
    private List<String> steps;
    private int counter;

    EditText stepText;
    ImageView stepImage;


    public static FitnessCreateFragment newInstance() {
       FitnessCreateFragment fragment = new FitnessCreateFragment();

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
        image = getActivity().getIntent().getStringExtra("PHOTO");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_create_fragment, container, false);


        stepText = (EditText)view.findViewById(R.id.fitness_edit_description);
        stepImage = (ImageView)view.findViewById(R.id.fitness_thumbnail);

        String id = getActivity().getIntent().getStringExtra("ID");
        if (id != null) {
            fitness = DependencyFactory.getFitnessService(getActivity()).getFitnessById(id);

            images = fitness.getStepImages();
            steps = fitness.getSteps();

            stepText.setText(steps.get(counter));
            stepImage.setImageBitmap(retBitmap(images.get(counter)));
        } else {
            images = new ArrayList<>();
            steps = new ArrayList<>();
        }

        backButton= (Button) view.findViewById(R.id.last_step_creation_button);
        backButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (counter > 1) {
                    --counter;
                    stepText.setText(fitness.getFitnessName());
                    stepImage.setImageBitmap(retBitmap(images.get(counter)));
                } else {
                    getActivity().finish();
                }
            }

        });

        nextButton = (Button) view.findViewById(R.id.next_step_creation_button);
        nextButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (checkInput()) {
                    if (counter < 9) {
                        images.set(counter, "");
                        steps.set(counter, stepText.getText().toString());

                        ++counter;
                        if (counter <= images.size() && counter <= steps.size()) {
                            stepText.setText(steps.get(counter));
                            stepImage.setImageBitmap(retBitmap(images.get(counter)));
                        } else {
                            stepText.setText("");
                            stepImage.setImageDrawable(null);
                        }
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

                fitness = new Fitness(name, bodyPart, reps, description, image);
                fitness.setStepImages(images);
                fitness.setSteps(steps);

                DependencyFactory.getFitnessService(getActivity()).addFitness(fitness);
                Intent intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
                intent.putExtra("ID", fitness.getId());
                getActivity().finish();
                startActivity(intent);
            }

        });

        return view;
    }

    private boolean checkInput() {
        if (!stepText.getText().toString().isEmpty() || !(stepImage.getDrawable() == null)) {
            return false;
        } else {
            return true;
        }
    }

    private Bitmap retBitmap(String i) {
        Uri uri = Uri.parse(fitness.getStepImages().get(counter));
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
