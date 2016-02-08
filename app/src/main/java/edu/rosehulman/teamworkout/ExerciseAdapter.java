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
        holder.mSets.setText("Sets: " +tmpExercise.getSets().size());
    }

    @Override
    public int getItemCount() {
        return mExercises.size();
    }

    public List<ExerciseModel> getList(){
        return mExercises;
    }

    public void addExercise(String name, List<SetModel> list_of_sets) {
        ExerciseModel exercise = new ExerciseModel();
        exercise.setName(name);
        exercise.setSets(list_of_sets);
        mExercises.add(exercise);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mExerciseName;
        private TextView mSets;
        public ViewHolder(View itemView) {
            super(itemView);
            mExerciseName = (TextView) itemView.findViewById(R.id.exerciseNameTextView);
            mSets = (TextView) itemView.findViewById(R.id.setsTextView);
        }
    }
}
