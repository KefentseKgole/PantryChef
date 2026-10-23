package com.kefentse.pantrychef;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Fetches and displays recipes using the strict-matching algorithm
public class SuggestedRecipesActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {

    private RecyclerView recyclerView;
    private TextView tvEmptyRecipes;
    private DatabaseHelper dbHelper;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("What Can I Cook?");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerViewRecipes);
        tvEmptyRecipes = findViewById(R.id.tvEmptyRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {
        // This calls the strict-matching algorithm we wrote earlier!
        List<Recipe> matchedRecipes = dbHelper.getSuggestedRecipes();

        if (matchedRecipes.isEmpty()) {
            tvEmptyRecipes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyRecipes.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new RecipeAdapter(matchedRecipes, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        // Open the Recipe Detail screen and pass the recipe object
        android.content.Intent intent = new android.content.Intent(SuggestedRecipesActivity.this, RecipeDetailActivity.class);
        intent.putExtra("selected_recipe", recipe);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}