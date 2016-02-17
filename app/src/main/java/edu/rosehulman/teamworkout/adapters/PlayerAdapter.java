package edu.rosehulman.teamworkout.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.teamworkout.MainActivity;
import edu.rosehulman.teamworkout.R;
import edu.rosehulman.teamworkout.models.Player;

/**
 * Created by lashomjt on 2/8/2016.
 */
public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {
    private List<Player> mPlayers;
    private Context mContext;
    private RecyclerView mRecylerView;
    private FragmentManager mFragmanager;
    private Firebase mPlayerRef;

    public PlayerAdapter(FragmentActivity activity, RecyclerView recyclerView, FragmentManager fragmentManager) {
        mPlayers = new ArrayList<>();
        mContext = activity;
        mRecylerView = recyclerView;
        mFragmanager = fragmentManager;
        mPlayerRef = new Firebase(MainActivity.USER_AUTH + "/players");
        mPlayerRef.addChildEventListener(new PlayerChildEventListener());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_recycler_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mPlayerName.setText(mPlayers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mPlayers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mPlayerName;
        public ViewHolder(View itemView) {
            super(itemView);
            mPlayerName = (TextView) itemView.findViewById(R.id.playerName);
        }
    }

    public void addPlayer(String playerName) {
        Player player = new Player();
        player.setName(playerName);
        mPlayerRef.push().setValue(player);
    }

    private class PlayerChildEventListener implements ChildEventListener {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Player player = dataSnapshot.getValue(Player.class);
            player.setKey(dataSnapshot.getKey());
            mPlayers.add(player);
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
    }
}
