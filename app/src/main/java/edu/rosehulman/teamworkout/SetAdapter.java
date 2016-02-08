package edu.rosehulman.teamworkout;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lashomjt on 1/25/2016.
 */
public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {
    private List<SetModel> mSets;


    public SetAdapter(List<SetModel> reps){
        this.mSets = new ArrayList();
        Log.d(Constants.TAG, "SetAdapter: " + mSets);
        mSets = reps;
        notifyDataSetChanged();

    }

    private void createSets() {

    }

    @Override
    public SetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SetAdapter.ViewHolder holder, int position) {
        SetModel tmp = mSets.get(position);
        holder.reps.setText(tmp.getReps() +" reps");
        holder.tWeight.setText("Target is: " +tmp.getWeight());
    }

    @Override
    public int getItemCount() {
        return mSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView reps;
        private TextView tWeight;
        public ViewHolder(View itemView) {
            super(itemView);
            reps = (TextView) itemView.findViewById(R.id.reps);
            tWeight=(TextView) itemView.findViewById(R.id.target_weight);
        }
    }
}
