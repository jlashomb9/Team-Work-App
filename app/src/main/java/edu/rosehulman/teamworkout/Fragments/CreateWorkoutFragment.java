package edu.rosehulman.teamworkout.Fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.adapters.ExerciseAdapter;
import edu.rosehulman.teamworkout.models.ExerciseModel;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.models.SetModel;
import edu.rosehulman.teamworkout.models.WorkoutModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateWorkoutFragment extends Fragment  {
    private ExerciseAdapter mAdapter;
    private Firebase mWorkoutRef;

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
        final EditText shareID = (EditText) view.findViewById(R.id.shareID);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWorkout(workoutName.getText().toString(), shareID.getText().toString());
            }
        });

        mWorkoutRef = new Firebase(MainActivity.USER_AUTH + Constants.WORKOUT);
        final EditText exerciseName = (EditText) view.findViewById(R.id.edit_text_exercise_name);
        final EditText exerciseSets = (EditText) view.findViewById(R.id.edit_text_exercise_sets);
        final Button addExercise = (Button) view.findViewById(R.id.add_new_exercise);
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addingSet(exerciseName.getText().toString());
            }
        });
        return view;
    }

    private void addingSet(final String name) {
        final List<SetModel> list_of_sets = new ArrayList<>();
        DialogFragment dialogFragment = new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Add Reps");
                final View view = getActivity().getLayoutInflater().inflate(R.layout.add_sets,null,false);
                builder.setView(view);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText numberOfReps = (EditText) view.findViewById(R.id.set1_rep);
                        EditText targetWeight = (EditText) view.findViewById(R.id.set1_target_weight);
                        SetModel setModel = new SetModel();
                        setModel.setReps(Integer.parseInt(numberOfReps.getText().toString()));
                        setModel.setWeight(Integer.parseInt(targetWeight.getText().toString()));
                        if (setModel != null) {
                            list_of_sets.add(setModel);
                        }

                        numberOfReps = (EditText) view.findViewById(R.id.set2_rep);
                        targetWeight = (EditText) view.findViewById(R.id.set2_target_weight);
                        setModel = new SetModel();
                        setModel.setReps(Integer.parseInt(numberOfReps.getText().toString()));
                        setModel.setWeight(Integer.parseInt(targetWeight.getText().toString()));
                        if (setModel != null) {
                            list_of_sets.add(setModel);
                        }
                        numberOfReps = (EditText) view.findViewById(R.id.set3_rep);
                        targetWeight = (EditText) view.findViewById(R.id.set3_target_weight);
                        setModel = new SetModel();
                        setModel.setReps(Integer.parseInt(numberOfReps.getText().toString()));
                        setModel.setWeight(Integer.parseInt(targetWeight.getText().toString()));
                        if (setModel != null) {
                            list_of_sets.add(setModel);
                        }

                        numberOfReps = (EditText) view.findViewById(R.id.set4_rep);
                        targetWeight = (EditText) view.findViewById(R.id.set4_target_weight);
                        setModel = new SetModel();
                        setModel.setReps(Integer.parseInt(numberOfReps.getText().toString()));
                        setModel.setWeight(Integer.parseInt(targetWeight.getText().toString()));
                        if (setModel != null) {
                            list_of_sets.add(setModel);
                        }

                        numberOfReps = (EditText) view.findViewById(R.id.set5_rep);
                        targetWeight = (EditText) view.findViewById(R.id.set5_target_weight);
                        setModel = new SetModel();

                        setModel.setReps(Integer.parseInt(numberOfReps.getText().toString()));
                        setModel.setWeight(Integer.parseInt(targetWeight.getText().toString()));
                        if (setModel != null) {
                            list_of_sets.add(setModel);
                        }
                        mAdapter.addExercise(name, list_of_sets);

                    }
                });
                return builder.create();
            }
        };
        dialogFragment.show(getActivity().getFragmentManager(), "add set");
    }

    private void addWorkout(String name, String shareID){
        WorkoutModel model = new WorkoutModel();
        List<ExerciseModel> tmp = new ArrayList<>();
        for(int i =0;i<mAdapter.getItemCount();i++){
            tmp.add(mAdapter.getList().get(i));
        }
        model.setExercises(tmp);
        Date currentDate = new Date();
        String date = new SimpleDateFormat("yyyyMMdd").format(currentDate);

        Log.d(Constants.TAG, "currentDate: "+ date);
        model.setWorkoutDate(date);
        model.setName(name);
        model.setShareID(shareID);
//        MainActivity.allWorkouts.add(model);
        mWorkoutRef.push().setValue(model);

    }



}
