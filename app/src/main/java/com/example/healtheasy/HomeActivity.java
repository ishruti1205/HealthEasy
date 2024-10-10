package com.example.healtheasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private TextView hiUser;
    private RecyclerView specializationRecyclerView;
    private List<Specialization> specializationList;
    private SpecializationAdapter specializationAdapter;
    private ImageView homeTab, favTab, msgTab, notifyTab, accountTab; // Footer Section

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        hiUser = findViewById(R.id.hiUser);

        // Retrieve username from SharedPreferences
        String username = getUsername();

        // Update greeting message with user's name
        hiUser.setText("Hi, " + username + "!");

        homeTab = findViewById(R.id.homeTab);

        // Set tint color for homeTab to blueColor
        homeTab.setColorFilter(ContextCompat.getColor(this, R.color.lightBlueColor));

        homeTab.setOnClickListener(v -> {
            specializationRecyclerView.scrollToPosition(0);
        });

        favTab = findViewById(R.id.favTab);

        // Retrieve all doctors
        ArrayList<Doctor> allDoctors = getAllDoctorsFromStorage();

        // Favorites tab click listener
        favTab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, FavouritesActivity.class);
            intent.putExtra("allDoctors", allDoctors);
            startActivity(intent);
        });

        accountTab = findViewById(R.id.accountTab);
        // Account tab click listener
        accountTab.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AccountActivity.class);
            intent.putExtra("allDoctors", allDoctors);
            startActivity(intent);
        });

        // Initialize RecyclerView for specializations
        specializationRecyclerView = findViewById(R.id.recyclerViewSpecializations);
        specializationList = new ArrayList<>();
        specializationAdapter = new SpecializationAdapter(specializationList);

        // Set RecyclerView layout manager and adapter
        specializationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        specializationRecyclerView.setAdapter(specializationAdapter);

        // Populate specializationList with data
        variousSpecializations();

        // Handle item click listener for specialization cards
        specializationAdapter.setOnItemClickListener(new SpecializationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Get selected specialization
                Specialization selectedSpecialization = specializationList.get(position);

                // Filter doctors based on the selected specialization
                ArrayList<Doctor> filteredDoctors = filterDoctorsBySpecialization(allDoctors, selectedSpecialization.getName());

                Intent intent = new Intent(HomeActivity.this, DoctorActivity.class);
                intent.putExtra("specialization_name", selectedSpecialization.getName());
                intent.putExtra("filteredDoctors", filteredDoctors);
                startActivity(intent);
            }
        });
    }



    private String getUsername() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
        return preferences.getString("username", ""); // Retrieve username from SharedPreferences, Default value is empty string if not found
    }

    private ArrayList<Doctor> filterDoctorsBySpecialization(ArrayList<Doctor> allDoctors, String specializationName) {
        ArrayList<Doctor> filteredDoctors = new ArrayList<>();
        for (Doctor doctor : allDoctors) {
            if (doctor.getSpecialization().equalsIgnoreCase(specializationName)) {
                filteredDoctors.add(doctor);
            }
        }
        return filteredDoctors;
    }

    private void variousSpecializations() {
        // Add specializations to the list
        specializationList.add(new Specialization("Cardiologist", R.drawable.cardiologist_logo));
        specializationList.add(new Specialization("Dentist", R.drawable.dentist_logo));
        specializationList.add(new Specialization("Dermatologist", R.drawable.dermatologist_logo));
        specializationList.add(new Specialization("Endocrinologist", R.drawable.endocrinologist_logo));
        specializationList.add(new Specialization("Gastroenterologist", R.drawable.gastroenterologist_logo));
        specializationList.add(new Specialization("Gynecologist", R.drawable.gynecologist_logo));
        specializationList.add(new Specialization("Neurologist", R.drawable.neurologist_logo));
        specializationList.add(new Specialization("Ophthalmologist", R.drawable.ophthalmologist_logo));
        specializationList.add(new Specialization("Orthopedic Surgeon", R.drawable.orthopedic_surgeon_logo));
        specializationList.add(new Specialization("Otolaryngologist", R.drawable.otolaryngologist_logo));
        specializationList.add(new Specialization("Pediatrician", R.drawable.pediatrician_logo));
        specializationList.add(new Specialization("Physiotherapist", R.drawable.physiotherapist_logo));
        specializationList.add(new Specialization("Psychiatrist", R.drawable.psychiatrist_logo));
        specializationList.add(new Specialization("Rheumatologist", R.drawable.rheumatologist_logo));
        specializationList.add(new Specialization("Urologist", R.drawable.urologist_logo));

        // Notify specialization adapter that data set has changed
        specializationAdapter.notifyDataSetChanged();
    }

    private ArrayList<Doctor> getAllDoctorsFromStorage() {
        ArrayList<Doctor> allDoctors = new ArrayList<>();

        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Ananya Desai", "Cardiologist",
                "DM Cardiology", "Maharashtra", 1200, "10 years", 4.5,
                "Apollo Hospital", 500.0, "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Anjali Gupta", "Cardiologist",
                "MD Cardiology", "Uttar Pradesh", 910, "8 years", 4.0,
                "Fortis Hospital", 500.0, "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Deepika Singh", "Cardiologist",
                "DM Cardiology", "Karnataka", 2400, "12 years", 4.2,
                "Columbia Asia Hospital", 500.0, "Friday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Karthik Raj", "Cardiologist",
                "DM Cardiology", "Tamil Nadu", 3200, "15 years", 4.8,
                "Global Hospitals", 500.0, "Monday", "Tuesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Kavita Singh", "Cardiologist",
                "MD Cardiology", "Madhya Pradesh", 940, "9 years", 4.3,
                "Medanta Hospital", 500.0, "Wednesday", "Thursday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Meera Kapoor", "Cardiologist",
                "DM Cardiology", "Gujarat", 1205, "11 years", 4.1,
                "Max Healthcare", 500.0, "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Priyanka Sood", "Cardiologist",
                "MD Cardiology", "Rajasthan", 920, "7 years", 3.9,
                "Narayana Health", 500.0, "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Rahul Sharma", "Cardiologist",
                "DM Cardiology", "Delhi", 1940, "13 years", 4.6,
                "Manipal Hospital", 500.0, "Wednesday", "Thursday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Rajesh Patel", "Cardiologist",
                "MD Cardiology", "Bihar", 2900, "14 years", 4.4,
                "Aster Hospitals", 500.0, "Friday", "Saturday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.cardiologist_logo, "Dr. Rohit Verma", "Cardiologist",
                "DM Cardiology", "Punjab", 905, "6 years", 3.8,
                "Vedanta Hospitals", 500.0, "Monday", "Tuesday", "10:00 AM - 12:00 PM"));

        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Ankit Sharma", "Dentist",
                "BDS", "Maharashtra", 950, "8 years", 4.2, "City Dental Clinic", 300.0,
                "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Ashish Verma", "Dentist",
                "MDS (Prosthodontics)", "Uttar Pradesh", 1250, "10 years", 4.5, "Smile Care Centre", 300.0,
                "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Karan Gill", "Dentist",
                "BDS", "Punjab", 950, "5 years", 3.8, "Happy Teeth Clinic", 300.0,
                "Friday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Megha Joshi", "Dentist",
                "MDS (Pedodontics)", "Karnataka", 2010, "12 years", 4.8, "Kinder Dental Care", 300.0,
                "Monday", "Tuesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Nisha Gupta", "Dentist",
                "BDS", "Delhi", 970, "7 years", 4.0, "Perfect Smile Dental Clinic", 300.0,
                "Wednesday", "Thursday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Nisha Reddy", "Dentist",
                "MDS (Orthodontics)", "Telangana", 910, "9 years", 4.3, "OrthoSmile Dental Clinic", 300.0,
                "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Pooja Shah", "Dentist",
                "BDS", "Gujarat", 1290, "11 years", 4.6, "Shah's Dental Studio", 300.0,
                "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Priya Patel", "Dentist",
                "MDS (Endodontics)", "Rajasthan", 950, "8 years", 4.1, "Root Canal Experts", 300.0,
                "Wednesday", "Thursday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Raj Singhania", "Dentist",
                "BDS", "Madhya Pradesh", 980, "6 years", 3.9, "White Pearl Dental Care", 300.0,
                "Friday", "Saturday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dentist_logo, "Dr. Riya Sharma", "Dentist",
                "MDS (Periodontics)", "Haryana", 970, "7 years", 4.4, "Gum Health Clinic", 300.0,
                "Monday", "Tuesday", "10:00 AM - 12:00 PM"));

        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Aditya Hooda", "Dermatologist",
                "MD Dermatology", "Maharashtra", 1200, "10 years", 4.5, "SkinCare Clinic", 350.0,
                "Monday", "Tuesday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Aman Khanna", "Dermatologist",
                "MD Dermatology", "Uttar Pradesh", 920, "8 years", 4.0, "Glow Skin Center", 350.0,
                "Wednesday", "Thursday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Anu Rajput", "Dermatologist",
                "MD Dermatology", "Karnataka", 1480, "12 years", 4.2, "Clear Skin Care", 350.0,
                "Friday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Karan Shah", "Dermatologist",
                "MD Dermatology", "Tamil Nadu", 2150, "15 years", 4.8, "Radiant Skin Clinic", 350.0,
                "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Nisha Sharma", "Dermatologist",
                "MD Dermatology", "Madhya Pradesh", 1190, "9 years", 4.3, "Silky Skin Specialists", 350.0,
                "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Pooja Verma", "Dermatologist",
                "MD Dermatology", "Gujarat", 1100, "11 years", 4.1, "Glowing Faces Dermatology", 350.0,
                "Friday", "Saturday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Priya Thakur", "Dermatologist",
                "MD Dermatology", "Rajasthan", 930, "7 years", 3.9, "DermaCare Solutions", 350.0,
                "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Rahul Sharma", "Dermatologist",
                "MD Dermatology", "Delhi", 1400, "13 years", 4.6, "SkinLux Clinic", 350.0,
                "Wednesday", "Thursday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Rohini Gupta", "Dermatologist",
                "MD Dermatology", "Bihar", 2295, "14 years", 4.4, "Vibrant Skin Center", 350.0,
                "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.dermatologist_logo, "Dr. Sameer Kapoor", "Dermatologist",
                "MD Dermatology", "Punjab", 805, "6 years", 3.8, "DermaGlow Experts", 350.0,
                "Monday", "Tuesday", "2:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Alisha Patel", "Endocrinologist",
                "DM Endocrinology", "Maharashtra", 1050, "10 years", 4.5, "Hormone Health Clinic", 350.0,
                "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Ananya Sharma", "Endocrinologist",
                "DM Endocrinology", "Uttar Pradesh", 1520, "8 years", 4.0, "Metabolic Wellness Center", 350.0,
                "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Karun Verma", "Endocrinologist",
                "DM Endocrinology", "Karnataka", 2380, "12 years", 4.2, "Diabetes & Thyroid Clinic", 350.0,
                "Friday", "Saturday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Meera Desai", "Endocrinologist",
                "DM Endocrinology", "Tamil Nadu", 1150, "15 years", 4.8, "Hormone Balance Clinic", 350.0,
                "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Nisha Gupta", "Endocrinologist",
                "DM Endocrinology", "Madhya Pradesh", 990, "9 years", 4.3, "Endocrine Care Specialists", 350.0,
                "Wednesday", "Thursday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Nisha Reddy", "Endocrinologist",
                "DM Endocrinology", "Gujarat", 1140, "11 years", 4.1, "Thyroid & Diabetes Clinic", 350.0,
                "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Priya Singh", "Endocrinologist",
                "DM Endocrinology", "Rajasthan", 1350, "7 years", 3.9, "Hormone Harmony Center", 350.0,
                "Monday", "Tuesday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Priya Verma", "Endocrinologist",
                "DM Endocrinology", "Delhi", 1420, "13 years", 4.6, "Metabolic Health Specialists", 350.0,
                "Wednesday", "Thursday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Rahul Kapoor", "Endocrinologist",
                "DM Endocrinology", "Bihar", 2195, "14 years", 4.4, "Hormone Expert Clinic", 350.0,
                "Friday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.endocrinologist_logo, "Dr. Sunny Kapoor", "Endocrinologist",
                "DM Endocrinology", "Punjab", 905, "6 years", 3.8, "Thyroid & Hormone Clinic", 350.0,
                "Monday", "Tuesday", "2:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Ajay Kumar", "Gastroenterologist",
                "DM Gastroenterology", "Maharashtra", 1200, "10 years", 4.5, "Gut Health Clinic", 400.0,
                "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Anil Khanna", "Gastroenterologist",
                "MD Gastroenterology", "Uttar Pradesh", 1020, "8 years", 4.0, "Digestive Care Center", 400.0,
                "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Anjali Sharma", "Gastroenterologist",
                "DM Gastroenterology", "Karnataka", 2380, "12 years", 4.2, "Gastro Clinic", 400.0,
                "Friday", "Saturday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Karthik Gupta", "Gastroenterologist",
                "DM Gastroenterology", "Tamil Nadu", 2510, "15 years", 4.8, "Digestive Health Specialists", 400.0,
                "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Neha Sharma", "Gastroenterologist",
                "MD Gastroenterology", "Madhya Pradesh", 1290, "9 years", 4.3, "Gastrointestinal Care Center", 400.0,
                "Wednesday", "Thursday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Nisha Gupta", "Gastroenterologist",
                "DM Gastroenterology", "Gujarat", 1140, "11 years", 4.1, "GI Health Clinic", 400.0,
                "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Preeti Singh", "Gastroenterologist",
                "MD Gastroenterology", "Rajasthan", 1320, "7 years", 3.9, "Digestive Wellness Center", 400.0,
                "Monday", "Tuesday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Priya Desai", "Gastroenterologist",
                "DM Gastroenterology", "Delhi", 1402, "13 years", 4.6, "Gastro Care Clinic", 400.0,
                "Wednesday", "Thursday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Rajiv Mishra", "Gastroenterologist",
                "MD Gastroenterology", "Bihar", 3295, "14 years", 4.4, "Digestive Disorders Clinic", 400.0,
                "Friday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gastroenterologist_logo, "Dr. Rakesh Patel", "Gastroenterologist",
                "DM Gastroenterology", "Punjab", 955, "6 years", 3.8, "GI Tract Health Clinic", 400.0,
                "Monday", "Tuesday", "2:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Anjali Gupta", "Gynecologist",
                "MD Obstetrics and Gynecology", "Maharashtra", 2210, "10 years", 4.5, "Women's Health Clinic", 500.0,
                "Monday", "Tuesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Ananya Singh", "Gynecologist",
                "MD Obstetrics and Gynecology", "Uttar Pradesh", 1160, "8 years", 4.0, "Maternity Care Center", 500.0,
                "Wednesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Komal Sood", "Gynecologist",
                "MD Obstetrics and Gynecology", "Karnataka", 2040, "12 years", 4.2, "Fertility and Wellness Clinic", 500.0,
                "Friday", "Saturday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Maya Verma", "Gynecologist",
                "MD Obstetrics and Gynecology", "Tamil Nadu", 3300, "15 years", 4.8, "Reproductive Health Center", 500.0,
                "Monday", "Tuesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Meera Singh", "Gynecologist",
                "MD Obstetrics and Gynecology", "Madhya Pradesh", 1080, "9 years", 4.3, "Prenatal Care Clinic", 500.0,
                "Wednesday", "Thursday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Neha Kapoor", "Gynecologist",
                "MD Obstetrics and Gynecology", "Gujarat", 2020, "11 years", 4.1, "Women's Wellness Center", 500.0,
                "Friday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Neha Singh", "Gynecologist",
                "MD Obstetrics and Gynecology", "Rajasthan", 940, "7 years", 3.9, "Obstetric Care Clinic", 500.0,
                "Monday", "Tuesday", "1:00 PM - 3:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Nisha Gupta", "Gynecologist",
                "MD Obstetrics and Gynecology", "Delhi", 2260, "13 years", 4.6, "Gynecologic Surgery Center", 500.0,
                "Wednesday", "Thursday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Pooja Verma", "Gynecologist",
                "MD Obstetrics and Gynecology", "Bihar", 2580, "14 years", 4.4, "Women's Reproductive Clinic", 500.0,
                "Friday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.gynecologist_logo, "Dr. Priyanka Rai", "Gynecologist",
                "MD Obstetrics and Gynecology", "Punjab", 1170, "6 years", 3.8, "Menstrual Health Clinic", 500.0,
                "Monday", "Tuesday", "2:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Aisha Khan", "Neurologist",
                "DM Neurology", "Maharashtra", 1180, "10 years", 4.5, "Neuro Care Center", 500.0,
                "Monday", "Wednesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Ananya Rao", "Neurologist",
                "DM Neurology", "Uttar Pradesh", 960, "8 years", 4.0, "Brain and Spine Clinic", 500.0,
                "Tuesday", "Thursday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Arjun Singh", "Neurologist",
                "DM Neurology", "Karnataka", 1240, "12 years", 4.2, "Advanced Neurological Care", 500.0,
                "Monday", "Wednesday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Diya Shah", "Neurologist",
                "DM Neurology", "Tamil Nadu", 2440, "15 years", 4.8, "Neuro Diagnostics Center", 500.0,
                "Tuesday", "Thursday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Kavya Menon", "Neurologist",
                "DM Neurology", "Madhya Pradesh", 1220, "9 years", 4.3, "Comprehensive Neurology Services", 500.0,
                "Wednesday", "Friday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Manish Singh", "Neurologist",
                "DM Neurology", "Gujarat", 1250, "11 years", 4.1, "Neuro Rehab Institute", 500.0,
                "Thursday", "Saturday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Nandini Reddy", "Neurologist",
                "DM Neurology", "Rajasthan", 1190, "7 years", 3.9, "Neurology Solutions Clinic", 500.0,
                "Monday", "Wednesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Pooja Mehta", "Neurologist",
                "DM Neurology", "Delhi", 2280, "13 years", 4.6, "Brain Health Institute", 500.0,
                "Tuesday", "Thursday", "4:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Priya Sharma", "Neurologist",
                "DM Neurology", "Bihar", 2210, "14 years", 4.4, "Neurological Disorders Center", 500.0,
                "Wednesday", "Friday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.neurologist_logo, "Dr. Rahul Kapoor", "Neurologist",
                "DM Neurology", "Punjab", 970, "6 years", 3.8, "Cognitive Neurology Clinic", 500.0,
                "Thursday", "Saturday", "1:00 PM - 3:00 PM"));

        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Anand Khanna", "Ophthalmologist",
                "MS Ophthalmology", "Maharashtra", 1200, "10 years", 4.5, "Vision Care Clinic", 300.0,
                "Monday", "Wednesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Ananya Desai", "Ophthalmologist",
                "MS Ophthalmology", "Uttar Pradesh", 940, "8 years", 4.0, "Eye Health Center", 300.0,
                "Tuesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Anika Bhatia", "Ophthalmologist",
                "MS Ophthalmology", "Karnataka", 1160, "12 years", 4.2, "Optical Solutions Clinic", 300.0,
                "Wednesday", "Friday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Aryan Mehra", "Ophthalmologist",
                "MS Ophthalmology", "Tamil Nadu", 2300, "15 years", 4.8, "Clear Vision Hospital", 300.0,
                "Thursday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Kavya Verma", "Ophthalmologist",
                "MS Ophthalmology", "Madhya Pradesh", 980, "9 years", 4.3, "Sight Care Institute", 300.0,
                "Monday", "Wednesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Kartik Joshi", "Ophthalmologist",
                "MS Ophthalmology", "Gujarat", 1220, "11 years", 4.1, "Vision Plus Center", 300.0,
                "Tuesday", "Thursday", "4:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Maya Kapoor", "Ophthalmologist",
                "MS Ophthalmology", "Rajasthan", 1260, "7 years", 3.9, "Eyesight Clinic", 300.0,
                "Wednesday", "Friday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Naina Reddy", "Ophthalmologist",
                "MS Ophthalmology", "Delhi", 1280, "13 years", 4.6, "ClearSight Hospital", 300.0,
                "Thursday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Natasha Goel", "Ophthalmologist",
                "MS Ophthalmology", "Bihar", 2190, "14 years", 4.4, "Vision Hub", 300.0,
                "Monday", "Wednesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.ophthalmologist_logo, "Dr. Priya Sharma", "Ophthalmologist",
                "MS Ophthalmology", "Punjab", 910, "6 years", 3.8, "Eye Wellness Center", 300.0,
                "Tuesday", "Thursday", "1:00 PM - 3:00 PM"));

        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Amit Singh", "Orthopedic Surgeon",
                "MS Orthopedics", "Maharashtra", 1205, "10 years", 4.5, "Bone Health Clinic", 400.0,
                "Monday", "Wednesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Anil Reddy", "Orthopedic Surgeon",
                "MS Orthopedics", "Uttar Pradesh", 940, "8 years", 4.0, "Joint Care Hospital", 400.0,
                "Tuesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Arjun Sharma", "Orthopedic Surgeon",
                "MS Orthopedics", "Karnataka", 1160, "12 years", 4.2, "Spine Center", 400.0,
                "Wednesday", "Friday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Manisha Rao", "Orthopedic Surgeon",
                "MS Orthopedics", "Tamil Nadu", 2300, "15 years", 4.8, "OrthoCare Clinic", 400.0,
                "Thursday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Neha Gupta", "Orthopedic Surgeon",
                "MS Orthopedics", "Madhya Pradesh", 980, "9 years", 4.3, "Limb Health Institute", 400.0,
                "Monday", "Wednesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Nisha Patel", "Orthopedic Surgeon",
                "MS Orthopedics", "Gujarat", 1220, "11 years", 4.1, "Joint Wellness Center", 400.0,
                "Tuesday", "Thursday", "4:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Pooja Desai", "Orthopedic Surgeon",
                "MS Orthopedics", "Rajasthan", 960, "7 years", 3.9, "OrthoPlus Hospital", 400.0,
                "Wednesday", "Friday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Rajesh Kumar", "Orthopedic Surgeon",
                "MS Orthopedics", "Delhi", 1680, "13 years", 4.6, "Bone Cure Clinic", 400.0,
                "Thursday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Ravi Kumar", "Orthopedic Surgeon",
                "MS Orthopedics", "Bihar", 2190, "14 years", 4.4, "OrthoMed Hospital", 400.0,
                "Monday", "Wednesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.orthopedic_surgeon_logo, "Dr. Sahil Mishra", "Orthopedic Surgeon",
                "MS Orthopedics", "Punjab", 910, "6 years", 3.8, "Joint Relief Clinic", 400.0,
                "Tuesday", "Thursday", "1:00 PM - 3:00 PM"));

        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Aditya Reddy", "Otolaryngologist",
                "MS ENT", "Maharashtra", 1150, "10 years", 4.5, "Ear Nose Throat Clinic", 350.0,
                "Monday", "Wednesday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Alisha Sharma", "Otolaryngologist",
                "MS ENT", "Uttar Pradesh", 980, "8 years", 4.0, "ENT Care Hospital", 350.0,
                "Tuesday", "Thursday", "10:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Anuj Mehta", "Otolaryngologist",
                "MS ENT", "Karnataka", 1120, "12 years", 4.2, "ENT Wellness Center", 350.0,
                "Wednesday", "Friday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Arjun Goel", "Otolaryngologist",
                "MS ENT", "Tamil Nadu", 2200, "15 years", 4.8, "Ear & Throat Specialist Clinic", 350.0,
                "Thursday", "Saturday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Kavita Rao", "Otolaryngologist",
                "MS ENT", "Madhya Pradesh", 930, "9 years", 4.3, "ENT Solutions Center", 350.0,
                "Monday", "Wednesday", "2:00 PM - 4:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Meera Desai", "Otolaryngologist",
                "MS ENT", "Gujarat", 1160, "11 years", 4.1, "Ear & Throat Care Clinic", 350.0,
                "Tuesday", "Thursday", "4:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Neha Mishra", "Otolaryngologist",
                "MS ENT", "Rajasthan", 990, "7 years", 3.9, "ENT Health Clinic", 350.0,
                "Wednesday", "Friday", "9:00 AM - 11:00 AM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Nisha Patel", "Otolaryngologist",
                "MS ENT", "Delhi", 2510, "13 years", 4.6, "ENT Specialists Hospital", 350.0,
                "Thursday", "Saturday", "11:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Rahul Kumar", "Otolaryngologist",
                "MS ENT", "Bihar", 2140, "14 years", 4.4, "Advanced ENT Care Center", 350.0,
                "Monday", "Wednesday", "3:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.otolaryngologist_logo, "Dr. Rohan Gupta", "Otolaryngologist",
                "MS ENT", "Punjab", 960, "6 years", 3.8, "ENT Care Associates", 350.0,
                "Tuesday", "Thursday", "1:00 PM - 3:00 PM"));

        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Aanya Sharma", "Pediatrician",
                "MD Pediatrics", "Maharashtra", 1120, "10 years", 4.5, "Child Care Hospital", 300.0,
                "Monday", "Wednesday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Aarav Gupta", "Pediatrician",
                "MD Pediatrics", "Uttar Pradesh", 950, "8 years", 4.0, "Children's Clinic", 300.0,
                "Tuesday", "Thursday", "10:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Advik Sharma", "Pediatrician",
                "MD Pediatrics", "Karnataka", 1200, "12 years", 4.2, "Kids Wellness Center", 300.0,
                "Wednesday", "Friday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Arnav Sood", "Pediatrician",
                "MD Pediatrics", "Tamil Nadu", 2800, "15 years", 4.8, "Pediatric Care Specialists", 300.0,
                "Thursday", "Saturday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Arjun Mehta", "Pediatrician",
                "MD Pediatrics", "Madhya Pradesh", 930, "9 years", 4.3, "Children's Health Center", 300.0,
                "Monday", "Wednesday", "2:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Diya Verma", "Pediatrician",
                "MD Pediatrics", "Gujarat", 1160, "11 years", 4.1, "Pediatric Associates", 300.0,
                "Tuesday", "Thursday", "4:00 PM - 7:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Kabir Goel", "Pediatrician",
                "MD Pediatrics", "Rajasthan", 940, "7 years", 3.9, "Kids Clinic", 300.0,
                "Wednesday", "Friday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Krish Patel", "Pediatrician",
                "MD Pediatrics", "Delhi", 1170, "13 years", 4.6, "Pediatric Wellness Center", 300.0,
                "Thursday", "Saturday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Neha Patel", "Pediatrician",
                "MD Pediatrics", "Bihar", 2210, "14 years", 4.4, "Child Health Specialists", 300.0,
                "Monday", "Wednesday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.pediatrician_logo, "Dr. Riya Sharma", "Pediatrician",
                "MD Pediatrics", "Punjab", 920, "6 years", 3.8, "Pediatric Care Clinic", 300.0,
                "Tuesday", "Thursday", "1:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Aditya Kumar", "Physiotherapist",
                "MPT Physiotherapy", "Maharashtra", 1120, "10 years", 4.5, "Rehabilitation Center", 350.0,
                "Monday", "Wednesday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Akash Jain", "Physiotherapist",
                "MPT Physiotherapy", "Uttar Pradesh", 950, "8 years", 4.0, "Physical Therapy Clinic", 350.0,
                "Tuesday", "Thursday", "10:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Ankit Verma", "Physiotherapist",
                "MPT Physiotherapy", "Karnataka", 1100, "12 years", 4.2, "Sports Injury Clinic", 350.0,
                "Wednesday", "Friday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Natasha Reddy", "Physiotherapist",
                "MPT Physiotherapy", "Tamil Nadu", 2180, "15 years", 4.8, "Orthopedic Rehabilitation Center", 350.0,
                "Thursday", "Saturday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Nidhi Gupta", "Physiotherapist",
                "MPT Physiotherapy", "Madhya Pradesh", 930, "9 years", 4.3, "Neurorehabilitation Clinic", 350.0,
                "Monday", "Wednesday", "2:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Pooja Singh", "Physiotherapist",
                "MPT Physiotherapy", "Gujarat", 1160, "11 years", 4.1, "Home Health Care", 350.0,
                "Tuesday", "Thursday", "4:00 PM - 7:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Priya Sharma", "Physiotherapist",
                "MPT Physiotherapy", "Rajasthan", 940, "7 years", 3.9, "Geriatric Physiotherapy Clinic", 350.0,
                "Wednesday", "Friday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Rahul Singh", "Physiotherapist",
                "MPT Physiotherapy", "Delhi", 2470, "13 years", 4.6, "Pediatric Rehabilitation Center", 350.0,
                "Thursday", "Saturday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Rajesh Gupta", "Physiotherapist",
                "MPT Physiotherapy", "Bihar", 2610, "14 years", 4.4, "Musculoskeletal Physiotherapy Clinic", 350.0,
                "Monday", "Wednesday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.physiotherapist_logo, "Dr. Rohit Khanna", "Physiotherapist",
                "MPT Physiotherapy", "Punjab", 920, "6 years", 3.8, "Spine Rehabilitation Clinic", 350.0,
                "Tuesday", "Thursday", "1:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Aditya Gupta", "Psychiatrist",
                "MD Psychiatry", "Maharashtra", 1140, "10 years", 4.5, "Mental Health Clinic", 450.0,
                "Monday", "Wednesday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Ananya Singh", "Psychiatrist",
                "MD Psychiatry", "Uttar Pradesh", 920, "8 years", 4.0, "Behavioral Health Center", 450.0,
                "Tuesday", "Thursday", "10:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Arjun Gill", "Psychiatrist",
                "MD Psychiatry", "Karnataka", 1680, "12 years", 4.2, "Psychiatric Hospital", 450.0,
                "Wednesday", "Friday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Kabir Sharma", "Psychiatrist",
                "MD Psychiatry", "Tamil Nadu", 2650, "15 years", 4.8, "Mental Wellness Institute", 450.0,
                "Thursday", "Saturday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Karan Patel", "Psychiatrist",
                "MD Psychiatry", "Madhya Pradesh", 910, "9 years", 4.3, "Psychological Services Center", 450.0,
                "Monday", "Wednesday", "2:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Natasha Sood", "Psychiatrist",
                "MD Psychiatry", "Gujarat", 2110, "11 years", 4.1, "Mind and Mood Clinic", 450.0,
                "Tuesday", "Thursday", "4:00 PM - 7:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Naina Sharma", "Psychiatrist",
                "MD Psychiatry", "Rajasthan", 1030, "7 years", 3.9, "Emotional Wellness Center", 450.0,
                "Wednesday", "Friday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Priya Gupta", "Psychiatrist",
                "MD Psychiatry", "Delhi", 2140, "13 years", 4.6, "Cognitive Behavioral Clinic", 450.0,
                "Thursday", "Saturday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Priya Khanna", "Psychiatrist",
                "MD Psychiatry", "Bihar", 2905, "14 years", 4.4, "Psychiatric Consultation Center", 450.0,
                "Monday", "Wednesday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.psychiatrist_logo, "Dr. Rahul Mehta", "Psychiatrist",
                "MD Psychiatry", "Punjab", 905, "6 years", 3.8, "Psychiatric Care Unit", 450.0,
                "Tuesday", "Thursday", "1:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Aditya Patel", "Rheumatologist",
                "MD Rheumatology", "Maharashtra", 1080, "10 years", 4.6, "Rheumatology Clinic", 400.0,
                "Monday", "Wednesday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Ananya Sharma", "Rheumatologist",
                "MD Rheumatology", "Uttar Pradesh", 990, "8 years", 4.3, "Joint Health Center", 400.0,
                "Tuesday", "Thursday", "10:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Ishita Desai", "Rheumatologist",
                "MD Rheumatology", "Gujarat", 1310, "12 years", 4.8, "Arthritis and Rheumatism Center", 400.0,
                "Wednesday", "Friday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Kabir Sharma", "Rheumatologist",
                "MD Rheumatology", "Tamil Nadu", 2110, "15 years", 4.9, "Rheumatic Disorders Clinic", 400.0,
                "Thursday", "Saturday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Neha Gupta", "Rheumatologist",
                "MD Rheumatology", "Rajasthan", 920, "9 years", 4.2, "Bone and Joint Care Center", 400.0,
                "Monday", "Wednesday", "2:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Priya Mehta", "Rheumatologist",
                "MD Rheumatology", "Delhi", 1130, "11 years", 4.5, "Rheumatic Disease Specialist", 400.0,
                "Tuesday", "Thursday", "4:00 PM - 7:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Rajesh Kalra", "Rheumatologist",
                "MD Rheumatology", "Punjab", 1340, "13 years", 4.7, "Connective Tissue Disorders Clinic", 400.0,
                "Wednesday", "Friday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Rahul Verma", "Rheumatologist",
                "MD Rheumatology", "Haryana", 2150, "14 years", 4.4, "Musculoskeletal Health Center", 400.0,
                "Thursday", "Saturday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Rishi Verma", "Rheumatologist",
                "MD Rheumatology", "Karnataka", 960, "7 years", 4.0, "Rheumatoid Arthritis Clinic", 400.0,
                "Monday", "Wednesday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.rheumatologist_logo, "Dr. Rohan Sood", "Rheumatologist",
                "MD Rheumatology", "Madhya Pradesh", 3170, "16 years", 4.9, "Joint Inflammation Center", 400.0,
                "Tuesday", "Thursday", "1:00 PM - 4:00 PM"));

        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Aarti Sharma", "Urologist",
                "MD Urology", "Delhi", 1180, "12 years", 4.7, "Urology Clinic", 450.0,
                "Monday", "Wednesday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Arjun Kapoor", "Urologist",
                "MS Urology", "Mumbai", 1190, "10 years", 4.5, "Advanced Urological Care", 420.0,
                "Tuesday", "Thursday", "10:00 AM - 1:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Maya Reddy", "Urologist",
                "MD Urology", "Hyderabad", 1100, "8 years", 4.3, "Kidney Stone Center", 400.0,
                "Wednesday", "Friday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Nandini Patel", "Urologist",
                "MS Urology", "Ahmedabad", 2810, "15 years", 4.9, "Prostate Health Clinic", 480.0,
                "Thursday", "Saturday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Neha Goel", "Urologist",
                "MD Urology", "Chennai", 920, "9 years", 4.2, "Bladder Health Center", 400.0,
                "Monday", "Wednesday", "2:00 PM - 5:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Rajesh Gill", "Urologist",
                "MS Urology", "Pune", 1130, "11 years", 4.6, "Male Reproductive Health Clinic", 430.0,
                "Tuesday", "Thursday", "4:00 PM - 7:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Rohit Singh", "Urologist",
                "MD Urology", "Bangalore", 2340, "14 years", 4.8, "Renal Care Center", 470.0,
                "Wednesday", "Friday", "9:00 AM - 12:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Sanjay Gupta", "Urologist",
                "MS Urology", "Kolkata", 2250, "13 years", 4.4, "Urinary Tract Clinic", 450.0,
                "Thursday", "Saturday", "11:00 AM - 2:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Sameer Joshi", "Urologist",
                "MD Urology", "Jaipur", 960, "7 years", 4.0, "Urological Surgery Center", 380.0,
                "Monday", "Wednesday", "3:00 PM - 6:00 PM"));
        allDoctors.add(new Doctor(R.drawable.urologist_logo, "Dr. Sneha Rao", "Urologist",
                "MS Urology", "Chandigarh", 3170, "16 years", 4.9, "Female Urology Clinic", 490.0,
                "Tuesday", "Thursday", "1:00 PM - 4:00 PM"));

        return allDoctors;
    }
}

