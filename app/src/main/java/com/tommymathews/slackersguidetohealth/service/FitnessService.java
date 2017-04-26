package com.tommymathews.slackersguidetohealth.service;

import com.tommymathews.slackersguidetohealth.model.Fitness;

import java.util.List;

public interface FitnessService {
    public void addFitness(Fitness fitness);
    public Fitness getFitnessByName(String fitness);
    public List<Fitness> getAllFitness();
}
