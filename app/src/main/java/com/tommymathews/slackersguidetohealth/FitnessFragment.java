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
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import java.util.ArrayList;

public class FitnessFragment extends Fragment {
    private final String EXTRA_PLAYLIST_CREATED = "EXTRA_PLAYLIST_CREATED";

    private final int MATCH_DISTANCE = 10;

    private final int ABS = Color.BLUE;
    private final int BICEPS = Color.RED;
    private final int CHEST = Color.GREEN;
    private final int QUADS = Color.CYAN;
    private final int BACKVIEW = Color.YELLOW;

    private Button createWorkoutButton;
    private Button playlistWorkoutButton;

    private Fitness fitness;

    private Button createWorkout;

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

                switch(color) {
                    case ABS:
                        Log.d("Clicked", "ABS");
                        startActivity(new Intent(getActivity(), FitnessDescriptionActivity.class));
                        break;

                    case BICEPS:
                        Log.d("Clicked", "BICEPS");
                        startActivity(new Intent(getActivity(), FitnessDescriptionActivity.class));
                        break;

                    case CHEST:
                        Log.d("Clicked", "CHEST");
                        startActivity(new Intent(getActivity(), FitnessDescriptionActivity.class));
                        break;

                    case QUADS:
                        Log.d("Clicked", "QUADS");
                        startActivity(new Intent(getActivity(), FitnessDescriptionActivity.class));
                        break;

                    case BACKVIEW:
                        Log.d("Clicked", "BACKVIEW");
                        startActivity(new Intent(getActivity(), FitnessActivityBack.class));
                        break;
                }
                return true;
            }
        });

        createWorkoutButton = ( Button ) view.findViewById( R.id.create_workout_button );
        createWorkoutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessDescriptionActivity.class );

                Drawable step_1 = getResources().getDrawable(R.drawable.chest);
                Bitmap img = ((BitmapDrawable) step_1).getBitmap();

                Fitness test = new Fitness("Push ups", 2, 4, "Try hard to go up and down", img);

                ArrayList<String> temp = new ArrayList<String>();
                temp.add("Push up");
                temp.add("Push down");
                temp.add("Push around");

                Drawable image1 = getResources().getDrawable(R.drawable.biceps);
                Bitmap img1 = ((BitmapDrawable) image1).getBitmap();

                Drawable image2 = getResources().getDrawable(R.drawable.abs);
                Bitmap img2 = ((BitmapDrawable) image2).getBitmap();

                Drawable image3 = getResources().getDrawable(R.drawable.calves);
                Bitmap img3 = ((BitmapDrawable) image3).getBitmap();

                ArrayList<Bitmap> tempImgs = new ArrayList<Bitmap>();

                tempImgs.add(img1);
                tempImgs.add(img2);
                tempImgs.add(img3);

                test.setSteps(temp);
                test.setStepImages(tempImgs);

                DependencyFactory.getFitnessService(getActivity()).addFitness(test);


                intent.putExtra("ID", test.getId());
                startActivity( intent );
            }
        }
        );

        playlistWorkoutButton = ( Button ) view.findViewById( R.id.playlist_workout );
        playlistWorkoutButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity(), FitnessPlaylistBacklogActivity.class );
                startActivity( intent );
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

    /* Local comparator for colors.  For our purposes, the two colors only need to be
     * similar to each other.  In order to avoid problems from changes between devices or stretching
     * of the image, the colors can be within MATCH_DISTANCE of one another.
     *
     * Compares using the absolute values of individual RGB colors from color1 and color2.
     */
    private boolean match (int color1, int color2) {
        if ((((int) Math.abs(Color.red(color1) - Color.red(color2))) > MATCH_DISTANCE)  ||
                (((int) Math.abs(Color.green(color1) - Color.green(color2))) > MATCH_DISTANCE) ||
                (((int) Math.abs(Color.blue(color1) - Color.blue(color2))) > MATCH_DISTANCE)) {

            Log.d("Color1", "R" + Color.red(color1));
            Log.d("Color1", "G" + Color.green(color1));
            Log.d("Color1", "B" + Color.blue(color1));

            Log.d("Color2", "R" + Color.red(color2));
            Log.d("Color2", "G" + Color.green(color2));
            Log.d("Color2", "B" + Color.blue(color2));

            Log.d("Color", "Does not Equal");
            return false;

        } else {
            Log.d("Color1", "" + color1);
            Log.d("Color2", "" + color2);
            Log.d("Color", "Equals");

            return true;

        }
    }

}