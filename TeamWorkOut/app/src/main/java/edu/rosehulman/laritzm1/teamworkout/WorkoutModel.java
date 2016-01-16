package edu.rosehulman.laritzm1.teamworkout;

import java.util.Date;
import java.util.List;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class WorkoutModel {
    private List<ExerciseModel> listOfExercises;
    private Date workoutDate;

    public List<ExerciseModel> getListOfExercises() {
        return listOfExercises;
    }

    public void setListOfExercises(List<ExerciseModel> listOfExercises) {
        this.listOfExercises = listOfExercises;
    }
}
