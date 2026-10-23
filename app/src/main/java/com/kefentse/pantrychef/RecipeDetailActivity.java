package com.kefentse.pantrychef;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Recipe Details");
        }

        TextView tvName = findViewById(R.id.tvDetailRecipeName);
        TextView tvIngredients = findViewById(R.id.tvDetailIngredients);
        TextView tvInstructions = findViewById(R.id.tvDetailInstructions);

        // Receive the recipe passed from the Suggested Recipes screen
        if (getIntent().hasExtra("selected_recipe")) {
            Recipe recipe = (Recipe) getIntent().getSerializableExtra("selected_recipe");

            if (recipe != null) {
                tvName.setText(recipe.getName());
                tvInstructions.setText(recipe.getInstructions());

                // Format the ingredients into a nice list
                StringBuilder ingText = new StringBuilder();
                for (RecipeIngredient ing : recipe.getIngredients()) {
                    ingText.append("- ").append(ing.getQuantity()).append(" ").append(ing.getName()).append("\n");
                }
                tvIngredients.setText(ingText.toString());
            }
        }
    }

    // Makes the back arrow in the top bar work
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}