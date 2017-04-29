package com.tommymathews.slackersguidetohealth.service;

import com.tommymathews.slackersguidetohealth.model.User;

import java.util.List;

/**
 * Created by cj on 4/19/17.
 */

public interface UserService {
    public void addUser(User user);

    public User getUserByEmail(String email);

    public List<User> getAllUsers();

    public int incrementFitnessProgress(String email); //return how many rows were updated, should always only be 1

    public int incrementFoodProgress(String email); //return how many rows were updated, should always only be 1

    public int incrementEventsProgress(String email); //return how many rows were updated, should always only be 1
}
