package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.tommymathews.slackersguidetohealth.model.Food;
import com.tommymathews.slackersguidetohealth.service.FoodService;

import java.io.File;

public class FoodForm extends ActivityWithMenu {

    private static final int REQUEST_PHOTO = 800;

    private ImageView imageView;
    private ImageButton imageButton;
    private EditText recipeTitle;
    private EditText recipeCalories;
    private EditText recipeIngredients;
    private EditText recipeInstructions;
    private Button submit;
    private Uri uri;
    private Bitmap bitmap;

    private final String TAG = "FoodForm";

    private SQLiteDatabase database;

    private File photoFile;
    private String mCurrentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_form);

        imageButton = (ImageButton) findViewById(R.id.camera);
        imageView = (ImageView) findViewById(R.id.photo);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if (intent.resolveActivity(getPackageManager()) != null){
                    photoFile = getPhotoFile();
                    if (photoFile != null) {
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(intent, REQUEST_PHOTO);
                    } else {
                        imageButton.setEnabled(false);
                    }
                } else {
                    imageButton.setEnabled(false);
                }
            }
        });

        recipeTitle = (EditText) findViewById(R.id.recipeTitle);
        recipeCalories = (EditText) findViewById(R.id.recipeCalories);
        recipeIngredients = (EditText) findViewById(R.id.recipeIngredients);
        recipeInstructions = (EditText) findViewById(R.id.recipeInstructions);
        mCurrentPhotoPath = "";

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputs()) {
                    int cal = Integer.parseInt(recipeCalories.getText().toString());
                    Food food = new Food(cal, "", recipeTitle.getText().toString(), recipeIngredients.getText().toString(),
                            recipeInstructions.getText().toString(), mCurrentPhotoPath);
                    FoodService foodService = DependencyFactory.getFoodService(getApplication());
                    foodService.addFood(food);
                    Intent intent = new Intent(FoodForm.this, FoodMain.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(FoodForm.this, "Recipe is incomplete!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private File getPhotoFile(){
        File externalPhotoDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalPhotoDir == null){
            return null;
        }

        return new File(externalPhotoDir, "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PHOTO) {
            bitmap = BitmapFactory.decodeFile(photoFile.getPath());
            mCurrentPhotoPath = photoFile.getPath();
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean checkInputs() {
        return recipeTitle.getText().length() > 0 &&
                recipeCalories.getText().length() > 0 &&
                recipeInstructions.getText().length() > 0 &&
                recipeIngredients.getText().length() > 0;
    }


}
