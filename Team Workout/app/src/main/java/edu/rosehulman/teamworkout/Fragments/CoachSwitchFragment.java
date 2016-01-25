package edu.rosehulman.teamworkout.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.teamworkout.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoachSwitchFragment extends Fragment {


    public CoachSwitchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach_switch, container, false);
    }

}
