package edu.rosehulman.teamworkout.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.WorkoutAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private OnLogoutListener mListener;
    private WorkoutAdapter mAdapter;
    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);

        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_past_workouts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new WorkoutAdapter(getActivity(),recyclerView, getFragmentManager());
        recyclerView.setAdapter(mAdapter);

        final EditText searchWorkoutName = (EditText) rootView.findViewById(R.id.oldworkoutname);
        final EditText searchWorkoutDate = (EditText) rootView.findViewById(R.id.workoutdate);

        final Button searchWorkouts = (Button) rootView.findViewById(R.id.search_for_workouts);
        searchWorkouts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Only does one of the searches
                if(searchWorkoutName.getText().length() != 0 && searchWorkoutDate.getText().length() == 0){
                    mAdapter.searchWorkout(searchWorkoutName.getText().toString(), true);
                }else if(searchWorkoutDate.getText().length() != 0 && searchWorkoutName.getText().length() == 0){
                    mAdapter.searchWorkout(searchWorkoutDate.getText().toString(), false);
                }
            }
        });
        final EditText addWorkoutDate = (EditText) rootView.findViewById(R.id.addworkoutdate);
        final Button copyWorkout = (Button) rootView.findViewById(R.id.search_for_workouts);
        copyWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Copy Workout
                //mAdapter.addWorkout(addWorkoutDate, workoutToAdd);
            }
        });
        return rootView;
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.action_logout:
                Log.d("PK", "LOGOUT Menu Item Clicked!");
                mListener.onLogout();
                return true;
        }
        return false;
    }

    public interface  OnLogoutListener{
        void onLogout();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnLogoutListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnLogoutListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
