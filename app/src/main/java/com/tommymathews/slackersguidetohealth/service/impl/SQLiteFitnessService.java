package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

import com.tommymathews.slackersguidetohealth.R;
import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SQLiteFitnessService implements FitnessService {

    private SQLiteDatabase database;
    private Context context;

    public SQLiteFitnessService(Context c) {
        context = c;
        database = new DbHelper(c).getWritableDatabase();
        prepopulate(c);

    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void addFitness(Fitness fitness) {
        ContentValues contentValues = getContentValues( fitness );
        Fitness currFitness = getFitnessById(fitness.getId());

        if ( currFitness == null ) {
            database.insert(DbSchema.FitnessTable.FITNESS_NAME, null, contentValues);

        } else {
            database.update(DbSchema.FitnessTable.FITNESS_NAME, contentValues, DbSchema.FitnessTable.Columns.ID + "=?", new String[] {currFitness.getId()});

        }
    }

    //Finds and returns a fitness with parameter id. If it DNE, returns null.
    @Override
    public Fitness getFitnessById(String id) {
        if( id == null ) {
            return null;
        }

        List<Fitness> fitnessList = queryFitness(DbSchema.FitnessTable.Columns.ID, new String[]{id}, null);

        for( Fitness fit : fitnessList ) {
            if( fit.getId().equals( id ) ) {
                return fit;
            }
        }

        return null;
    }

    @Override
    public List<Fitness> getFitnessesByName(String name) {
        if( name == null ) {
            return null;
        }

        List<Fitness> fitnessList = queryFitness(DbSchema.FitnessTable.Columns.NAME, new String[]{name}, null);

        return fitnessList;
    }

    //Returns a list of all fitnesses that work out parameter body part.
    @Override
    public List<Fitness> getFitnessesByBodyPart(Fitness.BodyPart bodyPart) {
        List<Fitness> fitness = getAllFitness();
        ArrayList<Fitness> temp = new ArrayList<Fitness>();
        for (int i = 0; i < fitness.size(); ++i) {
            if ((fitness.get(i).getBodyPart()).equals(bodyPart)) {
                temp.add(fitness.get(i));
            }
        }
        return fitness;
    }

    //Returns a list of all fitnesses.
    @Override
    public List<Fitness> getAllFitness() {
        List<Fitness> fitness = queryFitness(null, null, null);
        return fitness;
    }

    //Returns a list of fitnesses that match the given parameter criteria.
    private List<Fitness> queryFitness( String whereClause, String[] whereArgs, String orderBy ) {
        List<Fitness> fitness = new ArrayList<Fitness>();
        if ( whereClause != null )
            whereClause = whereClause + "=?";

        Cursor cursor = database.query(DbSchema.FitnessTable.FITNESS_NAME, null, whereClause, whereArgs, null, null, orderBy);

        FitnessCursorWrapper wrapper = new FitnessCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();

            while (!wrapper.isAfterLast()) {
                Fitness fit = wrapper.getFitness();

                fitness.add(fit);

                wrapper.moveToNext();
            }

        } finally {
            cursor.close();

            wrapper.close();

        }

        return fitness;
    }


    //Returns the content value from the given fitness for the fitness storage.
    private static ContentValues getContentValues( Fitness fitness ) {
        ContentValues contentValues = new ContentValues();

        contentValues.put( DbSchema.FitnessTable.Columns.NAME, fitness.getFitnessName());
        contentValues.put( DbSchema.FitnessTable.Columns.ID, fitness.getId());
        contentValues.put( DbSchema.FitnessTable.Columns.LIKES, fitness.getLikes());
        contentValues.put( DbSchema.FitnessTable.Columns.BODY_PART, fitness.getBodyPart().toString());
        contentValues.put( DbSchema.FitnessTable.Columns.NUM_REPS, fitness.getNumReps() );
        contentValues.put( DbSchema.FitnessTable.Columns.INSTRUCTIONS, fitness.getInstructions() );
        contentValues.put( DbSchema.FitnessTable.Columns.IMAGE, fitness.getImageDb().getPath() );
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_1, fitness.getStepsDB()[0]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_2, fitness.getStepsDB()[1]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_3, fitness.getStepsDB()[2]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_4, fitness.getStepsDB()[3]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_5, fitness.getStepsDB()[4]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_6, fitness.getStepsDB()[5]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_7, fitness.getStepsDB()[6]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_8, fitness.getStepsDB()[7]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_9, fitness.getStepsDB()[8]);
        contentValues.put( DbSchema.FitnessTable.Columns.STEP_10, fitness.getStepsDB()[9]);

        if (fitness.getImagesDB()[0] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_1, ((fitness.getImagesDB()[0])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_1, "");
        }

        if (fitness.getImagesDB()[1] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_2, ((fitness.getImagesDB()[1])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_2, "");
        }

        if (fitness.getImagesDB()[2] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_3, ((fitness.getImagesDB()[2])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_3, "");
        }

        if (fitness.getImagesDB()[3] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_4, ((fitness.getImagesDB()[3])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_4, "");
        }

        if (fitness.getImagesDB()[4] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_5, ((fitness.getImagesDB()[4])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_5, "");
        }

        if (fitness.getImagesDB()[5] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_6, ((fitness.getImagesDB()[5])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_6, "");
        }

        if (fitness.getImagesDB()[6] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_7, ((fitness.getImagesDB()[6])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_7, "");
        }

        if (fitness.getImagesDB()[7] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_8, ((fitness.getImagesDB()[7])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_8, "");
        }

        if (fitness.getImagesDB()[8] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_9, ((fitness.getImagesDB()[8])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_9, "");
        }

        if (fitness.getImagesDB()[9] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_10, ((fitness.getImagesDB()[9])).getPath());
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_10, "");
        }

        return contentValues;
    }

    private File getPhotoFile(){
        File externalPhotoDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalPhotoDir == null){
            return null;
        }

        return new File(externalPhotoDir, "IMG_" + System.currentTimeMillis() + ".jpg");
    }

    private class FitnessCursorWrapper extends CursorWrapper {
        public FitnessCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Fitness getFitness() {
            String name = getString( getColumnIndex( DbSchema.FitnessTable.Columns.NAME ) );
            String id = getString( getColumnIndex( DbSchema.FitnessTable.Columns.ID ) );
            int likes =  getInt( getColumnIndex( DbSchema.FitnessTable.Columns.LIKES ) );
            int bodyPart = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.BODY_PART ) );
            int numReps = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.NUM_REPS ) );
            String instructions = getString( getColumnIndex( DbSchema.FitnessTable.Columns.INSTRUCTIONS ) );
            File imageFile = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE)));
            File image = imageFile;

            String steps[] = {
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_1)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_2)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_3)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_4)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_5)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_6)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_7)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_8)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_9)),
                    getString( getColumnIndex( DbSchema.FitnessTable.Columns.STEP_10))};

            File images[] = {null, null, null, null ,null, null, null, null, null, null};
            images[0] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_1)));
            images[1] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_2)));
            images[2] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_3)));
            images[3] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_4)));
            images[4] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_5)));
            images[5] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_6)));
            images[6] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_7)));
            images[7] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_8)));
            images[8] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_9)));
            images[9] = new File(getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_10)));

            Fitness fitness = new Fitness(name, bodyPart, numReps, instructions, image);
            fitness.setId(id);
            fitness.setLikes(likes);
            fitness.setFitnessName( name );
            fitness.setBodyPart( bodyPart );
            fitness.setNumReps( numReps );
            fitness.setInstructions( instructions );
            fitness.setStepsDB(steps);
            fitness.setImagesDB(images);

            return fitness;
        }
    }

    private void prepopulate(Context context) {
        //eggplant
        //TODO

        File img = new File ("android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.bicycle_crunch);

        Fitness f1 = new Fitness("Crunches", Fitness.BodyPart.ABS.ordinal(), 30, "Crunches are a great abs exercise.", img);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("Lay on your back with your feet on the floor and knees facing forward.");
        steps.add("Cross your arms across your chest");
        steps.add("Use only your upper body to rise up and make your elbows touch your knees");
        steps.add("Do 30 of these");

        ArrayList<File> images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f1.setSteps(steps);
        f1.setStepImages(images);

        //TODO
        Fitness f2 = new Fitness("Pull Ups", Fitness.BodyPart.BACK.ordinal(), 20, "Pull Ups are a great back exercise", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f2.setSteps(steps);
        f2.setStepImages(images);

        //TODO Fix
        Fitness f3 = new Fitness("Towel Bicep Curls", 2, 20, "TBC", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f3.setSteps(steps);
        f3.setStepImages(images);

        Fitness f4 = new Fitness("Double-Leg Calf Raise", 3, 20, "TODO Double-Leg Calf Raise", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f4.setSteps(steps);
        f4.setStepImages(images);

        Fitness f5 = new Fitness("Chest Dips", 4, 20, "TODO Chest Dips", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f5.setSteps(steps);
        f5.setStepImages(images);

        Fitness f6 = new Fitness("Glutes Exercise TODO", 5, 20, "TODO glutes", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f6.setSteps(steps);
        f6.setStepImages(images);

        Fitness f7 = new Fitness("Leg Extension", 6, 20, "TODO Leg Extension", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f7.setSteps(steps);
        f7.setStepImages(images);

        Fitness f8 = new Fitness("Handstand Push Ups", 7, 20, "", img);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f8.setSteps(steps);
        f8.setStepImages(images);


        Fitness f9 = new Fitness("Push Ups", 8, 20, "Push Ups are a great triceps exercise.", img);
        ArrayList<String> steps9 = new ArrayList<>();
        steps9.add("Put your hands on the floor, shoulders, width apart");
        steps9.add("Extend your body while keeping your it above the ground");
        steps9.add("Bend your arms and have your chin touch the floor");
        steps9.add("Do 20 of these");

        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img);
        images.add(img);
        images.add(img);
        images.add(img);

        f9.setSteps(steps);
        f9.setStepImages(images);

        addFitness(f1);
        addFitness(f2);
        addFitness(f3);
        addFitness(f4);
        addFitness(f5);
        addFitness(f6);
        addFitness(f7);
        addFitness(f8);
        addFitness(f9);
    }

}