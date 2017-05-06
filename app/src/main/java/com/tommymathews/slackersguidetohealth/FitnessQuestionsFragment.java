package com.tommymathews.slackersguidetohealth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.UserService;
import com.tommymathews.slackersguidetohealth.service.impl.DbSchema;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class FitnessQuestionsFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private static final String EXTRA_FITNESS_CREATED = "FitnessCreated";
    private final static String ARG_FITNESS_ID = "FitnessId";
    private final static int REQUEST_PHOTO = 1;

    private String photoPathName;

    private Fitness fitness;

    private EditText fitnessName;
    private Spinner bodyPartSelection;
    private EditText numReps;
    private EditText instructions;
    private ImageButton cameraImage;
    private ImageView photoView;
    private Button saveButton;
    private Button cancelButton;
    private Bitmap bitmap;
    private UserService userService;

    private File photoFile;

    public static FitnessQuestionsFragment newInstance( String fitnessId ) {
        Bundle args = new Bundle();
        args.putString( ARG_FITNESS_ID, fitnessId );
        Log.d( "TAG", "ARG_FITNESS_ID: " + args.toString() );

        FitnessQuestionsFragment fragment = new FitnessQuestionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String fitnessId = getArguments().getString( ARG_FITNESS_ID );
        fitness = DependencyFactory.getFitnessService( getActivity().getApplicationContext() ).getFitnessByName( fitnessId );
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_fitness_questions, container, false );

        Log.d( TAG, "FitnessQuestionsFragment - fitnessName edit text created" );
        fitnessName = ( EditText ) view.findViewById( R.id.fitness_name_edit );
        if( fitness != null ) {
            Log.d( TAG, "FitnessQuestionsFragment - fitnessNameEdit has been set" );
            fitnessName.setText( fitness.getFitnessName() );
        }

        Log.d( TAG, "FitnessQuestionsFragment - bodyPart spinner has been created" );
        bodyPartSelection = ( Spinner ) view.findViewById( R.id.spinner_fitness );
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource( getActivity(), R.array.spinner_body_part, android.R.layout.simple_spinner_item );
        statusAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        bodyPartSelection.setAdapter(statusAdapter);
        if (fitness != null) {
            Log.d( TAG, "FitnessQuestionsFragment - bodyPart for this workout is: " + fitness.getBodyPart() );
            Log.d( TAG, "FitnessQuestionsFragment - bodyPartEdit has been set" );
            bodyPartSelection.setSelection( fitness.getBodyPartPosition() );
        }

        Log.d( TAG, "FitnessQuestionsFragment - numReps edit text created" );
        numReps = ( EditText ) view.findViewById( R.id.num_reps_edit );
        if( fitness != null ) {
            Log.d( TAG, "FitnessQuestionsFragment - numRepsEdit has been set" );
            numReps.setText( "" + fitness.getNumReps() );
        }

        Log.d( TAG, "FitnessQuestionsFragment - instructions edit created (note: this is a multitext" );
        instructions = ( EditText ) view.findViewById( R.id.instructions_edit );
        if( fitness != null ) {
            Log.d( TAG, "FitnessQuestionsFragment - instructionsEdit has been set" );
            instructions.setText( fitness.getInstructions() );
        }

        Log.d( TAG, "FitnessQuestionsFragment - cameraImage created" );
        cameraImage = ( ImageButton ) view.findViewById( R.id.camera );
        cameraImage.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d( TAG, "FitnessQuestionsFragment - checking for permission" );
//                int permissionCheck = ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.CAMERA );
//                Log.d( TAG, "FitnessQuestionsFragment - permission given" );
//                if( permissionCheck != PackageManager.PERMISSION_GRANTED ) {
//                    Log.d( TAG, "FitnessQuestionsFragment - permission given" );
//                    ActivityCompat.requestPermissions( getActivity(), new String[]{ Manifest.permission.CAMERA }, REQUEST_PHOTO );
//                } else {
                  returnIntent();
//                }
            }
        }
        );

        Log.d( TAG, "FitnessQuestionsFragment - photoView created" );
        photoView = ( ImageView ) view.findViewById( R.id.photo );

        saveButton = ( Button ) view.findViewById( R.id.save_fitness_button );
        saveButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( fitness == null ) {
                    fitness = new Fitness( null, 0, 0, null, null );
                }
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(DbSchema.LOGIN, MODE_PRIVATE);

                userService = DependencyFactory.getUserService(getActivity().getApplicationContext());
                String email = sharedPreferences.getString(DbSchema.EMAIL,null);
                userService.incrementFitnessProgress(email);

                fitness.setFitnessName( fitnessName.getText().toString() );
                fitness.setBodyPartPosition( bodyPartSelection.getSelectedItemPosition() );
                fitness.setNumReps( Double.parseDouble( numReps.getText().toString() ));
                fitness.setInstructions( instructions.getText().toString() );
                if( bitmap == null ) {
                    bitmap = null;
                } else {
                    fitness.setImage( bitmap );
                }
//                fitness.setImage( cameraImage.getDrawingCache() );

                Intent data = new Intent();
                data.putExtra( EXTRA_FITNESS_CREATED, fitness );
                getActivity().setResult(RESULT_OK, data);
                getActivity().finish();
            }
        });

        cancelButton = (Button)view.findViewById( R.id.cancel_fitness_button );
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                getActivity().setResult( RESULT_CANCELED );
                getActivity().finish();
            }
        });

        return view;
    }

    private void returnIntent() {
        Log.d( TAG, "FitnessQuestionsFragment - cameraIntent made" );
        Intent takePicture = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        Log.d( TAG, "FitnessQuestionsFragment - checking if the intent is null" );
        if ( takePicture.resolveActivity( getActivity().getPackageManager() ) != null ) {
            Log.d( TAG, "FitnessQuestionsFragment - intent is not null" );
            photoFile = getPhotoFile();
            Log.d( TAG, "FitnessQuestionsFragment - photoFile received" );
            if( photoFile != null ) {
                Uri photoURI = Uri.fromFile( photoFile );
                takePicture.putExtra( MediaStore.EXTRA_OUTPUT, photoURI );
                startActivityForResult( takePicture, REQUEST_PHOTO );
            } else {
                cameraImage.setEnabled( false );
            }
        } else {
            Log.d( TAG, "FitnessQuestionsFragment - intent is null" );
            cameraImage.setEnabled( false );
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( requestCode == REQUEST_PHOTO && resultCode == Activity.RESULT_OK ) {
            bitmap = BitmapFactory.decodeFile( photoFile.getPath() );
            Log.d( TAG, "FitnessQuestionsFragment - Since the image was accepted, photoView has been set" );
            photoView.setImageBitmap( bitmap );
            galleryAddPic( getContext(), photoPathName );
        }
    }

    public static Fitness getFitnessCreated(Intent data) {
        return ( Fitness ) data.getSerializableExtra( EXTRA_FITNESS_CREATED );
    }

    private File getPhotoFile(){
        File externalPhotoDir = getActivity().getExternalFilesDir( Environment.DIRECTORY_PICTURES );
        if( externalPhotoDir == null ) {
            return null;
        }
        return new File( externalPhotoDir, "IMG_" + System.currentTimeMillis() + ".jpg" );
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_PHOTO: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    returnIntent();
//                } else {
//                    cameraImage.setEnabled( false );
//                }
//                return;
//            } default:
//                cameraImage.setEnabled( false );
//        }
//    }

    private void galleryAddPic( Context context, String photoPath ) {
        photoPath = photoFile.getAbsolutePath();
        photoPathName = photoPath;
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File( photoPathName );
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast( mediaScanIntent );
    }
}