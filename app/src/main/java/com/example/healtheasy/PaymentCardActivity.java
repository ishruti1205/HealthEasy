package com.example.healtheasy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentCardActivity extends AppCompatActivity {

    private EditText cardNumberInput;
    private EditText cardHolderNameInput;
    private EditText cardExpiryDateInput;
    private EditText cardCVVInput;
    private Button scanCardBtn;
    private Button paymentBtn;
    private ImageView backBtn;
    private ImageView cardPaymentMethod, UPIPaymentMethod, netBankingPaymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_card);

        // Initialize EditText fields
        cardNumberInput = findViewById(R.id.cardNumberInput);
        cardHolderNameInput = findViewById(R.id.cardHolderNameInput);
        cardExpiryDateInput = findViewById(R.id.cardExpiryDateInput);
        cardCVVInput = findViewById(R.id.cardCVVInput);

        // Initialize buttons
        scanCardBtn = findViewById(R.id.scanCardBtn);
        paymentBtn = findViewById(R.id.paymentBtn);

        backBtn = findViewById(R.id.backBtn);

        // Initialize ImageView
        cardPaymentMethod = findViewById(R.id.cardPaymentMethod);
        UPIPaymentMethod = findViewById(R.id.UPIPaymentMethod);
        netBankingPaymentMethod = findViewById(R.id.netBankingPaymentMethod);

        // Retrieve the Doctor object from the intent extras
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        paymentBtn.setText("Pay Rs. " + doctor.getFee());

        // Set click listener for Scan Card button
        scanCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to handle card scanning (e.g., open camera for scanning)
                Toast.makeText(PaymentCardActivity.this, "Scan your card", Toast.LENGTH_SHORT).show();
            }
        });

        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());

        // Set click listener for Payment button
        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input values from EditText fields
                String cardNumber = cardNumberInput.getText().toString().trim();
                String cardHolderName = cardHolderNameInput.getText().toString().trim();
                String expiryDate = cardExpiryDateInput.getText().toString().trim();
                String cvv = cardCVVInput.getText().toString().trim();

                // Validate input values
                boolean isValid = true;

                if (cardNumber.length() != 16) {
                    cardNumberInput.setError("Please enter a valid card number");
                    isValid = false;
                }

                if (!isValidExpiryDate(expiryDate)) {
                    cardExpiryDateInput.setError("Please enter a valid expiry date");
                    isValid = false;
                }

                if (cvv.length() != 3) {
                    cardCVVInput.setError("Please enter a valid CVV");
                    isValid = false;
                }

                if (cardHolderName.isEmpty()) {
                    cardHolderNameInput.setError("Please enter the card holder's name");
                    isValid = false;
                }

                // Check if all validations passed
                if (isValid) {
                    // Payment logic (e.g., validate card details, process payment)
                    initiatePaymentWithCard(cardNumber, cardHolderName, expiryDate, cvv);

                    // Navigate to the PaymentSuccessfulActivity after payment
                    Intent intent = new Intent(PaymentCardActivity.this, PaymentSuccessfulActivity.class);
                    startActivity(intent);
                }
            }
        });


        // Card Payment button click listener
        cardPaymentMethod.setOnClickListener(v -> {
            // Do nothing since already in Card payment mode
            Toast.makeText(PaymentCardActivity.this, "Already in Card payment mode", Toast.LENGTH_SHORT).show();
        });

        // UPI Payment button click listener
        UPIPaymentMethod.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentCardActivity.this, PaymentUPIActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
            finish();
        });
    }

    // Function to validate expiry date format (MM/YY) and check validity
    private boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate.length() != 5) {
            return false;
        }

        String[] parts = expiryDate.split("/");
        if (parts.length != 2) {
            return false;
        }

        try {
            int month = Integer.parseInt(parts[0]);
            int year = Integer.parseInt(parts[1]);

            // Get the current year dynamically
            Calendar calendar = Calendar.getInstance();
            int currentYear = calendar.get(Calendar.YEAR) % 100; // Get last two digits of the current year

            // Validate month (01-12) and year (current year to current year + 5)
            if (month < 1 || month > 12) {
                return false;
            }
            if (year < currentYear || year > currentYear + 5) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    private void initiatePaymentWithCard(String cardNumber, String cardHolderName, String expiryDate, String cvv) {
        // Placeholder method to simulate payment initiation with Card
        Toast.makeText(PaymentCardActivity.this, "Initiating payment...", Toast.LENGTH_LONG).show();
        // Logic to initiate Card payment with the provided Card details
    }
}
