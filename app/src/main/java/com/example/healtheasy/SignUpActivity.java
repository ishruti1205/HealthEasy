package com.example.healtheasy;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

public class SignUpActivity extends AppCompatActivity {

    EditText usernameInput, emailInput;
    Button signUpButton;
    ImageView googleLogoImg, twitterLogoImg, facebookLogoImg;
    LinearLayout whiteRectangle;

    private TextInputLayout passwordInputLayout, confirmPasswordInputLayout;
    private TextInputEditText passwordInput, confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize views
        usernameInput = findViewById(R.id.usernameInput);
        emailInput = findViewById(R.id.emailInput);

        passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordInput = findViewById(R.id.passwordInput);

        confirmPasswordInputLayout = findViewById(R.id.confirmPasswordInputLayout);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);

        signUpButton = findViewById(R.id.signUpButton);

        whiteRectangle = findViewById(R.id.whiteRectangle);

        // Set rounded corners for the top of whiteRectangle
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
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
                // Toggle password visibility for the password field
                togglePasswordVisibility(passwordInput);
            }
        });

        // Set click listener for the confirm password visibility toggle icon
        confirmPasswordInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle password visibility for the confirm password field
                togglePasswordVisibility(confirmPasswordInput);
            }
        });

        // Set click listener for sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user inputs
                String username = usernameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim().toLowerCase(); // Convert email to lowercase
                String password = passwordInput.getText().toString();
                String confirmPassword = confirmPasswordInput.getText().toString();

                Database db = new Database(getApplicationContext(), "HealthEasy", null, 1);

                // Validate inputs
                if (TextUtils.isEmpty(username)) {
                    usernameInput.setError("Please enter a username");
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    emailInput.setError("Please enter an email address");
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailInput.setError("Please enter a valid email address");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    passwordInput.setError("Please enter a password");
                    return;
                }

                if (password.length() < 6) {
                    passwordInput.setError("Password must be at least 6 characters long");
                    return;
                }

                if (TextUtils.isEmpty(confirmPassword)) {
                    confirmPasswordInput.setError("Please confirm your password");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    confirmPasswordInput.setError("Passwords do not match");
                    return;
                }

                // If all validations pass:
                db.signUp(username, email, password);
                Toast.makeText(getApplicationContext(), "Signed up successfully !", Toast.LENGTH_SHORT).show();

                // Save user data in SharedPreferences

                // Generate SharedPreferences file name based on the username
                String userPrefFileName = "userPrefs_" + username;
                SharedPreferences sharedPreferences = getSharedPreferences(userPrefFileName, MODE_PRIVATE);

                // Save user data to SharedPreferences specific to the current user
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("username", username);
                editor.putString("email", email);
                editor.putString("password", password);
                editor.apply();

                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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

}