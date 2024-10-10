package com.example.healtheasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class PaymentUPIActivity extends AppCompatActivity {
    private ImageView backBtn;
    private Button paymentBtn;
    private EditText UPIIDInput;
    private ImageView cardPaymentMethod, UPIPaymentMethod, netBankingPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_upi_activity);

        backBtn = findViewById(R.id.backBtn);

        // Initialize ImageView
        cardPaymentMethod = findViewById(R.id.cardPaymentMethod);
        UPIPaymentMethod = findViewById(R.id.UPIPaymentMethod);
        netBankingPaymentMethod = findViewById(R.id.netBankingPaymentMethod);

        UPIIDInput = findViewById(R.id.UPIIDInput);
        paymentBtn = findViewById(R.id.paymentBtn);

        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());

        // Retrieve the Doctor object from the intent extras
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        paymentBtn.setText("Pay Rs. " + doctor.getFee());

        // Payment button click listener
        paymentBtn.setOnClickListener(v -> {
            // Retrieve UPI ID input
            String UPIId = UPIIDInput.getText().toString().trim();

            // Validate UPI ID input
            if (isValidUPIId(UPIId)) {
                // Payment logic (e.g., initiate UPI payment with UPIId)
                initiatePaymentWithUPI(UPIId);

                // Navigate to the PaymentSuccessfulActivity after payment
                Intent intent = new Intent(PaymentUPIActivity.this, PaymentSuccessfulActivity.class);
                startActivity(intent);
            }

            else {
                // Display validation error message
                UPIIDInput.setError("Please provide a valid UPI ID");
            }
        });

        // Card Payment button click listener
        cardPaymentMethod.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentUPIActivity.this, PaymentCardActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
            finish();
        });

        // UPI Payment button click listener
        UPIPaymentMethod.setOnClickListener(v -> {
            // Do nothing since already in UPI payment mode
            Toast.makeText(PaymentUPIActivity.this, "Already in UPI payment mode", Toast.LENGTH_SHORT).show();
        });
    }

    // Function to validate UPI ID format
    private boolean isValidUPIId(String UPIId) {
        // Check minimum length requirement
        if (UPIId.length() < 14) {
            return false;
        }

        // Validate format after 10 characters
        if (UPIId.length() >= 11) {
            String afterAtSymbol = UPIId.substring(10); // Get substring after the 10th character
            if (!afterAtSymbol.startsWith("@")) {
                return false;
            }
            String domain = afterAtSymbol.substring(1); // Get domain (sequence of small letters)
            if (!domain.matches("^[a-z]+$")) {
                return false;
            }
        }

        return true;
    }

    private void initiatePaymentWithUPI(String UPIId) {
        // Placeholder method to simulate payment initiation with UPI
        Toast.makeText(PaymentUPIActivity.this, "Initiating UPI payment to " + UPIId, Toast.LENGTH_LONG).show();
        // Logic to initiate UPI payment with the provided UPI ID
    }
}