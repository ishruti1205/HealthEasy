package com.example.healtheasy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PasswordChangeActivity extends AppCompatActivity {
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        EditText oldPasswordInput = findViewById(R.id.oldPasswordInput);
        EditText newPasswordInput = findViewById(R.id.newPasswordInput);
        EditText confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        Button changePasswordButton = findViewById(R.id.changePasswordButton);

        // Back button click listener
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        // Database instance
        Database db = new Database(this, "HealthEasy", null, 1);

        // Set OnClickListener for the change password button
        changePasswordButton.setOnClickListener(v -> {
            String oldPassword = oldPasswordInput.getText().toString().trim();
            String newPassword = newPasswordInput.getText().toString().trim();
            String confirmPassword = confirmPasswordInput.getText().toString().trim();

            // Input validation
            if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "New passwords do not match.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Fetch logged-in username from SharedPreferences
            SharedPreferences preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
            String username = preferences.getString("username", ""); // Retrieve username

            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "User is not logged in. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.d("PasswordChangeActivity", "Attempting to change password for user: " + username);


            // Attempt to change password
            boolean result = db.changePassword(username, oldPassword, newPassword);

            if (result) {
                // Update SharedPreferences with the new password
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("password", newPassword);
                editor.apply(); // Save the new password

                Log.d("PasswordChangeActivity", "Password updated successfully in SharedPreferences: " + newPassword);


                Toast.makeText(this, "Password changed successfully!", Toast.LENGTH_SHORT).show();

                // Redirect to MyProfileActivity
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close the current activity
            } else {
                Toast.makeText(this, "Incorrect old password.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}