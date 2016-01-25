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

import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.WorkoutAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayWorkoutFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    private OnLogoutListener mListener;
    private WorkoutAdapter mAdapter;
    public TodayWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        mAdapter = new WorkoutAdapter(getActivity(),recyclerView);
        recyclerView.setAdapter(mAdapter);
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
