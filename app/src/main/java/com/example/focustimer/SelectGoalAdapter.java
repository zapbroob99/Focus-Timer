package com.example.focustimer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class SelectGoalAdapter extends RecyclerView.Adapter<SelectGoalAdapter.SelectGoalViewHolder> {

    ArrayList<SelectGoalHelperClass> goals;


    public SelectGoalAdapter(ArrayList<SelectGoalHelperClass> goals) {

        this.goals = goals;
    }

    @NonNull
    public SelectGoalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //creates a new instance
        // of MostViewedViewHolder
        // by inflating a layout from the specified XML resource.

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_select_card_design, parent, false);

        SelectGoalViewHolder mostViewedViewHolder = new SelectGoalViewHolder(view);

        return mostViewedViewHolder;

    } //end method

    public void onBindViewHolder(@NonNull SelectGoalViewHolder holder, int position) {
        SelectGoalHelperClass helperClass = goals.get(position);
        holder.textView.setText(helperClass.getTitle());

    }


    public int getItemCount(){
        return goals.size();
    }
    public static class SelectGoalViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public SelectGoalViewHolder(@NonNull View itemView){
            super(itemView);

            textView = itemView.findViewById(R.id.ms_title);
        }
    }
}
