package com.example.focustimer;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CenterItemScrollListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private int g_centeredPosition=0;

    public CenterItemScrollListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
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
            }
        }

        RecyclerView.ViewHolder centeredViewHolder = recyclerView.findViewHolderForAdapterPosition(centeredPosition);
        if (centeredViewHolder != null) {
            // Highlight the centered item here (e.g., change its background color)
            //centeredViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(centeredViewHolder.itemView.getContext(), R.color.mainBlue));
           // centeredViewHolder.itemView.setBackgroundResource(R.color.mainBlue);
            int selectedColor = Color.rgb(43, 139, 255);
            centeredViewHolder.itemView.getBackground().setTint(selectedColor);

        }

        // Additionally, check if we are at the right-most edge and highlight the last item //todo: it does not work
        if (centeredPosition == itemCount - 1) {
            RecyclerView.ViewHolder lastViewHolder = recyclerView.findViewHolderForAdapterPosition(itemCount - 1);
            if (lastViewHolder != null) {
               // lastViewHolder.itemView.setBackgroundColor(ContextCompat.getColor(lastViewHolder.itemView.getContext(), R.color.mainBlue));

            }
        }
    }
    public int getG_centeredPosition(){
        return g_centeredPosition;
    }
    public RecyclerView.ViewHolder returnCentered(int center){
        RecyclerView.ViewHolder centeredViewHolder = recyclerView.findViewHolderForAdapterPosition(center);
        return centeredViewHolder;

    }
}
