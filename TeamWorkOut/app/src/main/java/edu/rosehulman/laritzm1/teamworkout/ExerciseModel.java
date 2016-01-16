package edu.rosehulman.laritzm1.teamworkout;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class ExerciseModel {
    private String name;
    private int sets;
    private int reps;
    private int weight;

    public ExerciseModel(){
        //blank for now
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
