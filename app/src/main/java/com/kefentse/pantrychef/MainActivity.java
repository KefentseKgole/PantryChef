package com.kefentse.pantrychef;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PantryAdapter adapter;
    private DatabaseHelper dbHelper;
    private TextView tvEmptyState;
    private FloatingActionButton fabAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        recyclerView = findViewById(R.id.recyclerViewPantry);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        fabAddItem = findViewById(R.id.fabAddItem);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize database
        dbHelper = new DatabaseHelper(this);

        // Button click (we will connect this to the Add screen next)
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Add Item screen coming soon!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // onResume runs every time you come back to this screen, so the list is always fresh
    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        // Get data from database
        List<PantryItem> items = dbHelper.getAllPantryItems();

        // Check if empty to show/hide the message
        if (items.isEmpty()) {
            tvEmptyState.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmptyState.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        // Attach adapter to the list
        adapter = new PantryAdapter(items);
        recyclerView.setAdapter(adapter);
    }
}