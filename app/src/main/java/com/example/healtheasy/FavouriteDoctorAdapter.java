package com.example.healtheasy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavouriteDoctorAdapter extends RecyclerView.Adapter<FavouriteDoctorAdapter.DoctorViewHolder> {

    private List<Doctor> doctorList; // List of favorite doctors
    private Context context; // Context reference for inflating views
    private String currentUsername; // Current logged-in username

    // Constructor to initialize the adapter with data and context
    public FavouriteDoctorAdapter(List<Doctor> doctorList, Context context, String currentUsername) {
        this.doctorList = doctorList;
        this.context = context;
        this.currentUsername = currentUsername;
    }

    // ViewHolder class to hold the view elements for each item
    public static class DoctorViewHolder extends RecyclerView.ViewHolder {
        public ImageView doctorImg;
        public TextView doctorName;
        public Button viewProfileBtn;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImg = itemView.findViewById(R.id.doctorImg);
            doctorName = itemView.findViewById(R.id.doctorName);
            viewProfileBtn = itemView.findViewById(R.id.viewProfileBtn);
        }
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new DoctorViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.doctorImg.setImageResource(doctor.getImageResource());
        holder.doctorName.setText(doctor.getName());

        // Set click listener for view profile button
        holder.viewProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle appointment button click
                Doctor selectedDoctor = doctorList.get(holder.getAdapterPosition());

                // Create an Intent to open DoctorDetailsActivity
                Intent intent = new Intent(v.getContext(), DoctorDetailsActivity.class);

                // Pass the selected Doctor object to DoctorDetailsActivity
                intent.putExtra("doctor", selectedDoctor);

                // Start DoctorDetailsActivity
//                v.getContext().startActivity(intent);

                ((Activity) context).startActivityForResult(intent, 1);
            }
        });
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    // Method to update the dataset when the list of doctors changes
    public void updateDoctors(List<Doctor> updatedDoctorList) {
        doctorList = updatedDoctorList;
        notifyDataSetChanged(); // Notify RecyclerView that the dataset has changed
    }
}

