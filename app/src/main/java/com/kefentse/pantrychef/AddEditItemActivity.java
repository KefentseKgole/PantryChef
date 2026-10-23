package com.kefentse.pantrychef;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditItemActivity extends AppCompatActivity {

    private EditText etName, etQuantity, etUnit, etExpiry;
    private Button btnSave;
    private DatabaseHelper dbHelper;

    // This will hold the item if we are editing, or stay null if we are adding
    private PantryItem existingItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etName = findViewById(R.id.etName);
        etQuantity = findViewById(R.id.etQuantity);
        etUnit = findViewById(R.id.etUnit);
        etExpiry = findViewById(R.id.etExpiry);
        btnSave = findViewById(R.id.btnSave);

        dbHelper = new DatabaseHelper(this);

        // Check if we are editing an existing item
        if (getIntent().hasExtra("edit_item")) {
            existingItem = (PantryItem) getIntent().getSerializableExtra("edit_item");

            if (existingItem != null) {
                // Fill the text boxes with the old data
                etName.setText(existingItem.getName());
                etQuantity.setText(String.valueOf(existingItem.getQuantity()));
                etUnit.setText(existingItem.getUnit());
                etExpiry.setText(existingItem.getExpiryDate());

                // Change the screen title and button text
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle("Edit Ingredient");
                }
                btnSave.setText("Update Ingredient");
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle("Add Ingredient");
            }
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
            }
        });
    }

    private void saveItem() {
        String name = etName.getText().toString().trim();
        String quantityStr = etQuantity.getText().toString().trim();
        String unit = etUnit.getText().toString().trim();
        String expiry = etExpiry.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            return;
        }
        if (TextUtils.isEmpty(quantityStr)) {
            etQuantity.setError("Quantity is required");
            return;
        }
        if (TextUtils.isEmpty(unit)) {
            etUnit.setError("Unit is required");
            return;
        }

        double quantity = Double.parseDouble(quantityStr);

        if (existingItem != null) {
            // UPDATE EXISTING ITEM
            existingItem.setName(name);
            existingItem.setQuantity(quantity);
            existingItem.setUnit(unit);
            existingItem.setExpiryDate(expiry);

            dbHelper.updatePantryItem(existingItem);
            Toast.makeText(this, "Ingredient updated!", Toast.LENGTH_SHORT).show();
        } else {
            // ADD NEW ITEM
            PantryItem newItem = new PantryItem(name, quantity, unit, expiry);
            dbHelper.addPantryItem(newItem);
            Toast.makeText(this, "Ingredient saved!", Toast.LENGTH_SHORT).show();
        }

        finish(); // Close screen and go back to the list
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}