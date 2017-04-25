package com.tommymathews.slackersguidetohealth.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by cj on 4/19/17.
 */

public class User implements Serializable{
    private String id;
    private String name;
    private String email;
    private String password;
    private Gender gender;
    private int age;
    private int weight;
    private int height;
    private Goal fitnessGoal;

    public User() {
        id = UUID.randomUUID().toString();
    }

    public User(String name, String email, String password, int gender, int age, int weight, int height,
                int fitnessGoal) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = Gender.values()[gender % 2];
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.fitnessGoal = Goal.values()[fitnessGoal % 3];
    }

    public User(String name, String email, String password, Gender gender, int age, int weight, int height,
                Goal fitnessGoal) {
        id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.fitnessGoal = fitnessGoal;

    }

    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " : " + email + " : " + gender + " : " + age + " : " + weight + " : " + convertHeight(height) + " : " + fitnessGoal;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = Gender.values()[gender % 2];
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Goal getFitnessGoal() {
        return fitnessGoal;
    }

    public void setFitnessGoal(Goal fitnessGoal) {
        this.fitnessGoal = fitnessGoal;
    }

    public void setFitnessGoal(int fitnessGoal) {
        this.fitnessGoal = Goal.values()[fitnessGoal % 3];
    }

    public static String convertHeight(int height) {
        return height/12 + "' " + height % 12 + "\"";
    }

    public enum Gender {
        MALE, FEMALE
    }

    public enum Goal {
        FITNESS, FOOD, FUN
    }
}

