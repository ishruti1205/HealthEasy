package com.example.healtheasy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder> {

    private List<PaymentDetail> paymentDetails;

    // Constructor to initialize the dataset
    public PaymentsAdapter(List<PaymentDetail> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payment_item, parent, false);
        return new PaymentViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder holder, int position) {
        PaymentDetail detail = paymentDetails.get(position);
        holder.specializationTextView.setText("Specialization: " + detail.getSpecialization());
        holder.dateTextView.setText("Date: " + detail.getDate());
        holder.amountTextView.setText("Amount: Rs. " + detail.getAmount());
    }

    // Return the size of the dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return paymentDetails.size();
    }

    // ViewHolder class to hold references to the views in each list item
    public static class PaymentViewHolder extends RecyclerView.ViewHolder {
        TextView specializationTextView, dateTextView, amountTextView;

        public PaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            specializationTextView = itemView.findViewById(R.id.specializationTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            amountTextView = itemView.findViewById(R.id.amountTextView);
        }
    }
}