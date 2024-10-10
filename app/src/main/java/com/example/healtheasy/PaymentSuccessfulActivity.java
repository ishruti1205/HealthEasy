package com.example.healtheasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class PaymentSuccessfulActivity extends AppCompatActivity {
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Set the theme before calling super.onCreate
        setTheme(R.style.DialogTheme);
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_payment_successful);

        // Make the dialog fill the screen
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        // Set the background dim color
        getWindow().setBackgroundDrawableResource(R.drawable.dim_background);

        backBtn = findViewById(R.id.backBtn);

        // Back Button click listener
        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentSuccessfulActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        // Do nothing to disable back button
    }
}
