package com.example.healtheasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MS = 2000; // Splash screen delay time in milliseconds
    private static final String PREF_FIRST_LAUNCH = "first_launch";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE);
        boolean isFirstLaunch = preferences.getBoolean(PREF_FIRST_LAUNCH, true);

        new Handler().postDelayed(() -> {
            if (isFirstLaunch) {
                // First launch, navigate to IntroActivity
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
            } else {
                // Not the first launch, navigate to LoginActivity
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
            finish(); // Finish current activity to prevent going back to it
        }, SPLASH_DELAY_MS);

        // Set the first launch flag to false
        if (isFirstLaunch) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(PREF_FIRST_LAUNCH, false);
            editor.apply();
        }
    }
}
