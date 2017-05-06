package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

import static java.security.AccessController.getContext;

public class FoodForm extends ActivityWithMenu {
    ImageView iv;
    ImageButton ib;
    static final int REQUEST_PHOTO = 300;
    File f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_form);

        ib = (ImageButton) findViewById(R.id.camera);
        iv = (ImageView) findViewById(R.id.photo);

        ib.setEnabled(true);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) == null)
                    ib.setEnabled(false);
                else {
                    f = getPhotoFile();
                    if (f==null)
                        ib.setEnabled(false);
                    else {
                        Uri uri = Uri.fromFile(f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_PHOTO);
                    }
                }
            }
        });
    }

    private File getPhotoFile(){
        File ePD = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (ePD==null) {
            return null;
        }

        return new File(ePD, "IMG_" + System.currentTimeMillis() + ".jpg");
    }
}
