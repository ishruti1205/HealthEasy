package com.example.healtheasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MyProfileActivity extends AppCompatActivity {

    private TextView usernameTextView, emailTextView;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordTextView;
    private ImageView backBtn;
    private Button updateProfileButton;

    private static final String TAG = "MyProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Initialize views
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordTextView = findViewById(R.id.passwordTextView);
        backBtn = findViewById(R.id.backBtn);


        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());



        // Load profile data
        reloadProfileData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadProfileData(); // Refresh profile data when the activity resumes
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            reloadProfileData(); // Reload the latest data
        }
    }

    private void reloadProfileData() {
        Log.d(TAG, "reloadProfileData called");

        // Fetch the latest username from global SharedPreferences
        SharedPreferences globalPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String currentUsername = globalPrefs.getString("username", "N/A");

        // Fetch user-specific data using the latest username
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs_" + currentUsername, MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String updatedPassword = sharedPreferences.getString("password", "N/A");

        // Log the fetched data
        Log.d(TAG, "reloadProfileData: Username: " + username);
        Log.d(TAG, "reloadProfileData: Email: " + email);
        Log.d(TAG, "reloadProfileData: Password: " + updatedPassword);

        // Update UI with the latest data
        usernameTextView.setText(username);
        emailTextView.setText(email);
        passwordTextView.setText(updatedPassword);
    }
}