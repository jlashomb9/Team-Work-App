package edu.rosehulman.teamworkout.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.Users.Player;
import edu.rosehulman.teamworkout.models.ExerciseModel;
import edu.rosehulman.teamworkout.Fragments.ExerciseViewFragment;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.models.WorkoutModel;

/**
 * Created by laritzm1 on 1/16/2016.
 */
public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.ViewHolder>{
    private List<WorkoutModel> listOfWorkouts;
    private Context mContext;
    private RecyclerView mRecylerView;
    private WorkoutModel mWorkout;
    private FragmentManager mFragmanager;
    private String firebaseUserPath;
    private String coachPath;
    private String ID;


    public WorkoutAdapter(FragmentActivity context, RecyclerView recyclerView, FragmentManager fragmentManager, String userPath) {
            this.listOfWorkouts = new ArrayList<>();
            this.mContext = context;
            this.mRecylerView = recyclerView;
            mWorkout = new WorkoutModel();
            this.firebaseUserPath = userPath;
            coachPath = getID();
            getCoachWorkoutPath();
            if(listOfWorkouts.isEmpty()) {
                addTodaysWorkout();
            }
            mFragmanager = fragmentManager;
        }

    private void getCoachWorkoutPath() {
        final Firebase coachRef = new Firebase(Constants.USER_PATH);
        coachRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(Constants.TAG, "onChildAdded: " + s);
//                Query coachQuery = coachRef.child("workouts").orderByChild("shareID");//.equalTo(coachPath);
                Firebase coachQuery = new Firebase(Constants.USER_PATH + "/" +s + Constants.WORKOUT);
                coachQuery.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot.getValue() != null) {
//                            Log.d(Constants.TAG, "onChildAdded: "+ dataSnapshot.getValue());
                            WorkoutModel workout = dataSnapshot.getValue(WorkoutModel.class);
                            Log.d(Constants.TAG, "onChildAdded: " + workout.getShareID());
                            if (workout.getShareID() != null) {
                                Log.d(Constants.TAG, "onChildAdded: " + workout.getShareID());
                                if (workout.getShareID().equals("rosefootball") && listOfWorkouts.isEmpty()) {
                                Log.d(Constants.TAG, "onChildAdded: " + workout.getShareID());
                                listOfWorkouts.add(workout);
                                notifyDataSetChanged();

                                }
                            }
                        }
                    }


                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void addTodaysWorkout() {
        Date currentDate = new Date();
        String date = new SimpleDateFormat("yyyyMMdd").format(currentDate);
        Firebase ref = new Firebase(MainActivity.USER_AUTH + Constants.WORKOUT);
        Query todays_workout = ref.orderByChild("workoutDate").equalTo(date);
        todays_workout.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WorkoutModel workoutModel = dataSnapshot.getValue(WorkoutModel.class);
                listOfWorkouts.add(workoutModel);
                notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WorkoutAdapter.ViewHolder holder, int position) {
        Log.d(Constants.TAG, "mWorkout: " + listOfWorkouts);
        final ExerciseModel tmpExercise = listOfWorkouts.get(position).getExercises().get(position);
        holder.mExerciseName.setText(tmpExercise.getName());
        holder.mSets.setText("Sets: " + tmpExercise.getSets().size());
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
        return listOfWorkouts.size();
    }

    public void createWorkout() {
        List<ExerciseModel> tmp = new ArrayList<>();
        for(int i= 0;i<5;i++){
            ExerciseModel mExercise = new ExerciseModel();
            mExercise.setName("Name" +i);
            tmp.add(mExercise);
        }
        mWorkout.setExercises(tmp);
        listOfWorkouts.add(mWorkout);
    }
    public void editWorkout(){
    }

    public void removeWorkout(){
    }

    public String getID() {
        final String[] ID = {""};
        Firebase idRef = new Firebase(MainActivity.USER_AUTH + "/userInfo");
        idRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Player player = dataSnapshot.getValue(Player.class);
                ID[0] = player.getCoachID();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return ID[0];
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
