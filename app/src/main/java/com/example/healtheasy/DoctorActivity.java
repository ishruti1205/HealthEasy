package com.example.healtheasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        // Retrieve specialization name from intent
        String specializationName = getIntent().getStringExtra("specialization_name");

        // Set specialization heading dynamically
        TextView specializationHeading = findViewById(R.id.specializationHeading);
        specializationHeading.setText("All " + specializationName + "s");

        // Retrieve data from intent
        ArrayList<Doctor> filteredDoctors = (ArrayList<Doctor>) getIntent().getSerializableExtra("filteredDoctors");

        // Initialize RecyclerView for Doctors
        RecyclerView recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors);

        // Use GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerViewDoctors.setLayoutManager(gridLayoutManager);

        // Set up adapter with filtered doctors
        DoctorAdapter doctorAdapter = new DoctorAdapter(filteredDoctors);
        recyclerViewDoctors.setAdapter(doctorAdapter);

        // Back button click listener to return to HomeActivity
        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go back to HomeActivity
                Intent intent = new Intent(DoctorActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close current activity (DoctorActivity)
            }
        });

    }
}
