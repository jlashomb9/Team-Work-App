package edu.rosehulman.teamworkout.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.adapters.WorkoutAdapter;
import edu.rosehulman.teamworkout.models.Player;

import static android.R.layout.simple_dropdown_item_1line;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWorkoutFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private OnLogoutListener mListener;
    private WorkoutAdapter mAdapter;
    private String userPath;
    private List<String> coachList;

    public TodayWorkoutFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null){
            userPath = getArguments().getString(Constants.FIREBASE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_today_workout, container, false);

        Toolbar mToolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.app_name);

//        getActivity().getMenuInflater().inflate(R.menu.main, mToolbar.getMenu());
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white));

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

//        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
//        drawer.setVisibility(View.VISIBLE);
//        NavigationView navigationView = (NavigationView) getActivity().findViewById(R.id.nav_view);
//        navigationView.setVisibility(View.VISIBLE);



        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.today_exercise_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        TextView todaysWorkoutName = (TextView) rootView.findViewById(R.id.todays_workout_name);
        TextView todaysWorkoutDate = (TextView) rootView.findViewById(R.id.todays_workout_date);

        mAdapter = new WorkoutAdapter(getActivity(),recyclerView, getFragmentManager(),userPath, todaysWorkoutName, todaysWorkoutDate);
        recyclerView.setAdapter(mAdapter);


        Button completeWorkoutButton = (Button) rootView.findViewById(R.id.complete_button_workout);
        completeWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.removeFromFirebase();
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
