package com.example.healtheasy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class AccountActivity extends AppCompatActivity {
    private ImageView profileSectionBtn, backBtn, homeTab, favTab, msgTab, notifyTab, accountTab, logoutSection, changePasswordSection, supportTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        ImageView paymentSectionBtn = findViewById(R.id.paymentSectionBtn);
        paymentSectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyPaymentsActivity.class);
            startActivity(intent);
        });

        accountTab = findViewById(R.id.accountTab);
        // Set tint color for accountTab to blueColor
        accountTab.setColorFilter(ContextCompat.getColor(this, R.color.lightBlueColor));

        profileSectionBtn = findViewById(R.id.profileSectionBtn);
        profileSectionBtn.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MyProfileActivity.class);
            startActivity(intent);
        });

        // Logout section click listener
        logoutSection = findViewById(R.id.logoutSectionBtn);
        logoutSection.setOnClickListener(v -> showLogoutConfirmationDialog());

        changePasswordSection = findViewById(R.id.changePasswordBtn);
        changePasswordSection.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, PasswordChangeActivity.class);
            startActivity(intent); // Launch the PasswordChangeActivity
        });

        homeTab = findViewById(R.id.homeTab);
        homeTab.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        favTab = findViewById(R.id.favTab);
        // Retrieve all doctors from intent
        ArrayList<Doctor> allDoctors = (ArrayList<Doctor>) getIntent().getSerializableExtra("allDoctors");

        // Favorites tab click listener
        favTab.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, FavouritesActivity.class);
            intent.putExtra("allDoctors", allDoctors);
            startActivity(intent);
        });

        supportTab = findViewById(R.id.supportSectionBtn);
        supportTab.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, SupportActivity.class);
            startActivity(intent);
        });

        // Back button click listener
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());
    }

    private void showLogoutConfirmationDialog() {
        // Inflate the custom layout
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logout_dialog, null);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Set up the buttons
        Button yesButton = dialogView.findViewById(R.id.yesButton);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);

        yesButton.setOnClickListener(v -> {
            // Close the app
            finishAffinity();
        });

        cancelButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }


}