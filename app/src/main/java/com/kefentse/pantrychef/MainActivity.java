package com.kefentse.pantrychef;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PantryAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvEmptyState;
    private FloatingActionButton fabAddItem;
    private Button btnFindRecipes; // New button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewPantry);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        fabAddItem = findViewById(R.id.fabAddItem);
        btnFindRecipes = findViewById(R.id.btnFindRecipes); // Find the new button

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DatabaseHelper(this);

        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
                startActivity(intent);
            }
        });

        // NEW: Open Suggested Recipes Screen
        btnFindRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        List<PantryItem> items = dbHelper.getAllPantryItems();

        if (items.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new PantryAdapter(items, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(PantryItem item) {
        Intent intent = new Intent(MainActivity.this, AddEditItemActivity.class);
        intent.putExtra("edit_item", item);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(final PantryItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Ingredient")
                .setMessage("Are you sure you want to delete " + item.getName() + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deletePantryItem(item.getId());
                        loadPantryItems();
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // 1. This makes the 3-dots menu appear in the top bar
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // 2. This handles the click on the "Settings" menu item
    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            android.content.Intent intent = new android.content.Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}