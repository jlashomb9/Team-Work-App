package edu.rosehulman.teamworkout.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.models.ExerciseModel;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.adapters.SetAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExerciseViewFragment extends Fragment {
    private SetAdapter mAdapter;
    private ExerciseModel mExercise;

    public ExerciseViewFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mExercise = getArguments().getParcelable(Constants.EXERCISE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_view, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list_of_sets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new SetAdapter(mExercise.getSets());
        recyclerView.setAdapter(mAdapter);

        TextView exerciseName = (TextView) view.findViewById(R.id.exercise_name);
       if(mExercise != null) {
            exerciseName.setText(mExercise.getName());
        }
        return view;
    }

}
