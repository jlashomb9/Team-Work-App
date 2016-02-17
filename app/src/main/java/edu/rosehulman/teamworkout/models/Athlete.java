package edu.rosehulman.teamworkout.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by lashomjt on 2/16/2016.
 */
public class Athlete {

    private String name;
    private List<WorkoutModel> workouts;
    @JsonIgnore
    private String key;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkoutModel> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<WorkoutModel> workouts) {
        this.workouts = workouts;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
