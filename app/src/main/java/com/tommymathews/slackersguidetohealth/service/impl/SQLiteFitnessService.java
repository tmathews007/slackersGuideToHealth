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
        Fitness currFitness = getFitnessByName( fitness.getFitnessName() );
        if ( currFitness == null ) {
            database.insert( DbSchema.FitnessTable.FITNESS_NAME,
                             null,
                             contentValues
            );

            database.insert(DbSchema.StepsTable.STEPS_NAME, null, stepsContentValues);

            database.insert(DbSchema.ImagesTable.IMAGE_NAME, null, imagesContentValues);
        } else {
            database.update( DbSchema.FitnessTable.FITNESS_NAME,
                             contentValues,
                             DbSchema.FitnessTable.Columns.NAME + "=?",
                             new String[] {
                                     currFitness.getFitnessName()
                             }
            );

            database.update(DbSchema.StepsTable.STEPS_NAME, stepsContentValues, DbSchema.StepsTable.Columns.STEPN + "=?", new String[] {currFitness.getFitnessName()});

            database.update(DbSchema.ImagesTable.IMAGE_NAME, imagesContentValues, DbSchema.ImagesTable.Columns.IMAGEN + "=?", new String[]{currFitness.getFitnessName()});
        }
    }

    @Override
    public Fitness getFitnessByName(String fitnessName) {
        Log.d("addFitness", "getting fitness by name");
        if( fitnessName == null ) {
            return null;
        }

        List<Fitness> fitnessList = queryFitness( DbSchema.FitnessTable.Columns.NAME, new String[]{fitnessName}, null);

        for( Fitness fit : fitnessList ) {
            if( fit.getFitnessName().equals( fitnessName ) ) {
                Log.d("addFitness", "get returning " + fit.getFitnessName());
                return fit;
            }
        }

        return null;
    }

    @Override
    public List<Fitness> getAllFitness() {
        List<Fitness> fitness = queryFitness(null, null, null);
        return fitness;
    }

    private List<Fitness> queryFitness( String whereClause, String[] whereArgs, String orderBy ) {
        List<Fitness> fitness = new ArrayList<Fitness>();
        if ( whereClause != null )
            whereClause = whereClause + "=?";

        Cursor cursor = database.query( DbSchema.FitnessTable.FITNESS_NAME, null, whereClause, whereArgs, null, null, orderBy);

        Cursor cursorSteps;

        Cursor cursorImages;

        if (whereClause.equals(DbSchema.FoodTable.Columns.NAME + "=?")) {
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

    private static ContentValues getContentValues( Fitness fitness ) {
        ContentValues contentValues = new ContentValues();

        contentValues.put( DbSchema.FitnessTable.Columns.NAME, fitness.getFitnessName());
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

    private static ContentValues getStepContextValues (Fitness fitness) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.StepsTable.Columns.STEPN, fitness.getFitnessName());
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

    private static ContentValues getImageContextValues (Fitness fitness) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.ImagesTable.Columns.IMAGEN, fitness.getFitnessName());
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

    private class FitnessCursorWrapper extends CursorWrapper {
        public FitnessCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Fitness getFitness() {
            String name = getString( getColumnIndex( DbSchema.FitnessTable.Columns.NAME ) );
            int bodyPart = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.BODY_PART ) );
            int numReps = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.NUM_REPS ) );
            String instructions = getString( getColumnIndex( DbSchema.FitnessTable.Columns.INSTRUCTIONS ) );
            byte[] image = getBlob( getColumnIndex( DbSchema.FitnessTable.Columns.IMAGE ) );

            Fitness fitness = new Fitness( name, bodyPart, numReps, instructions, DbBitmapUtility.getImage( image ) );
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