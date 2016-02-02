package edu.rosehulman.teamworkout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.teamworkout.Fragments.ExerciseViewFragment;
import edu.rosehulman.teamworkout.Fragments.LoginFragment;
import edu.rosehulman.teamworkout.Fragments.TodayWorkoutFragment;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder>{
    private List<WorkoutModel> listOfWorkouts;
    private Context mContext;
    private RecyclerView mRecylerView;
    private WorkoutModel mWorkout;
    private FragmentManager mFragmanager;


    public WorkoutAdapter(FragmentActivity context, RecyclerView recyclerView, FragmentManager fragmentManager) {
            this.listOfWorkouts = new ArrayList<>();
            this.mContext = context;
            this.mRecylerView = recyclerView;
            mWorkout = new WorkoutModel();
            createWorkout();
        mFragmanager = fragmentManager;
        }

    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkoutAdapter.ViewHolder holder, int position) {
        final ExerciseModel tmpExercise = mWorkout.getListOfExercises().get(position);
        holder.mExerciseName.setText(tmpExercise.getName());
        holder.mSets.setText("Sets: " +tmpExercise.getSets());
        holder.mReps.setText("Reps: " +tmpExercise.getReps());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = mFragmanager.beginTransaction();
                ExerciseViewFragment exerciseView = new ExerciseViewFragment();
                Bundle args = new Bundle();
                Log.d("Tag", "onClick: " + tmpExercise.getName());
                args.putParcelable(Constants.EXERCISE, tmpExercise);
                exerciseView.setArguments(args);
                ft.replace(R.id.fragment, exerciseView, "Exercises");
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWorkout.getListOfExercises().size();
    }

    public void createWorkout() {
        List<ExerciseModel> tmp = new ArrayList<>();
        for(int i= 0;i<5;i++){
            ExerciseModel mExercise = new ExerciseModel();
            mExercise.setName("Name" +i);
            mExercise.setReps(i);
            mExercise.setSets(i);
            tmp.add(mExercise);
        }
        mWorkout.setListOfExercises(tmp);
        listOfWorkouts.add(mWorkout);
    }
    public void editWorkout(){
    }

    public void removeWorkout(){
    }

    public void searchWorkout(String text, boolean bool) {
        //Do Firebase query
        if(bool){
            //If Name

        }else{
            //If date
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mExerciseName;
        private TextView mSets;
        private TextView mReps;
        public ViewHolder(View itemView) {
            super(itemView);
            mExerciseName = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            mReps = (TextView) itemView.findViewById(R.id.repsTextView);
            mSets = (TextView) itemView.findViewById(R.id.setsTextView);
        }

    }
}
