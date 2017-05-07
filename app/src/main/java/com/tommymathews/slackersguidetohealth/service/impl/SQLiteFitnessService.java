package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import java.util.ArrayList;
import java.util.List;

public class SQLiteFitnessService implements FitnessService {

    private SQLiteDatabase database;

    public SQLiteFitnessService(Context context) {
        database = new DbHelper(context).getWritableDatabase();
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

        if (whereClause.equals(DbSchema.FitnessTable.Columns.ID + "=?")) {
            cursorSteps = database.query(DbSchema.StepsTable.STEPS_NAME, null, DbSchema.StepsTable.Columns.STEPN + "=?", whereArgs, null, null, orderBy);
            cursorImages = database.query(DbSchema.ImagesTable.IMAGE_NAME, null, DbSchema.ImagesTable.Columns.IMAGEN + "=?", whereArgs, null, null, orderBy);
        } else {
            cursorSteps = database.query(DbSchema.StepsTable.STEPS_NAME, null, null, whereArgs, null, null, orderBy);
            cursorImages = database.query(DbSchema.ImagesTable.IMAGE_NAME, null, null, whereArgs, null, null, orderBy);
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
        Fitness f1 = new Fitness("Crunches", 0, 30, "Crunches are a great abs exercise.", null);
        ArrayList<String> steps1 = new ArrayList<>();
        steps1.add("Lay on your back with your feet on the floor and knees facing forward.");
        steps1.add("Cross your arms across your chest");
        steps1.add("Use only your upper body to rise up and make your elbows touch your knees");
        steps1.add("Do 30 of these");
        f1.setSteps(steps1);

        //TODO
        Fitness f2 = new Fitness("Pull Ups", 1, 20, "Pull Ups are a great back exercise", null);
        ArrayList<String> steps2 = new ArrayList<>();
        steps2.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps2.add("Pull yourself up until your chin is above the bar.");
        steps2.add("Take a slight pause and lower yourself all the way back down");
        steps2.add("Do 20 of these");
        f2.setSteps(steps2);


        Fitness f3 = new Fitness("Towel Bicep Curls", 2, 20, "Put your hands on the floor, shoulders" +
                " width apart, and extend your body while keeping your it above the ground. Bend " +
                "your arms and have your chin touch the floor. Do 20 of these", null);

        //TODO
        Fitness f4 = new Fitness("Push Ups", 3, 20, "", null);
        Fitness f5 = new Fitness("Push Ups", 4, 20, "Put your hands on the floor, shoulders" +
                " width apart, and extend your body while keeping your it above the ground. Bend " +
                "your arms and have your chin touch the floor. Do 20 of these", null);
        Fitness f6 = new Fitness("Push Ups", 5, 20, "", null);


        Fitness f7 = new Fitness("Handstand Push Ups", 6, 20, "", null);


        //TODO
        Fitness f8 = new Fitness("Push Ups", 7, 20, "", null);

        Fitness f9 = new Fitness("Push Ups", 8, 20, "Push Ups are a great triceps exercise.", null);
        ArrayList<String> steps9 = new ArrayList<>();
        steps9.add("Put your hands on the floor, shoulders, width apart");
        steps9.add("Extend your body while keeping your it above the ground");
        steps9.add("Bend your arms and have your chin touch the floor");
        steps9.add("Do 20 of these");
        f9.setSteps(steps9);


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