package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.model.Playlist;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FitnessFragment extends Fragment {
    private final String EXTRA_PLAYLIST_CREATED = "EXTRA_PLAYLIST_CREATED";

    private final int MATCH_DISTANCE = 10;

    private final int ABS = Color.BLUE;
    private final int BICEPS = Color.RED;
    private final int CHEST = Color.GREEN;
    private final int QUADS = Color.CYAN;
    private final int BACKVIEW = Color.YELLOW;

    private Button createWorkoutButton;
    private Button backViewButton;

    public static Fragment newInstance() {
        FitnessFragment fragment = new FitnessFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fitness_fragment, container, false);

        //random
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int color = getColor(R.id.fitness_image_front, x, y);

                List<Fitness> fitnesses;
                Drawable thumbNail;
                File img;
                Playlist playlist;
                Intent intent;

                switch(color) {
                    case ABS:
                        Log.d("Clicked", "ABS");
                        fitnesses = DependencyFactory.getFitnessService(getActivity()).getFitnessesByBodyPart(Fitness.BodyPart.ABS);
                        img = new File ("android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.abs);
                        playlist = new Playlist("Ab Workouts" , img, fitnesses);
                        DependencyFactory.getPlaylistService(getActivity()).addPlaylist(playlist);

                        intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
                        intent.putExtra("ID", playlist.getId());
                        startActivity(intent);

                        break;

                    case BICEPS:
                        Log.d("Clicked", "BICEPS");
                        fitnesses = DependencyFactory.getFitnessService(getActivity()).getFitnessesByBodyPart(Fitness.BodyPart.BICEPS);
                        img = new File ("android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.biceps);
                        playlist = new Playlist("Bicep Workouts" , img, fitnesses);
                        DependencyFactory.getPlaylistService(getActivity()).addPlaylist(playlist);

                        intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
                        intent.putExtra("ID", playlist.getId());
                        startActivity(intent);
                        break;

                    case CHEST:
                        Log.d("Clicked", "CHEST");
                        fitnesses = DependencyFactory.getFitnessService(getActivity()).getFitnessesByBodyPart(Fitness.BodyPart.CHEST);
                        img = new File ("android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.chest);
                        playlist = new Playlist("Chest Workouts" , img, fitnesses);
                        DependencyFactory.getPlaylistService(getActivity()).addPlaylist(playlist);

                        intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
                        intent.putExtra("ID", playlist.getId());
                        startActivity(intent);
                        break;

                    case QUADS:
                        Log.d("Clicked", "QUADS");
                        fitnesses = DependencyFactory.getFitnessService(getActivity()).getFitnessesByBodyPart(Fitness.BodyPart.QUADS);
                        img = new File ("android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.quadriceps);
                        playlist = new Playlist("Quad Workouts" , img, fitnesses);
                        DependencyFactory.getPlaylistService(getActivity()).addPlaylist(playlist);

                        intent = new Intent(getActivity(), FitnessDescriptionActivity.class);
                        intent.putExtra("ID", playlist.getId());
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        createWorkoutButton = ( Button ) view.findViewById( R.id.create_workout_button );
        createWorkoutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessCreateActivity.class);
                startActivity( intent );
            }
        }
        );

        backViewButton = ( Button ) view.findViewById( R.id.back_view_button);
        backViewButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                Intent intent = new Intent( getActivity(), FitnessActivityBack.class);
                startActivity(intent);
                getActivity().overridePendingTransition( 0,0 );
            }
        }
        );



        return view;
    }

    //Gets the color of the pixel from an image with ID = id at coordinates (x,y)
    private int getColor (int id, int x, int y) {

        ImageView image = (ImageView) getView().findViewById(id);
        image.setDrawingCacheEnabled(true);
        Bitmap img = Bitmap.createBitmap(image.getDrawingCache());
        image.setDrawingCacheEnabled(false);

        return img.getPixel(x, y);

    }


}