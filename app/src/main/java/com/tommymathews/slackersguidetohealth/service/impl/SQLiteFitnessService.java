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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
    public void addFitnessToPlaylist( Fitness fitness ) {
        ContentValues contentValues = getContentValues( fitness );
        List<Fitness> queryList = queryFitness( DbSchema.FitnessTable.Columns.ID + "=?",
                new String[] {
                        fitness.getId()
                },
                null
        );

        if( queryList == null || queryList.size() == 0 ) {
            database.insert( DbSchema.FitnessTable.FITNESS_NAME,
                    null,
                    contentValues
            );
        } else {
            database.update( DbSchema.FitnessTable.FITNESS_NAME,
                    contentValues,
                    DbSchema.FitnessTable.Columns.ID + "=?",
                    new String[] {
                            fitness.getId()
                    }
            );
        }
    }

    @Override
    public Fitness getFitnessById( String id ) {
        if( id == null ) {
            return null;
        }

        List<Fitness> queryList = queryFitness( DbSchema.FitnessTable.Columns.ID +"=?",
                new String[] {
                        id
                },
                null
        );

        for( Fitness getFit : queryList ) {
            if( getFit.getId() == null ) {
                return null;
            }

            return getFit;
        }

        return null;
    }

    public List<Fitness> getAllFitnessesSorted() {
        List<Fitness> prioritizedFitness = queryFitness( null, null, null );

        Collections.sort(prioritizedFitness, new Comparator<Fitness>() {
            @Override
            public int compare(Fitness fit1, Fitness fit2) {
                if( fit1.getFitnessName().equals( fit2.getFitnessName()) ) {
                    return fit1.getBodyPart().compareTo( fit2.getBodyPart() );
                } else {
                    return fit1.getFitnessName().compareTo( fit2.getFitnessName() );
                }
            }
        }
        );

        return prioritizedFitness;
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
        contentValues.put( DbSchema.FitnessTable.Columns.ID, fitness.getId().toString() );
        contentValues.put( DbSchema.FitnessTable.Columns.BODY_PART, fitness.getBodyPart().toString() );
        contentValues.put( DbSchema.FitnessTable.Columns.NUM_REPS, fitness.getNumReps() );
        contentValues.put( DbSchema.FitnessTable.Columns.INSTRUCTIONS, fitness.getInstructions() );
        contentValues.put( DbSchema.FitnessTable.Columns.IMAGE, DbBitmapUtility.getBytes( fitness.getImage() ) );

        return contentValues;
    }

    private class FitnessCursorWrapper extends CursorWrapper {
        public FitnessCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Fitness getFitness() {
            String name = getString( getColumnIndex( DbSchema.FitnessTable.FITNESS_NAME ) );
            String id = getString( getColumnIndex( DbSchema.FitnessTable.Columns.ID ) );
            int bodyPart = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.BODY_PART ) );
            int numReps = getInt( getColumnIndex( DbSchema.FitnessTable.Columns.NUM_REPS ) );
            String instructions = getString( getColumnIndex( DbSchema.FitnessTable.Columns.INSTRUCTIONS ) );
            byte[] image = getBlob( getColumnIndex( DbSchema.FitnessTable.Columns.IMAGE ) );

            Fitness fitness = new Fitness( name, id, bodyPart, numReps, instructions, DbBitmapUtility.getImage( image ) );
            fitness.setFitnessName( name );
            fitness.setId( id );
            fitness.setBodyPartPosition( bodyPart );
            fitness.setNumReps( numReps );
            fitness.setInstructions( instructions );
            fitness.setImage( DbBitmapUtility.getImage( image ) );

            return fitness;
        }
    }

}