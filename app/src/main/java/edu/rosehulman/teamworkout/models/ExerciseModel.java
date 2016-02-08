package edu.rosehulman.teamworkout.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class ExerciseModel implements Parcelable {
    private String name;
    private List<SetModel> sets;

    public ExerciseModel(){
        //blank for now
    }

    protected ExerciseModel(Parcel in) {
        name = in.readString();
        sets = in.readArrayList(SetModel.class.getClassLoader());
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

    public List<SetModel> getSets(){
        return sets;
    }
    public void setSets(List<SetModel> sets){
        this.sets = sets;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(sets);

    }
}
