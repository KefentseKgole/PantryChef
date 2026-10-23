package com.kefentse.pantrychef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private List<PantryItem> pantryItems;
    private OnItemClickListener listener;

    // 1. Interface for clicks
    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
        void onItemLongClick(PantryItem item);
    }

    // 2. Update constructor to accept the listener
    public PantryAdapter(List<PantryItem> pantryItems, OnItemClickListener listener) {
        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem currentItem = pantryItems.get(position);

        holder.tvItemName.setText(currentItem.getName());
        String quantityText = currentItem.getQuantity() + " " + currentItem.getUnit();
        holder.tvItemQuantity.setText(quantityText);

        // 3. Set up the click listeners
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onItemClick(currentItem);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) listener.onItemLongClick(currentItem);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        TextView tvItemQuantity;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvItemQuantity = itemView.findViewById(R.id.tvItemQuantity);
        }
    }
}