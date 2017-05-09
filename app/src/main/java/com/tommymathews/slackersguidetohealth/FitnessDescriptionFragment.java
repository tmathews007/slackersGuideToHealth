package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.IOException;

/**
 * Created by gregs on 4/26/2017.
 */

public class FitnessDescriptionFragment  extends Fragment{
    private static final String EXTRA_PLAYLIST_CREATED = "PlaylistCreated";

    private Fitness fitness;
    String id;
    TextView title;
    ImageView image;
    TextView description;
    Button backButton;
    Button lookAtFitnessButton;

    public static Fragment newInstance() {
        Fragment fragment = new FitnessDescriptionFragment();
        return fragment;
    }

    public static Fitness getFitnessCreated(Intent data) {
        return (Fitness) data.getSerializableExtra(EXTRA_PLAYLIST_CREATED);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getActivity().getIntent().getStringExtra("ID");
        fitness = DependencyFactory.getFitnessService(getActivity()).getFitnessById(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_description_fragment, container, false);

        id = getActivity().getIntent().getStringExtra("ID");

        Log.d("Fitness Id is", id);

        title = (TextView) view.findViewById(R.id.description_title);
        if( title != null ) {
            title.setText(fitness.getFitnessName());
        }

        image = (ImageView) view.findViewById(R.id.description_image);
        if( image != null ) {
            Uri uri = Uri.parse(fitness.getImage());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            image.setImageBitmap(bitmap);
        }

        description = (TextView) view.findViewById(R.id.description_text);
        if( description != null ) {
            description.setText(fitness.getInstructions());
        }

        backButton = (Button) view.findViewById(R.id.back_to_fitness_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getActivity().finish();
            }
        });

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
