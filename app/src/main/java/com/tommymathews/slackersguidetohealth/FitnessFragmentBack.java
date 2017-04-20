package com.tommymathews.slackersguidetohealth;

/**
 * Created by gregs on 4/19/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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

public class FitnessFragmentBack extends Fragment {
    private final int MATCH_DISTANCE = 10;

    private final int BACK = Color.BLUE;
    private final int CALFS = Color.CYAN;
    private final int GLUTES = Color.GREEN;
    private final int Shoulders = Color.RED;


    public static FitnessFragment newInstance() {
        FitnessFragment fragment = new FitnessFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fitness_fragment, container, false);

        Button frontButton = (Button) view.findViewById(R.id.front_button);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FitnessActivity.class));
            }
        });

        //random
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                int color = getColor(R.id.fitness_image_back, x, y);

                switch(color) {

                    case BACK:
                        Log.d("Clicked", "BACK");
                        startActivity(new Intent(getActivity(), BackActivity.class));
                        break;

                    case CALFS:
                        Log.d("Clicked", "CALFS");
                        startActivity(new Intent(getActivity(), CalfsActivity.class));
                        break;

                    case GLUTES:
                        Log.d("Clicked", "GLUTES");
                        startActivity(new Intent(getActivity(), GlutesActivity.class));
                        break;

                    case Shoulders:
                        Log.d("Clicked", "SHOULDERS");
                        startActivity(new Intent(getActivity(), ShouldersActivity.class));
                        break;



                }
                return true;
            }
        });
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
