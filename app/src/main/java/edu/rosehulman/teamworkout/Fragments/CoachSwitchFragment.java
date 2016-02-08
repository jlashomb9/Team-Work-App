package edu.rosehulman.teamworkout.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import edu.rosehulman.teamworkout.Constants;
import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.Users.Player;

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
        View view =inflater.inflate(R.layout.fragment_coach_switch, container, false);

        Button changeCoachButton = (Button) view.findViewById(R.id.changeCoachbutton);
        final EditText coachId = (EditText) view.findViewById(R.id.editTextCoachID);
        changeCoachButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCoach(coachId.getText().toString());

            }
        });

        return view;
    }
    private void changeCoach(String coachId) {
        Firebase ref = new Firebase(MainActivity.USER_AUTH + Constants.USER_INFO);
        Player player = new Player();
        player.setCoachID(coachId);
        ref.push().setValue(player);
    }


}
