package com.vagabon.digital.clock.com.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.vagabon.digital.clock.com.R;
import com.vagabon.digital.clock.com.ReusableCode.Settings;
import com.vagabon.digital.clock.com.databinding.ActivityCustomiseBinding;

public class CustomiseActivity extends AppCompatActivity {
    private ActivityCustomiseBinding binding;
    private Settings settings;
    private int p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = new Settings();
        setCustomTheme();

        super.onCreate(savedInstanceState);
        binding = ActivityCustomiseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding.saveButtonId.setBackgroundColor(Color.TRANSPARENT);
        setSpinnerItem();
        onClick();

    }

    private void setCustomTheme() {
        Log.d("checkTheme", "setCustomTheme: " + checkTheme() + "");
        if (checkTheme() == 0) {
            setTheme(R.style.customMode);
        } else if (checkTheme() == 1) {
            setTheme(R.style.NightBat);
        } else if (checkTheme() == 2) {
            setTheme(R.style.orangeMode);
        } else if (checkTheme() == 3) {
            setTheme(R.style.BlackDay);
        }
    }

    private int checkTheme() {
        return settings.get_theme(getApplicationContext(), "theme", "theme");
    }

    private void setSpinnerItem() {
        String[] items = new String[]{"Classic Theme", "Light Leaf", "Night Pumpkin", "Black Day"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        binding.customThemeDialogId.setAdapter(adapter);
        binding.customThemeDialogId.setSelection(settings.get_theme(getApplicationContext(), "theme", "theme"));
    }

    private void onClick() {

        /*
         * If you click this button (back button)
         * you will redirect to previous page
         */
        binding.backBtnId.setOnClickListener(v -> finish());

        /*
         * This button is used to get position of the spinner
         * and this button is used to set theme
         *
         */


        binding.customThemeDialogId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item position
                Log.d("Position", "onItemSelected: " + position + "");
                p = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        binding.saveButtonId.setOnClickListener(v -> {
            settings.set_theme(getApplicationContext(), "theme", "theme", p);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
            finish();
        });


    }
}