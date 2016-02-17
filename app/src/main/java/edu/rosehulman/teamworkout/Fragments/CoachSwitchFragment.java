package edu.rosehulman.teamworkout.Fragments;


import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.adapters.PlayerAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class CoachSwitchFragment extends Fragment {
    private PlayerAdapter mAdapter;

    public CoachSwitchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_coach_switch, container, false);

        FloatingActionButton fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlayer();
            }
        });


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.players_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        mAdapter = new PlayerAdapter(getActivity(),recyclerView, getFragmentManager());
        recyclerView.setAdapter(mAdapter);


        return view;
    }

    private void addPlayer() {

        DialogFragment dialogFragment = new DialogFragment(){
            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View view = getActivity().getLayoutInflater().inflate(R.layout.add_player,null,false);
                final EditText playerName = (EditText) view.findViewById(R.id.new_player);
                builder.setTitle("Add Player")
                        .setView(view)
                        .setNegativeButton(android.R.string.cancel,null)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mAdapter.addPlayer(playerName.getText().toString());
                            }
                        });

                return builder.create();
            }
        };
        dialogFragment.show(getActivity().getFragmentManager(), "add set");
    }


}
