package edu.rosehulman.teamworkout.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.models.ExerciseModel;
import edu.rosehulman.teamworkout.Fragments.ExerciseViewFragment;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.models.SetModel;
import edu.rosehulman.teamworkout.models.WorkoutModel;

/**
 * Created by lashomjt on 2/7/2016.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private List<WorkoutModel> listOfWorkouts;
    private Context mContext;
    private RecyclerView mRecylerView;
    private WorkoutModel mWorkout;
    private FragmentManager mFragmanager;


    public SearchAdapter(FragmentActivity context, RecyclerView recyclerView, FragmentManager fragmentManager) {
        this.listOfWorkouts = new ArrayList<>();
        this.mContext = context;
        this.mRecylerView = recyclerView;
        mWorkout = new WorkoutModel();
        mFragmanager = fragmentManager;

    }
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Log.d(Constants.TAG, "mWorkout: " + listOfWorkouts);
        final ExerciseModel tmpExercise = listOfWorkouts.get(position).getExercises().get(position);
        holder.mExerciseName.setText(tmpExercise.getName());
        holder.mSets.setText("Sets: " +tmpExercise.getSets().size());
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

    public void searchWorkout(String text, boolean bool) {
        Firebase ref = new Firebase(MainActivity.USER_AUTH + Constants.WORKOUT);
        Log.d(Constants.TAG, "searchWorkout: " + ref.getPath()
        );
        //Do Firebase query
        if(bool){
            Log.d(Constants.TAG, "searchWorkout: name");
            //If Name
            Query todays_workout = ref.orderByChild("name").equalTo(text);
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


        }else{
            //If date
            Log.d(Constants.TAG, "searchWorkout: Date");
            Query todays_workout = ref.orderByChild("workoutDate").equalTo(text);
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
        Log.d(Constants.TAG, "searched Workouts: " + listOfWorkouts);
    }

    public void emailWorkout() {
        WorkoutModel workout = listOfWorkouts.get(0);
        List<ExerciseModel> listOfExercise = workout.getExercises();
        String emailText = "Workout Name: " + workout.getName() +"\n" + "Exercises\n";
        for(int i =0; i<listOfExercise.size();i++){
            emailText = emailText +
                    listOfExercise.get(i).getName() + "\n";
            List<SetModel> listOfSets = listOfExercise.get(i).getSets();
            for(int j =0; j<listOfSets.size();j++){
                int setNum = j+1;
                emailText = emailText + setNum +": Reps:" + listOfSets.get(j).getReps() + " Target Weight: " + listOfSets.get(i).getWeight() + "\n";
            }
            emailText = emailText + "\n";
        }
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
//        intent.putExtra(Intent.EXTRA_EMAIL, "jtlashomb9@gmail.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this Workout");
        intent.putExtra(Intent.EXTRA_TEXT, emailText);
        if (intent.resolveActivity(mContext.getPackageManager()) != null) {
            mContext.startActivity(intent);
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
