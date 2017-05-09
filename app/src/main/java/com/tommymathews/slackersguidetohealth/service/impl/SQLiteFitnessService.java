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
        if (getAllFitness().isEmpty()) {
            prepopulate(c);
        }

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
    public List<Fitness> getFitnessesByBodyPart(int bodyPart) {
        List<Fitness> fitness = getAllFitness();
        ArrayList<Fitness> temp = new ArrayList<Fitness>();
        for (int i = 0; i < fitness.size(); ++i) {
            if (fitness.get(i).getBodyPart().ordinal() == bodyPart) {
                temp.add(fitness.get(i));
            }
        }
        return temp;
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
        contentValues.put( DbSchema.FitnessTable.Columns.BODY_PART, fitness.getBodyPart().ordinal());
        contentValues.put( DbSchema.FitnessTable.Columns.NUM_REPS, fitness.getNumReps() );
        contentValues.put( DbSchema.FitnessTable.Columns.INSTRUCTIONS, fitness.getInstructions() );
        contentValues.put( DbSchema.FitnessTable.Columns.IMAGE, fitness.getImage());
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
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_1, ((fitness.getImagesDB()[0])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_1, "");
        }

        if (fitness.getImagesDB()[1] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_2, ((fitness.getImagesDB()[1])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_2, "");
        }

        if (fitness.getImagesDB()[2] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_3, ((fitness.getImagesDB()[2])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_3, "");
        }

        if (fitness.getImagesDB()[3] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_4, ((fitness.getImagesDB()[3])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_4, "");
        }

        if (fitness.getImagesDB()[4] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_5, ((fitness.getImagesDB()[4])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_5, "");
        }

        if (fitness.getImagesDB()[5] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_6, ((fitness.getImagesDB()[5])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_6, "");
        }

        if (fitness.getImagesDB()[6] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_7, ((fitness.getImagesDB()[6])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_7, "");
        }

        if (fitness.getImagesDB()[7] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_8, ((fitness.getImagesDB()[7])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_8, "");
        }

        if (fitness.getImagesDB()[8] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_9, ((fitness.getImagesDB()[8])));
        } else {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_9, "");
        }

        if (fitness.getImagesDB()[9] != null) {
            contentValues.put(DbSchema.FitnessTable.Columns.IMAGE_10, ((fitness.getImagesDB()[9])));
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
            String image  = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE));

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

            String images[] = {null, null, null, null ,null, null, null, null, null, null};
            images[0] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_1));
            images[1] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_2));
            images[2] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_3));
            images[3] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_4));
            images[4] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_5));
            images[5] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_6));
            images[6] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_7));
            images[7] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_8));
            images[8] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_9));
            images[9] = getString(getColumnIndex(DbSchema.FitnessTable.Columns.IMAGE_10));

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

        String img1 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.regular_crunches;

        Fitness f1 = new Fitness("Crunches", Fitness.BodyPart.ABS.ordinal(), 30, "Crunches are a great abs exercise.", img1);

        ArrayList<String> steps = new ArrayList<>();
        steps.add("Lay on your back with your feet on the floor and knees facing forward.");
        steps.add("Cross your arms across your chest");
        steps.add("Use only your upper body to rise up and make your elbows touch your knees");
        steps.add("Do 30 of these");

        ArrayList<String> images = new ArrayList<>();
        images.add(img1);
        images.add(img1);
        images.add(img1);
        images.add(img1);

        f1.setSteps(steps);
        f1.setStepImages(images);

        //TODO
        String img2 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.pullup_narrow;
        Fitness f2 = new Fitness("Pull Ups", Fitness.BodyPart.BACK.ordinal(), 20, "Pull Ups are a great back exercise.", img2);
        steps = new ArrayList<>();
        steps.add("Grab a bar with a grip slightly wider than shoulder width, with your hands facing away from you," +
                "and hang all the way down");
        steps.add("Pull yourself up until your chin is above the bar.");
        steps.add("Take a slight pause and lower yourself all the way back down");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img2);
        images.add(img2);
        images.add(img2);
        images.add(img2);

        f2.setSteps(steps);
        f2.setStepImages(images);

        //TODO Fix
        String img3 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.tricep_towel;
        Fitness f3 = new Fitness("Towel Bicep Curls", Fitness.BodyPart.BICEPS.ordinal(), 20, "Towel Bicep Curls are a great biceps exercise.", img3);
        steps = new ArrayList<>();
        steps.add("Hold each end of a towel and loop the middle of the towel under one foot." +
                " You can stand with your back to the wall to help with balance.");
        steps.add("With your palms facing forwards, use your foot to provide resistance as you pull " +
                "up on the towel as hard as you can by bending your arms at the elbows.");
        steps.add("Hold this position at the top of the contraction for five seconds, pulling as hard " +
                "as you can while providing enough resistance with your foot as if you want to tear the towel.");
        steps.add("Do 15 of these");

        images = new ArrayList<>();
        images.add(img3);
        images.add(img3);
        images.add(img3);
        images.add(img3);

        f3.setSteps(steps);
        f3.setStepImages(images);

        String img4 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.double_calf_raise;
        Fitness f4 = new Fitness("Double-Leg Calf Raise", Fitness.BodyPart.CALFS.ordinal(), 20, "The Double-Leg Calf Raise is great calf exercise.", img4);
        steps = new ArrayList<>();
        steps.add("Stand with both feet on the edge of a step. The balls of" +
                " your feet and toes should be the only parts of your foot on the platform.");
        steps.add("Lift your heels by pressing up through your toes contracting your calf muscles.");
        steps.add("Slowly lower your heels to the neutral starting position.");
        steps.add("Do 10 of these");

        images = new ArrayList<>();
        images.add(img4);
        images.add(img4);
        images.add(img4);
        images.add(img4);

        f4.setSteps(steps);
        f4.setStepImages(images);

        String img5 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.dips;
        Fitness f5 = new Fitness("Chest Dips", Fitness.BodyPart.CHEST.ordinal(), 20, "Chest Dips are a great chest exercise.", img5);
        steps = new ArrayList<>();
        steps.add("For this exercise you will need access to parallel bars. To get yourself into the " +
                "starting position, hold your body at arms length (arms locked) above the bars.");
        steps.add("While breathing in, lower yourself slowly with your torso leaning forward around 30 " +
                "degrees or so and your elbows flared out slightly until you feel a slight stretch in the chest.");
        steps.add("Once you feel the stretch, use your chest to bring your body back to the starting position as you " +
                "breathe out. Tip: Remember to squeeze the chest at the top of the movement for a second");
        steps.add("Do 10 of these");

        images = new ArrayList<>();
        images.add(img5);
        images.add(img5);
        images.add(img5);
        images.add(img5);

        f5.setSteps(steps);
        f5.setStepImages(images);

        String img6 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.weighted_squats;
        Fitness f6 = new Fitness("Squats", Fitness.BodyPart.GLUTES.ordinal(), 20, "Squats are a great glutes exercise.", img6);
        steps = new ArrayList<>();
        steps.add("Stand up straight with a dumbbell held in the center, and position your legs using a shoulder " +
                "width medium stance with the toes slightly pointed out. Keep your head up while doing this.");
        steps.add("Begin to slowly lower your torso by bending the knees as you maintain a straight posture with the" +
                " head up. Continue down until your thighs are parallel to the floor.");
        steps.add("Begin to raise your torso as you exhale by pushing the floor with the heel of your foot mainly as " +
                "you straighten the legs again and go back to the starting position.");
        steps.add("Do 10 of these");

        images = new ArrayList<>();
        images.add(img6);
        images.add(img6);
        images.add(img6);
        images.add(img6);

        f6.setSteps(steps);
        f6.setStepImages(images);

        String img7 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.alternating_lunges;
        Fitness f7 = new Fitness("Alternating Lunges", Fitness.BodyPart.QUADS.ordinal(), 20, "Looking to kill two birds in one stone? Use this workout for cardio as well!", img7);
        steps = new ArrayList<>();
        steps.add("You will need a leg extension machine. Sit down on the machine with your legs under the bars " +
                "and hold the handles on the machine");
        steps.add("Using your quadriceps, extend your legs to the maximum as you exhale. Ensure that the rest of " +
                "the body remains stationary on the seat.");
        steps.add("Take a slight pause and Slowly lower the weight back to the original position as you inhale, " +
                "ensuring that you do not go past the 90-degree angle limit");
        steps.add("Do 15 of these");

        images = new ArrayList<>();
        images.add(img7);
        images.add(img7);
        images.add(img7);
        images.add(img7);

        f7.setSteps(steps);
        f7.setStepImages(images);

        String img8 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.handstand_pushup;
        Fitness f8 = new Fitness("Handstand Push Ups", Fitness.BodyPart.SHOULDERS.ordinal(), 20, "Handstand Push Ups are a great shoulders exercise.", img8);
        steps = new ArrayList<>();
        steps.add("With your back to the wall bend at the waist and place both hands on the floor at shoulder width.");
        steps.add("Kick yourself up against the wall with your arms straight. Your body should be upside down with the " +
                "arms and legs fully extended. Keep your whole body as straight as possible.");
        steps.add("Slowly lower yourself to the ground as you inhale until your head almost touches the floor.");
        steps.add("Push yourself back up slowly as you exhale until your elbows are nearly locked.");
        steps.add("Do 10 of these");

        images = new ArrayList<>();
        images.add(img8);
        images.add(img8);
        images.add(img8);
        images.add(img8);
        images.add(img8);

        f8.setSteps(steps);
        f8.setStepImages(images);

        String img9 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.diamond_push_up;
        Fitness f9 = new Fitness("Diamond Push Ups", Fitness.BodyPart.TRICEPS.ordinal(), 20, "Not only will workout the chest, but you will workout the triceps!", img9);
        steps = new ArrayList<>();
        steps.add("Put your hands on the floor and form a diamond. Make sure your legs are extended on the floor wider than shoulder width.");
        steps.add("Maintain your balance and go down.");
        steps.add("Slowly push up.");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img9);
        images.add(img9);
        images.add(img9);
        images.add(img9);

        f9.setSteps(steps);
        f9.setStepImages(images);

        String img10 = "android.resource://com.tommymathews.slackersguidetohealth/" + R.drawable.bicycle_crunch;
        Fitness f10 = new Fitness("Bicycle Crunches", Fitness.BodyPart.ABS.ordinal(), 20, "A great workout that is more involved than crunches.", img10);
        steps = new ArrayList<>();
        steps.add("Lie on your back with your body flat to the ground.");
        steps.add("Place your hands behind your head and your elbows flat to the ground.");
        steps.add("As you curl forward, make sure your left elbow touches your right knee.");
        steps.add("Rotate your body and let your right elbow touch your left knee.");
        steps.add("Do 20 of these");

        images = new ArrayList<>();
        images.add(img10);
        images.add(img10);
        images.add(img10);
        images.add(img10);

        f10.setSteps(steps);
        f10.setStepImages(images);

        addFitness(f1);
        addFitness(f2);
        addFitness(f3);
        addFitness(f4);
        addFitness(f5);
        addFitness(f6);
        addFitness(f7);
        addFitness(f8);
        addFitness(f9);
        addFitness(f10);
    }

}