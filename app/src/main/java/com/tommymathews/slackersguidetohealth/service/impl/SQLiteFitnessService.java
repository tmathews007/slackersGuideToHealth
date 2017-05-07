package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.tommymathews.slackersguidetohealth.R;
import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

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
        ContentValues stepsContentValues = getStepContextValues(fitness);
        ContentValues imagesContentValues = getImageContextValues(fitness);
        Fitness currFitness = getFitnessById(fitness.getId());

        if ( currFitness == null ) {
            database.insert(DbSchema.FitnessTable.FITNESS_NAME, null, contentValues);

            database.insert(DbSchema.StepsTable.STEPS_NAME, null, stepsContentValues);

            database.insert(DbSchema.ImagesTable.IMAGE_NAME, null, imagesContentValues);

        } else {
            database.update(DbSchema.FitnessTable.FITNESS_NAME, contentValues, DbSchema.FitnessTable.Columns.ID + "=?", new String[] {currFitness.getId()});

            database.update(DbSchema.StepsTable.STEPS_NAME, stepsContentValues, DbSchema.StepsTable.Columns.STEPN + "=?", new String[] {currFitness.getFitnessName()});

            database.update(DbSchema.ImagesTable.IMAGE_NAME, imagesContentValues, DbSchema.ImagesTable.Columns.IMAGEN + "=?", new String[]{currFitness.getFitnessName()});
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

        Cursor cursorSteps;

        Cursor cursorImages;

        if (whereClause == null) {
            cursorSteps = database.query(DbSchema.StepsTable.STEPS_NAME, null, null, whereArgs, null, null, orderBy);
            cursorImages = database.query(DbSchema.ImagesTable.IMAGE_NAME, null, null, whereArgs, null, null, orderBy);
        } else {
            cursorSteps = database.query(DbSchema.StepsTable.STEPS_NAME, null, DbSchema.StepsTable.Columns.STEPN + "=?", whereArgs, null, null, orderBy);
            cursorImages = database.query(DbSchema.ImagesTable.IMAGE_NAME, null, DbSchema.ImagesTable.Columns.IMAGEN + "=?", whereArgs, null, null, orderBy);

        }

        FitnessCursorWrapper wrapper = new FitnessCursorWrapper(cursor);
        StepsCursorWrapper stepsWrapper = new StepsCursorWrapper(cursorSteps);
        ImagesCursorWrapper imagesWrapper = new ImagesCursorWrapper(cursorImages);

        try {
            wrapper.moveToFirst();
            stepsWrapper.moveToFirst();
            imagesWrapper.moveToFirst();
            while (!wrapper.isAfterLast() && !stepsWrapper.isAfterLast() && !imagesWrapper.isAfterLast()) {
                Fitness fit = wrapper.getFitness();
                String[] steps = stepsWrapper.getSteps();
                Bitmap[] imgs = imagesWrapper.getImages();

                fit.setStepsDB(steps);
                fit.setImagesDB(imgs);

                fitness.add(fit);

                wrapper.moveToNext();
                stepsWrapper.moveToNext();
                imagesWrapper.moveToNext();
            }

        } finally {
            cursor.close();
            cursorSteps.close();
            cursorImages.close();

            wrapper.close();
            stepsWrapper.close();
            imagesWrapper.close();
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
        contentValues.put( DbSchema.FitnessTable.Columns.IMAGE, DbBitmapUtility.getBytes( fitness.getImage() ) );
//        for( int i = 0; i < fitness.getInstructions().size(); i++ ) {
//            contentValues.put( DbSchema.FitnessTable.Columns.INSTRUCTIONS, fitness.getInstructions().get( i ) );
//        }
//        for( int i = 0; i < fitness.getImage().size(); i++ ) {
//            contentValues.put( DbSchema.FitnessTable.Columns.IMAGE, DbBitmapUtility.getBytes( fitness.getImage().get( i ) ) );
//        }

        return contentValues;
    }

    //Returns the content value of the steps
    private static ContentValues getStepContextValues (Fitness fitness) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.StepsTable.Columns.STEPN, fitness.getId());
        contentValues.put(DbSchema.StepsTable.Columns.STEP_1, fitness.getStepsDB()[0]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_2, fitness.getStepsDB()[1]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_3, fitness.getStepsDB()[2]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_4, fitness.getStepsDB()[3]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_5, fitness.getStepsDB()[4]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_6, fitness.getStepsDB()[5]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_7, fitness.getStepsDB()[6]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_8, fitness.getStepsDB()[7]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_9, fitness.getStepsDB()[8]);
        contentValues.put(DbSchema.StepsTable.Columns.STEP_10, fitness.getStepsDB()[9]);
        return contentValues;
    }

    //Returns the content values of the images.
    private static ContentValues getImageContextValues (Fitness fitness) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.ImagesTable.Columns.IMAGEN, fitness.getId());
        if (fitness.getImagesDB()[0] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_1, DbBitmapUtility.getBytes(fitness.getImagesDB()[0]));
        }
        if (fitness.getImagesDB()[1] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_2, DbBitmapUtility.getBytes(fitness.getImagesDB()[1]));
        }
        if (fitness.getImagesDB()[2] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_3, DbBitmapUtility.getBytes(fitness.getImagesDB()[2]));
        }
        if (fitness.getImagesDB()[3] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_4, DbBitmapUtility.getBytes(fitness.getImagesDB()[3]));
        }
        if (fitness.getImagesDB()[4] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_5, DbBitmapUtility.getBytes(fitness.getImagesDB()[4]));
        }
        if (fitness.getImagesDB()[5] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_6, DbBitmapUtility.getBytes(fitness.getImagesDB()[5]));
        }
        if (fitness.getImagesDB()[6] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_7, DbBitmapUtility.getBytes(fitness.getImagesDB()[6]));
        }
        if (fitness.getImagesDB()[7] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_8, DbBitmapUtility.getBytes(fitness.getImagesDB()[7]));
        }
        if (fitness.getImagesDB()[8] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_9, DbBitmapUtility.getBytes(fitness.getImagesDB()[8]));
        }
        if (fitness.getImagesDB()[9] != null) {
            contentValues.put(DbSchema.ImagesTable.Columns.IMAGE_10, DbBitmapUtility.getBytes(fitness.getImagesDB()[9]));
        }
        return contentValues;
    }

    private void prepopulate(Context context) {
        //eggplant
        //TODO
        Drawable image = context.getResources().getDrawable(R.drawable.bicycle_crunch);
        Bitmap img = ((BitmapDrawable) image).getBitmap();

        Fitness f1 = new Fitness("Crunches", Fitness.BodyPart.ABS.ordinal(), 30, "Crunches are a great abs exercise.", img);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("Lay on your back with your feet on the floor and knees facing forward.");
        steps.add("Cross your arms across your chest");
        steps.add("Use only your upper body to rise up and make your elbows touch your knees");
        steps.add("Do 30 of these");

        ArrayList<Bitmap> images = new ArrayList<>();
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
            byte[] image = getBlob( getColumnIndex( DbSchema.FitnessTable.Columns.IMAGE ) );

            Fitness fitness = new Fitness( name, bodyPart, numReps, instructions, DbBitmapUtility.getImage( image ) );
            fitness.setId(id);
            fitness.setLikes(likes);
            fitness.setFitnessName( name );
            fitness.setBodyPart( bodyPart );
            fitness.setNumReps( numReps );
            fitness.setInstructions( instructions );
            fitness.setImage( DbBitmapUtility.getImage( image ) );

            return fitness;
        }
    }


    private class StepsCursorWrapper extends CursorWrapper {
        public StepsCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public String[] getSteps() {

            String steps[] = {
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_1)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_2)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_3)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_4)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_5)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_6)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_7)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_8)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_9)),
                    getString( getColumnIndex( DbSchema.StepsTable.Columns.STEP_10))};

            return steps;
        }
    }

    private class ImagesCursorWrapper extends CursorWrapper {
        public ImagesCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Bitmap[] getImages() {
            Bitmap images[] = {null, null, null, null ,null, null, null, null, null, null};
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_1)) != null) {
                images[0] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_1)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_2)) != null) {
                images[1] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_2)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_3)) != null) {
                images[2] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_3)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_4)) != null) {
                images[3] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_4)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_5)) != null) {
                images[4] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_5)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_6)) != null) {
                images[5] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_6)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_7)) != null) {
                images[6] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_7)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_8)) != null) {
                images[7] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_8)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_9)) != null) {
                images[8] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_9)));
            }
            if (getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_10)) != null) {
                images[9] = DbBitmapUtility.getImage(getBlob(getColumnIndex(DbSchema.ImagesTable.Columns.IMAGE_10)));
            }
            return images;
        }
    }

}