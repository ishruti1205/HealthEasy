package com.example.healtheasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PaymentActivity extends AppCompatActivity {
    private ImageView cardPaymentMethod, UPIPaymentMethod, netBankingPaymentMethod;
    private ImageView backBtn;
    private Button paymentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        cardPaymentMethod = findViewById(R.id.cardPaymentMethod);
        UPIPaymentMethod = findViewById(R.id.UPIPaymentMethod);
        netBankingPaymentMethod = findViewById(R.id.netBankingPaymentMethod);

        backBtn = findViewById(R.id.backBtn);
        paymentBtn = findViewById(R.id.paymentBtn);

        // Back button click listener
        backBtn.setOnClickListener(v -> onBackPressed());

        // Retrieve the Doctor object from the intent extras
        Doctor doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        paymentBtn.setText("Pay Rs. " + doctor.getFee());

        paymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PaymentActivity.this, "Please Select a Payment Method!", Toast.LENGTH_LONG).show();
            }

        });


        // Card Payment button click listener
        cardPaymentMethod.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentCardActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
            finish();
        });

        // UPI Payment button click listener
        UPIPaymentMethod.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, PaymentUPIActivity.class);
            intent.putExtra("doctor", doctor);
            startActivity(intent);
            finish();
        });

    }
}