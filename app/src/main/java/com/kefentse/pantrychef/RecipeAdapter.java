package com.kefentse.pantrychef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.recipeList = recipeList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = recipeList.get(position);

        holder.tvRecipeName.setText(currentRecipe.getName());

        // Create a quick string to show what's inside the recipe
        StringBuilder ingredientsText = new StringBuilder("Requires: ");
        for (int i = 0; i < currentRecipe.getIngredients().size(); i++) {
            ingredientsText.append(currentRecipe.getIngredients().get(i).getName());
            if (i < currentRecipe.getIngredients().size() - 1) {
                ingredientsText.append(", ");
            }
        }
        holder.tvRecipeIngredients.setText(ingredientsText.toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onRecipeClick(currentRecipe);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView tvRecipeName;
        TextView tvRecipeIngredients;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvRecipeIngredients = itemView.findViewById(R.id.tvRecipeIngredients);
        }
    }
}