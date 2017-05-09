package com.tommymathews.slackersguidetohealth;

/**
 * Created by gregs on 4/19/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
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

import java.io.File;
import java.util.List;

public class FitnessFragmentBack extends Fragment {
    private final int MATCH_DISTANCE = 10;

    private final int BACK = Color.BLUE;
    private final int CALFS = Color.CYAN;
    private final int GLUTES = Color.GREEN;
    private final int SHOULDERS = Color.RED;

    private Button frontViewButton;

    public static Fragment newInstance() {
        FitnessFragmentBack fragment = new FitnessFragmentBack();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fitness_fragment_back, container, false);

        //random
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    int color = getColor(R.id.fitness_image_back, x, y);

                    List<Fitness> fitnesses;
                    File img;
                    Playlist playlist;
                    Intent intent;

                    switch (color) {

                        case BACK:
                            Log.d("Clicked", "BACK");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.BACK.ordinal());
                            startActivity(intent);
                            break;

                        case CALFS:
                            Log.d("Clicked", "CALFS");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.CALFS.ordinal());
                            startActivity(intent);
                            break;

                        case GLUTES:
                            Log.d("Clicked", "GLUTES");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.GLUTES.ordinal());
                            startActivity(intent);
                            break;

                        case SHOULDERS:
                            Log.d("Clicked", "SHOULDERS");
                            intent = new Intent(getActivity(), FitnessBacklogActivity.class);
                            intent.putExtra("BODYPART", Fitness.BodyPart.SHOULDERS.ordinal());
                            startActivity(intent);
                            break;
                    }
                    return true;
                }
                return false;
            }
        });

        frontViewButton = ( Button ) view.findViewById( R.id.front_view_button);
        frontViewButton.setOnClickListener( new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   Intent intent = new Intent( getActivity(), FitnessActivity.class);
                                                   getActivity().finish();
                                                   startActivity(intent);
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
