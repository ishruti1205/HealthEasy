package com.example.healtheasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctors;
    private FavouriteDoctorAdapter doctorAdapter;
    private ArrayList<Doctor> allDoctors;
    private List<Doctor> favouriteDoctors;
    private String currentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Retrieve current logged-in username
        SharedPreferences sharedPreferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        currentUsername = sharedPreferences.getString("username", "");

        // Retrieve all doctors from intent
        allDoctors = (ArrayList<Doctor>) getIntent().getSerializableExtra("allDoctors");

        // Initialize RecyclerView for Doctors
        recyclerViewDoctors = findViewById(R.id.recyclerViewFavDoctors);
        recyclerViewDoctors.setLayoutManager(new GridLayoutManager(this, 2));

        // Retrieve and display favorite doctors
        favouriteDoctors = getFavouriteDoctorsFromStorage();
        doctorAdapter = new FavouriteDoctorAdapter(favouriteDoctors, this, currentUsername);
        recyclerViewDoctors.setAdapter(doctorAdapter);

        // Check if favoriteDoctors list is empty and show appropriate message
        if (favouriteDoctors.isEmpty()) {
            Toast.makeText(this, "No favourite doctors!", Toast.LENGTH_LONG).show();
        }

        // Set up footer buttons
        setupFooterIcons();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh favorite doctors list
        favouriteDoctors.clear();
        favouriteDoctors.addAll(getFavouriteDoctorsFromStorage());
        doctorAdapter.notifyDataSetChanged();

        // Check if favoriteDoctors list is empty and show appropriate message
        if (favouriteDoctors.isEmpty()) {
            Toast.makeText(this, "No favourite doctors!", Toast.LENGTH_LONG).show();
        }
    }

    // Retrieve favorite doctors from storage for the current user
    private List<Doctor> getFavouriteDoctorsFromStorage() {
        ArrayList<Doctor> favouriteDoctors = new ArrayList<>();
        Set<String> favouriteDoctorKeys = FavouriteDoctorManager.getFavouriteDoctorKeys(this, currentUsername);

        for (Doctor doctor : allDoctors) {
            if (favouriteDoctorKeys.contains(FavouriteDoctorManager.generateDoctorKey(doctor))) {
                favouriteDoctors.add(doctor);
            }
        }
        return favouriteDoctors;
    }

    // Set up click listeners for footer buttons
    private void setupFooterIcons() {
        ImageView homeTab = findViewById(R.id.homeTab);
        ImageView favTab = findViewById(R.id.favTab);
        ImageView accountTab = findViewById(R.id.accountTab);
        ImageView backBtn = findViewById(R.id.backBtn);

        // Home tab click listener
        homeTab.setOnClickListener(v -> {
            Intent intent = new Intent(FavouritesActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        // Favorite tab click listener
        favTab.setColorFilter(ContextCompat.getColor(this, R.color.lightBlueColor));
        favTab.setOnClickListener(v -> {
            recyclerViewDoctors.scrollToPosition(0);
        });

        // Account tab click listener
        accountTab.setOnClickListener(v -> {
            Intent intent = new Intent(FavouritesActivity.this, AccountActivity.class);
            intent.putExtra("allDoctors", allDoctors);
            startActivity(intent);
        });

        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());
    }
}

