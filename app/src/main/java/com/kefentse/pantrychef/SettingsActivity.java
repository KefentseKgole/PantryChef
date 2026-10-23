package com.kefentse.pantrychef;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Switch switchExpiryAlerts;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Settings");
        }

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);

        // Initialize SharedPreferences to save the toggle state
        sharedPreferences = getSharedPreferences("PantryChefPrefs", MODE_PRIVATE);

        // Load the saved state (default is true)
        boolean isAlertsEnabled = sharedPreferences.getBoolean("expiry_alerts", true);
        switchExpiryAlerts.setChecked(isAlertsEnabled);

        // Save the state whenever the user flips the switch
        switchExpiryAlerts.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("expiry_alerts", isChecked);
            editor.apply();
        });
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