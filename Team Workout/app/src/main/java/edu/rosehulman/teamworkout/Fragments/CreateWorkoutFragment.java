package edu.rosehulman.teamworkout.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.teamworkout.ExerciseAdapter;
import edu.rosehulman.teamworkout.ExerciseModel;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.WorkoutAdapter;
import edu.rosehulman.teamworkout.WorkoutModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateWorkoutFragment extends Fragment  {
    private ExerciseAdapter mAdapter;

    public CreateWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_workout, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_of_exercises);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new ExerciseAdapter(getActivity(),recyclerView);
        recyclerView.setAdapter(mAdapter);

        final Button addWorkout = (Button) view.findViewById(R.id.add_new_workout);
        final EditText workoutName = (EditText) view.findViewById(R.id.workoutname);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout(workoutName.getText().toString());
            }
        });
        final EditText exerciseName = (EditText) view.findViewById(R.id.edit_text_exercise_name);
        final EditText exerciseSets = (EditText) view.findViewById(R.id.edit_text_exercise_sets);
        final EditText exerciseReps = (EditText) view.findViewById(R.id.edit_text_exercise_reps);
        final Button addExercise = (Button) view.findViewById(R.id.add_new_exercise);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addExercise(exerciseName.getText().toString(),exerciseSets.getText().toString(),exerciseReps.getText().toString());
            }
        });
        return view;
    }
    private void addWorkout(String name){
        WorkoutModel model = new WorkoutModel();
        List<ExerciseModel> tmp = new ArrayList<>();
        for(int i =0;i<mAdapter.getItemCount();i++){
            tmp.add(mAdapter.getList().get(i));
        }
        model.setListOfExercises(tmp);

        model.setName(name);
        MainActivity.allWorkouts.add(model);

    }


}
