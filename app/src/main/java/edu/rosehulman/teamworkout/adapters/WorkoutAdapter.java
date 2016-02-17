package edu.rosehulman.teamworkout.adapters;

import android.content.Context;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.models.Player;
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
    private FragmentManager mFragmanager;
    private TextView mWorkoutName;




    public WorkoutAdapter(FragmentActivity context, RecyclerView recyclerView, FragmentManager fragmentManager, String userPath, TextView workoutName, TextView workoutDate) {
            this.listOfWorkouts = new ArrayList<>();
            this.mContext = context;
            this.mRecylerView = recyclerView;
            addTodaysWorkout();
            mFragmanager = fragmentManager;
            mWorkoutName = workoutName;
            addingWorkoutDate(workoutDate);

        }

    public void addingWorkoutDate(TextView workoutDate){
        Date currentDate = new Date();
        String date = new SimpleDateFormat("MM/dd/yyyy").format(currentDate);
        workoutDate.setText(date);
    }

    private void addTodaysWorkout() {
        Date currentDate = new Date();
        String date = new SimpleDateFormat("yyyyMMdd").format(currentDate);
        Firebase ref = new Firebase(MainActivity.USER_AUTH);
        Query todays_workout = ref.orderByChild("workoutDate").equalTo(date);
        todays_workout.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                WorkoutModel workoutModel = dataSnapshot.getValue(WorkoutModel.class);
                workoutModel.setKey(dataSnapshot.getKey());
                listOfWorkouts.add(workoutModel);
                notifyDataSetChanged();
                mWorkoutName.setText(workoutModel.getName());
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
//                Log.d("Tag", "onClick: " + tmpExercise.getName());
                args.putParcelable(Constants.EXERCISE, tmpExercise);
                exerciseView.setArguments(args);
                ft.replace(R.id.fragment, exerciseView, "Exercises");
                ft.addToBackStack("todays_exercises");
                ft.commit();
            }
        });
    }
    @Override
    public WorkoutAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listOfWorkouts.size();
    }

    public void removeFromFirebase() {
        Firebase ref = new Firebase(MainActivity.USER_AUTH);
        ref.addChildEventListener(new ChildEventListener() {
            private int remove(String key) {
                for (WorkoutModel workoutModel : listOfWorkouts) {
                    if (workoutModel.getKey().equals(key)) {
                        int foundPos = listOfWorkouts.indexOf(workoutModel);
                        listOfWorkouts.remove(workoutModel);
                        return foundPos;
                    }
                }
                return -1;
            }
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int position = remove(dataSnapshot.getKey());
                if (position >= 0) {
                    notifyItemRemoved(position);
                }
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        ref.child(listOfWorkouts.get(0).getKey()).removeValue();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mExerciseName;
        private TextView mSets;
        public ViewHolder(View itemView) {
            super(itemView);
            mExerciseName = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            mSets = (TextView) itemView.findViewById(R.id.setsTextView);
        }

    }
}
