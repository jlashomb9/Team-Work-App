package edu.rosehulman.teamworkout.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class WorkoutModel {
    private List<ExerciseModel> exercises;
    private String workoutDate;
    private String workoutName;
    private String playerName;
    @JsonIgnore
    private String key;
    public List<ExerciseModel> getExercises() {
        return exercises;
    }

    public void setExercises(List<ExerciseModel> exercises) {
        this.exercises = exercises;
    }

    public String getName() {
        return workoutName;
    }

    public void setName(String name) {
        this.workoutName = name;
    }

    public String getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(String workoutDate) {
        this.workoutDate = workoutDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
