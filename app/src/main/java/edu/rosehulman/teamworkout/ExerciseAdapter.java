package edu.rosehulman.teamworkout;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lashomjt on 1/24/2016.
 */
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{
    private List<ExerciseModel> mExercises;
    private Context mContext;
    private RecyclerView mRecylerView;


    public ExerciseAdapter(FragmentActivity context, RecyclerView recyclerView){
        this.mExercises = new ArrayList<>();
        this.mContext = context;
        this.mRecylerView = recyclerView;
    }
    @Override
    public ExerciseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_view, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExerciseAdapter.ViewHolder holder, int position) {
        ExerciseModel tmpExercise = mExercises.get(position);
        holder.mExerciseName.setText(tmpExercise.getName());
        holder.mSets.setText("Sets: " +tmpExercise.getSets());
        holder.mReps.setText("Reps: " +tmpExercise.getReps());
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public void addExercise(String name, String sets, String reps) {
        if(name != null || sets != null || reps != null) {
            ExerciseModel model = new ExerciseModel();
            model.setName(name);
            model.setSets(Integer.parseInt(sets));
            model.setReps(Integer.parseInt(reps));
            mExercises.add(model);
            notifyDataSetChanged();
        }
    }

    public List<ExerciseModel> getList(){
        return mExercises;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mExerciseName;
        private TextView mSets;
        private TextView mReps;
        public ViewHolder(View itemView) {
            super(itemView);
            mExerciseName = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            mReps = (TextView) itemView.findViewById(R.id.repsTextView);
            mSets = (TextView) itemView.findViewById(R.id.setsTextView);
        }
    }
}
