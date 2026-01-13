package com.example.healtheasy;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.text.TextUtils;
import android.widget.Toast;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textfield.TextInputEditText;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LoginActivity extends AppCompatActivity{

    EditText usernameInput;
    Button loginButton, newAccButton;
    ImageView googleLogoImg, twitterLogoImg, facebookLogoImg;
    LinearLayout whiteRectangle;
    private TextInputLayout passwordInputLayout;
    private TextInputEditText passwordInput;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);

        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordInput = findViewById(R.id.passwordInput);

        loginButton = findViewById(R.id.loginButton);
        newAccButton = findViewById(R.id.newAccButton);

        whiteRectangle = findViewById(R.id.whiteRectangle);

        // Set rounded corners for the top of whiteRectangle
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadii(new float[] {
                convertDpToPx(40), convertDpToPx(40), // Top-left corner radius in px
                convertDpToPx(40), convertDpToPx(40), // Top-right corner radius in px
                convertDpToPx(0), convertDpToPx(0), // Bottom corners set to 0 for no rounding
                convertDpToPx(0), convertDpToPx(0)
        });

        // Set background color and drawable for whiteRectangle
        whiteRectangle.setBackground(gradientDrawable);

        // Set click listener for the password visibility toggle icon
        passwordInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle password visibility
                togglePasswordVisibility(passwordInput);
            }
        });

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String username = usernameInput.getText().toString().trim();
                String password = passwordInput.getText().toString();

                Database db = new Database(getApplicationContext(), "HealthEasy", null, 1);

                // Validate inputs
                if (TextUtils.isEmpty(username)) {
                    usernameInput.setError("Please enter a username");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordInput.setError("Please enter a password");
                    return;
                }

                // If all validations pass:

                // Attempt login
                boolean loginSuccessful = db.login(username, password);

                if (loginSuccessful) {
                    Toast.makeText(LoginActivity.this, "Logged in successfully!", Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("username", username);
                    editor.apply();

                    String lastLoginTime = db.getLastLoginTime(username);
                    if (lastLoginTime != null) {
                        long loginAtMillis = Long.parseLong(lastLoginTime); // Convert timestamp string to milliseconds
                        String formattedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                .format(new Date(loginAtMillis)); // Format the timestamp
                        Log.d("LoginActivity", "User " + username + " logged in at: " + formattedTime);
                        Toast.makeText(LoginActivity.this, "Last login: " + formattedTime, Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("LoginActivity", "User " + username + " logged in for the first time.");
                        Toast.makeText(LoginActivity.this, "Welcome! This is your first login.", Toast.LENGTH_LONG).show();
                    }

                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }

                else {
                    Toast.makeText(LoginActivity.this, "Login failed ! Invalid username or password.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Set click listener for new account button
        newAccButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });
    }

    private int convertDpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
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
        return preferences.getString("username", ""); // Default value is empty string if not found
    }

}
