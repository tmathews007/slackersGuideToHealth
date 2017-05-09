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
    private final int ABS = Color.BLUE;
    private final int BICEPS = Color.RED;
    private final int CHEST = Color.GREEN;
    private final int QUADS = Color.CYAN;

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
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int color = getColor(R.id.fitness_image_front, x, y);

                    List<Fitness> fitnesses;
                    File img;
                    Playlist playlist;
                    Intent intent;

                    switch (color) {
                        case ABS:
                            Log.d("Clicked", "ABS");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.ABS.ordinal());
                            startActivity(intent);
                            break;

                        case BICEPS:
                            Log.d("Clicked", "BICEPS");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.BICEPS.ordinal());
                            startActivity(intent);
                            break;

                        case CHEST:
                            Log.d("Clicked", "CHEST");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.CHEST.ordinal());
                            startActivity(intent);
                            break;

                        case QUADS:
                            Log.d("Clicked", "QUADS");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.QUADS.ordinal());
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
                return false;
            }
        });

        createWorkoutButton = ( Button ) view.findViewById( R.id.create_workout_button );
        createWorkoutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessQuestionsActivity.class);
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