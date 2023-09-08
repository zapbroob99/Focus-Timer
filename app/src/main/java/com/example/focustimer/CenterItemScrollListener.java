package com.example.focustimer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.focustimer.user.UserClass;

public class CenterItemScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private int g_centeredPosition=0;
    private Context context;


    public CenterItemScrollListener(RecyclerView recyclerView,Context context) {
        this.recyclerView = recyclerView;
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        this.context=context;
    }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
            int centeredPosition = (firstVisibleItemPosition + lastVisibleItemPosition) / 2;
            g_centeredPosition=centeredPosition;

            // Update the appearance of centered item
            updateCenteredItem(centeredPosition);
        }

    private void updateCenteredItem(int centeredPosition) {
        int itemCount = recyclerView.getAdapter().getItemCount();

        for (int i = 0; i < itemCount; i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                // Reset the appearance for all items
                viewHolder.itemView.getBackground().setTint(Color.WHITE);
                viewHolder.itemView.setOnClickListener(null); // Remove any previous click listeners


            }
        }

        RecyclerView.ViewHolder centeredViewHolder = recyclerView.findViewHolderForAdapterPosition(centeredPosition);
        if (centeredViewHolder != null) {
            // Highlight the centered item here (e.g., change its background color)
            int selectedColor = Color.rgb(241, 241, 241);
            centeredViewHolder.itemView.getBackground().setTint(selectedColor);
            TextView textView = (TextView)centeredViewHolder.itemView.findViewById(R.id.ms_title);
            UserClass.setCurrentGoal(UserClass.goalsList.get(0));
            centeredViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserClass.adjustGoalSettings(context).show();
                }
            });



        }

        // Additionally, check if we are at the right-most edge and highlight the last item //todo: it does not work
        if (centeredPosition == itemCount - 1) {
            RecyclerView.ViewHolder lastViewHolder = recyclerView.findViewHolderForAdapterPosition(itemCount - 1);
            if (lastViewHolder != null) {

                // Highlight the centered item here (e.g., change its background color)
                int selectedColor = Color.rgb(241, 241, 241);
                lastViewHolder.itemView.getBackground().setTint(selectedColor);
                TextView textView = (TextView)lastViewHolder.itemView.findViewById(R.id.ms_title);
            }
        }
    }

    public RecyclerView.ViewHolder returnCentered(int center){
        RecyclerView.ViewHolder centeredViewHolder = recyclerView.findViewHolderForAdapterPosition(center);
        return centeredViewHolder;

    }
}
