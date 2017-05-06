package com.tommymathews.slackersguidetohealth.service;

import com.tommymathews.slackersguidetohealth.model.Fitness;

import java.util.List;

public interface FitnessService {
    public void addFitnessToPlaylist( Fitness fitness );
    public Fitness getFitnessById( String id );
    public List<Fitness> getAllFitnessesSorted();
    public List<Fitness> getAllFitness();
}
