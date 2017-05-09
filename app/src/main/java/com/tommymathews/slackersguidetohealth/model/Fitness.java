package com.tommymathews.slackersguidetohealth.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.SectionIndexer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The purpose of this class is to create the database for the user who wants to input a fitness.
 * This will create the getters and setters for the database. The main components of the database
 * are: the fitness name, the body part(s), the instructions (which will be in the form of an
 * array), and the images (which will also be in the form of an array).
 */
public class Fitness implements Serializable {

    private String fitnessName;
    private double numReps;
    private BodyPart bodyPart;
    private String[] steps = {null, null, null, null, null, null, null, null, null, null};
    private String[] images = {null, null, null, null, null, null, null, null, null, null};
    private int likes;
    private String id;
    private String instructions;
    private String image;


    public Fitness( String fitnessName, int bodyPart, double numReps, String instructions, String image) {
        this.fitnessName = fitnessName;
        this.bodyPart = BodyPart.values()[ bodyPart % 9 ];
        this.numReps = numReps;
        this.instructions = instructions;
        this.image = image;
        this.id = UUID.randomUUID().toString();
        this.likes = 0;
    }


    public String getId(){
        return id;
    }

    public int getLikes() {
        return likes;
    }

    public String[] getImagesDB() {return images;}

    public String[] getStepsDB() {return steps; }

    public ArrayList<String> getSteps () {
        ArrayList<String> s = new ArrayList<>();

        int i = 0;
        while (i < steps.length && steps[i] != null) {
            s.add(steps[i]);
            ++i;
        }
        return s;
    }

    public ArrayList<String> getStepImages() {
        ArrayList<String> b = new ArrayList<>();

        int i = 0;
        while (i < images.length && images[i] != null) {
            b.add(images[i]);
            ++i;
        }
        return b;
    }

    public String getFitnessName() {
        return this.fitnessName;
    }

    public double getNumReps() {
        return this.numReps;
    }

    public BodyPart getBodyPart() {
        return this.bodyPart;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public String getImage() {
        return image;
    }

    public void setId(String i) {
        id = i;
    }

    public void setLikes(int i) {
        likes = i;
    }

    public void setFitnessName( String fitnessName ) {
        this.fitnessName = fitnessName;
    }

    public void setNumReps( double numReps ) {
        this.numReps = numReps;
    }

    public void setBodyPart( int bodyPart ) {
        this.bodyPart = BodyPart.values()[ bodyPart % 9 ];
    }

    public void setInstructions( String instructions ) {
        this.instructions = instructions;
    }

    public void setImage( String image ) {
        this.image = image;
    }

    public void setImagesDB(String[] imgs) {
        int i = 0;
        while(i < imgs.length) {
            images[i] = imgs[i];
            ++i;
        }
        while(i < images.length) {
            images[i] = null;
            ++i;
        }
    }

    public void setStepImages(List<String> imgs) {
        int i = 0;
        while (i < imgs.size() && i < images.length) {
            images[i] = imgs.get(i);
            i++;
        }

        while (i < steps.length) {
            images[i] = null;
            i++;
        }
    }

    public void setStepsDB(String[] s) {steps = s;}

    public void setSteps(List<String> s) {
        int i = 0;
        while (i < s.size() && i < steps.length) {
            steps[i] = s.get(i);
            i++;
        }

        while (i < steps.length) {
            steps[i] = null;
            i++;
        }
    }

    public int getBodyPartPosition() {
        switch ( this.bodyPart ) {
            case ABS:
                return 0;
            case BACK:
                return 1;
            case BICEPS:
                return 2;
            case CALFS:
                return 3;
            case CHEST:
                return 4;
            case GLUTES:
                return 5;
            case QUADS:
                return 6;
            case SHOULDERS:
                return 7;
            case TRICEPS:
                return 8;
            default:
                return 0;
        }
    }

    public void setBodyPartPosition(int position) {
        switch (position) {
            case 0:
                this.bodyPart = BodyPart.ABS;
                break;
            case 1:
                this.bodyPart = BodyPart.BACK;
                break;
            case 2:
                this.bodyPart = BodyPart.BICEPS;
                break;
            case 3:
                this.bodyPart = BodyPart.CALFS;
                break;
            case 4:
                this.bodyPart = BodyPart.CHEST;
                break;
            case 5:
                this.bodyPart = BodyPart.GLUTES;
                break;
            case 6:
                this.bodyPart = BodyPart.QUADS;
                break;
            case 7:
                this.bodyPart = BodyPart.SHOULDERS;
                break;
            case 8:
                this.bodyPart = BodyPart.TRICEPS;
                break;
            default:
                this.bodyPart = BodyPart.ABS;
                break;
        }
    }

    @Override
    public String toString() {
        return this.fitnessName + " : " +
                this.numReps + " : " +
                this.bodyPart + " : " +
                this.instructions + " : " +
                this.image;
    }
    public enum BodyPart {
        ABS, BACK, BICEPS, CALFS, CHEST, GLUTES, QUADS, SHOULDERS, TRICEPS
    }
}
