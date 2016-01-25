package edu.rosehulman.teamworkout;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class ExerciseModel implements Parcelable {
    private String name;
    private int sets;
    private int reps;
    private int weight;

    public ExerciseModel(){
        //blank for now
    }

    protected ExerciseModel(Parcel in) {
        name = in.readString();
        sets = in.readInt();
        reps = in.readInt();
        weight = in.readInt();
    }

    public static final Creator<ExerciseModel> CREATOR = new Creator<ExerciseModel>() {
        @Override
        public ExerciseModel createFromParcel(Parcel in) {
            return new ExerciseModel(in);
        }

        @Override
        public ExerciseModel[] newArray(int size) {
            return new ExerciseModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(sets);
        dest.writeInt(reps);
        dest.writeInt(weight);
    }
}
