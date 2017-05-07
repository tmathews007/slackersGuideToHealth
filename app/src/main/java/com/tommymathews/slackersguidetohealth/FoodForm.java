package com.tommymathews.slackersguidetohealth;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.io.File;

public class FoodForm extends ActivityWithMenu {
    private final static int REQUEST_CODE_RECIPE = 0;
    private final static int REQUEST_CODE_SUBMIT_RECIPE = 0;

    private ImageView iv;
    private ImageButton ib;
    private EditText recipeTitle;
    private EditText recipeCalories;
    private EditText recipeIngredients;
    private EditText recipeInstructions;
    private Button submit;
    private Uri uri;

    private SQLiteDatabase database;

    private static final int REQUEST_PHOTO = 300;
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_form);

        ib = (ImageButton) findViewById(R.id.camera);
        iv = (ImageView) findViewById(R.id.photo);

        recipeTitle = (EditText) findViewById(R.id.recipeTitle);
        recipeCalories = (EditText) findViewById(R.id.recipeCalories);
        recipeIngredients = (EditText) findViewById(R.id.recipeIngredients);
        recipeInstructions = (EditText) findViewById(R.id.recipeInstructions);

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
                        uri = Uri.fromFile(f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, REQUEST_PHOTO);
                    }
                }
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInputs()) {
                    int cal = Integer.parseInt(recipeCalories.getText().toString());
                    Food food = new Food(cal, "", recipeTitle.getText().toString(), recipeIngredients.getText().toString(), "");
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
        File ePD = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (ePD==null) {
            return null;
        }

        return new File(ePD, "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_PHOTO && resultCode == RESULT_OK ) {
            Uri selectedImageUri = (data == null) ? uri : data.getData();
            if( selectedImageUri != null ) {
//                if( requestCode == REQUEST_PHOTO ) {
//                    Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE );
//                    mediaScanIntent.setData( selectedImageUri );
//                    this.sendBroadcast( mediaScanIntent );
//                }
                Cursor cursor = getContentResolver().query( uri,
                        new String[] {
                                MediaStore.Images.ImageColumns.DATA
                        },
                        null,
                        null,
                        null,
                        null
                );
                cursor.moveToFirst();
                String imageFilePath = cursor.getString( 0 );
                cursor.close();

                if( imageFilePath != null ) {
                    database.execSQL("INSERT INTO TABLE_NAME VALUES(' "+imageFilePath+" ');");
                }
            }
        }
    }

    private boolean checkInputs() {
        return recipeTitle.getText().length() > 0 &&
                recipeCalories.getText().length() > 0 &&
                recipeInstructions.getText().length() > 0 &&
                recipeIngredients.getText().length() > 0;
    }
}
