package com.tommymathews.slackersguidetohealth.service.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;

import com.tommymathews.slackersguidetohealth.model.Fitness;
import com.tommymathews.slackersguidetohealth.service.FitnessService;

import java.util.ArrayList;
import java.util.List;

public class SQLiteFitnessService implements FitnessService {

    private SQLiteDatabase database;

    public SQLiteFitnessService(Context context) {
        database = new DbHelper(context).getWritableDatabase();
//        database.delete(DbSchema.FitnessTable.FITNESS_NAME, null, null );
    }

    protected SQLiteDatabase getDatabase() {
        return database;
    }

    @Override
    public void addFitness(Fitness fitness) {
        ContentValues contentValues = getContentValues( fitness );
//        Fitness currFitness = getFitnessByName( fitness.getFitnessName() );
        List<Fitness> queryList = queryFitness(DbSchema.FitnessTable.Columns.ID + "=?",
                new String[]{
                        fitness.getId()
                },
                null
        );
        if ( queryList == null ) {
            database.insert( DbSchema.FitnessTable.FITNESS_NAME,
                             null,
                             contentValues
            );
        } else {
            database.update( DbSchema.FitnessTable.FITNESS_NAME,
                             contentValues,
                             DbSchema.FitnessTable.FITNESS_NAME + "=?",
                             new String[] {
                                     fitness.getFitnessName()
                             }
            );
        }
    }

    @Override
    public Fitness getFitnessByName(String fitness) {
        if( fitness == null ) {
            return null;
        }

        List<Fitness> fitnessList = queryFitness( DbSchema.FitnessTable.FITNESS_NAME,
                                                  new String[]{
                                                          fitness
                                                  },
                                                   null
        );

        for( Fitness fit : fitnessList ) {
            if( fit.getFitnessName().equals( fitness ) ) {
                return fit;
            }
        }

        return null;
    }

    public Fitness getFitnessById( String id ) {
        if( id == null ) {
            return null;
        }

        List<Fitness> queryList = queryFitness( DbSchema.FitnessTable.Columns.ID + "=?",
                new String[] {
                    id
                },
                null
        );

        for( Fitness fit : queryList ) {
            if( fit.getId() == null ) {
                return null;
            }
            return fit;
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

        Cursor cursor = database.query( DbSchema.FitnessTable.FITNESS_NAME,
                                        null,
                                        whereClause,
                                        whereArgs,
                                        null,
                                        null,
                                        orderBy
        );

        FitnessCursorWrapper wrapper = new FitnessCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                fitness.add( wrapper.getFitness() );
                wrapper.moveToNext();
            }
        } finally {
            cursor.close();
            wrapper.close();
        }

        return fitness;
    }

    private static ContentValues getContentValues( Fitness fitness ) {
        ContentValues contentValues = new ContentValues();

        contentValues.put( DbSchema.FitnessTable.FITNESS_NAME, fitness.getFitnessName() );
        contentValues.put( DbSchema.FitnessTable.Columns.BODY_PART, fitness.getBodyPart().toString() );
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

    private class FitnessCursorWrapper extends CursorWrapper {
        public FitnessCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Fitness getFitness() {
            String name = getString( getColumnIndex( DbSchema.FitnessTable.FITNESS_NAME ) );
            int bodyPart = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.BODY_PART ) );
            int numReps = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.NUM_REPS ) );
            String instructions = getString( getColumnIndex( DbSchema.FitnessTable.Columns.INSTRUCTIONS ) );
            byte[] image = getBlob( getColumnIndex( DbSchema.FitnessTable.Columns.IMAGE ) );

            Fitness fitness = new Fitness( name, bodyPart, numReps, instructions, DbBitmapUtility.getImage( image ) );
            fitness.setFitnessName( name );
            fitness.setBodyPartPosition( bodyPart );
            fitness.setNumReps( numReps );
            fitness.setInstructions( instructions );
            fitness.setImage( DbBitmapUtility.getImage( image ) );

            return fitness;
        }
    }

}