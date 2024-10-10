package com.example.healtheasy;

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

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private List<Doctor> doctorList;

    public DoctorAdapter(List<Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
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
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView doctorImg;
        TextView doctorName;
        Button viewProfileBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            doctorImg = itemView.findViewById(R.id.doctorImg);
            doctorName = itemView.findViewById(R.id.doctorName);
            viewProfileBtn = itemView.findViewById(R.id.viewProfileBtn);
        }
    }

    // Method to get Doctor object at a specific position
    public Doctor getDoctorAtPosition(int position) {
        if (position < doctorList.size()) {
            return doctorList.get(position);
        }
        return null;
    }
}
