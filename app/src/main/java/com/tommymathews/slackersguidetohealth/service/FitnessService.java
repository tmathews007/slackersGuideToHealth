package com.tommymathews.slackersguidetohealth.service;

import com.tommymathews.slackersguidetohealth.model.Fitness;

import java.util.List;

public interface FitnessService {
    public Fitness getFitnessById( String id );
    List<Fitness> getFitnessesByName(String name);
    public List<Fitness> getFitnessesByBodyPart(Fitness.BodyPart bodyPart);
    public List<Fitness> getAllFitness();
    public void addFitness(Fitness fitness);
}
