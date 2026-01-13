package com.example.healtheasy;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyPaymentsActivity extends AppCompatActivity {

    private RecyclerView paymentsRecyclerView;
    private TextView noPaymentsMessage;
    private PaymentsAdapter paymentsAdapter;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_payments);

        paymentsRecyclerView = findViewById(R.id.recyclerViewPayments);
        noPaymentsMessage = findViewById(R.id.noPaymentsMessage);

        // Back button click listener
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(v -> onBackPressed());

        // Fetch logged-in user name
        String userName = getSharedPreferences("userPrefs", MODE_PRIVATE)
                .getString("username", null);

        if (userName == null) {
            Toast.makeText(this, "User not logged in. Please log in first.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Fetch payment details from the database
        Database db = new Database(this, "HealthEasy", null, 4);
        Cursor cursor = db.getReadableDatabase().rawQuery(
                "SELECT * FROM Appointments WHERE user_name = ?",
                new String[]{userName}
        );

        if (cursor.getCount() == 0) {
            // No payment details found
            noPaymentsMessage.setVisibility(View.VISIBLE);
            paymentsRecyclerView.setVisibility(View.GONE);
        } else {
            // Populate payment details in a list
            List<PaymentDetail> paymentDetails = new ArrayList<>();
            while (cursor.moveToNext()) {
                String specialization = cursor.getString(cursor.getColumnIndex("specialization"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                double amount = cursor.getDouble(cursor.getColumnIndex("amount"));

                // Add each payment detail to the list
                paymentDetails.add(new PaymentDetail(specialization, date, amount));
            }
            cursor.close();

            // Set up the RecyclerView
            paymentsAdapter = new PaymentsAdapter(paymentDetails);
            paymentsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            paymentsRecyclerView.setAdapter(paymentsAdapter);

            paymentsRecyclerView.setVisibility(View.VISIBLE);
            noPaymentsMessage.setVisibility(View.GONE);
        }
    }
}