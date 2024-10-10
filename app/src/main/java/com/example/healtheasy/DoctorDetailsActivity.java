package com.example.healtheasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class DoctorDetailsActivity extends AppCompatActivity {
    private boolean isFavourite = false; // Declare isFavourite variable
    private ImageView favIcon;
    private Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        // Initialize views
        ImageView backBtn = findViewById(R.id.backBtn);
        ImageView doctorImg = findViewById(R.id.doctorImg);
        TextView doctorName = findViewById(R.id.doctorName);
        TextView doctorSpecialization = findViewById(R.id.doctorSpecialization);
        TextView doctorLocation = findViewById(R.id.doctorLocation);
        TextView patients = findViewById(R.id.patientsDetails);
        TextView experience = findViewById(R.id.experienceDetails);
        TextView rating = findViewById(R.id.ratingDetails);
        TextView aboutDetails = findViewById(R.id.aboutDetails);
        TextView availabilityDetails = findViewById(R.id.availabilityDetails);
        TextView consultationFeeDetails = findViewById(R.id.consultationFeeDetails);

        favIcon = findViewById(R.id.favIcon);
        Button bookBtn = findViewById(R.id.bookBtn);

        // Retrieve the Doctor object from the intent extras
        doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        // Set doctor details in UI
        if (doctor != null) {
            doctorName.setText(doctor.getName());
            doctorImg.setImageResource(doctor.getImageResource());
            doctorSpecialization.setText(doctor.getSpecialization());
            doctorLocation.setText(doctor.getLocation());
            patients.setText(String.valueOf(doctor.getPatients()));
            experience.setText(doctor.getExperience());
            rating.setText(String.valueOf(doctor.getRating()));
            aboutDetails.setText(generateAboutDetails(doctor));
            availabilityDetails.setText(generateAvailabilityDetails(doctor));
            consultationFeeDetails.setText(generateConsultationFeeDetails(doctor));

            // Check if the doctor is in favorites for the current user
            isFavourite = isDoctorInFavorites();
            updateFavoriteIcon(); // Update the favorite icon based on the current state
        }

        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());

        // Favorite icon click listener
        favIcon.setOnClickListener(v -> {
            // Toggle favorite state
            isFavourite = !isFavourite;
            updateFavoriteIcon(); // Update the favorite icon based on the new state

            // Save or remove the doctor from favorites based on the new state
            if (isFavourite) {
                addToFavorites();
            } else {
                removeFromFavorites();
            }
        });

        // Book Appointment button click listener
        bookBtn.setOnClickListener(v -> {
            // Open AppointmentSelectionActivity
            Intent intent = new Intent(DoctorDetailsActivity.this, AppointmentSelectionActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
        });
    }

    private String generateAboutDetails(Doctor doctor) {
        return doctor.getName() + " has a degree in '" + doctor.getDegree() +
                "' and is associated with '" + doctor.getHospital() + "'.";
    }

    private String generateAvailabilityDetails(Doctor doctor) {
        return doctor.getDay1() + ", " + doctor.getDay2() + ": " + doctor.getTimeSlot();
    }

    private String generateConsultationFeeDetails(Doctor doctor) {
        return "Rs. " + doctor.getFee() + "/- (inc. of all taxes)";
    }

    // Method to update the favorite icon based on the current state
    private void updateFavoriteIcon() {
        // Resolve the color resource based on the favorite state
        int colorRes = isFavourite ? R.color.blueColor : R.color.lighterBlackColor;

        // Get the resolved color value from resources
        int color = ContextCompat.getColor(this, colorRes);

        // Apply color filter to the icon
        favIcon.setColorFilter(color);
    }

    // Method to check if the doctor is in favorites for the current user
    private boolean isDoctorInFavorites() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        return FavouriteDoctorManager.isFavourite(this, username, doctor);
    }

    // Method to add the doctor to favorites for the current user
    private void addToFavorites() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        FavouriteDoctorManager.addToFavourites(this, username, doctor);
    }

    // Method to remove the doctor from favorites for the current user
    private void removeFromFavorites() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String username = preferences.getString("username", "");

        FavouriteDoctorManager.removeFromFavourites(this, username, doctor);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
