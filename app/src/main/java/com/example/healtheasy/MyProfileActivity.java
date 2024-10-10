package com.example.healtheasy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MyProfileActivity extends AppCompatActivity {

    private TextView usernameTextView, emailTextView;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordTextView;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        // Initialize views
        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);
        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordTextView = findViewById(R.id.passwordTextView);

        // Retrieve user data from SharedPreferences specific to the logged-in user
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs_" + getUsername(), MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "N/A");
        String email = sharedPreferences.getString("email", "N/A");
        String password = sharedPreferences.getString("password", "N/A");

        // Set user data to TextViews
        usernameTextView.setText(username);
        emailTextView.setText(email);
        passwordTextView.setText(password);

        // Set click listener for the password visibility toggle icon
        passwordInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle password visibility for the password field
                togglePasswordVisibility(passwordTextView);
            }
        });

        // Back button click listener
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void togglePasswordVisibility(TextInputEditText editText) {
        if (editText.getTransformationMethod() instanceof PasswordTransformationMethod) {
            // Password is currently hidden, show it
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // Password is currently shown, hide it
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        editText.setSelection(editText.getText().length()); // Keep cursor at the end
    }

    private String getUsername() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        return preferences.getString("username", ""); // Retrieve username from SharedPreferences, Default value is empty string if not found
    }
}
