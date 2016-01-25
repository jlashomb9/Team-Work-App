package edu.rosehulman.teamworkout;

import java.util.Date;
import java.util.List;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class WorkoutModel {
    private List<ExerciseModel> listOfExercises;
    private Date workoutDate;
    private String name;
    public List<ExerciseModel> getListOfExercises() {
        return listOfExercises;
    }

    public void setListOfExercises(List<ExerciseModel> listOfExercises) {
        this.listOfExercises = listOfExercises;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getWorkoutDate() {
        return workoutDate;
    }

    public void setWorkoutDate(Date workoutDate) {
        this.workoutDate = workoutDate;
    }
}
