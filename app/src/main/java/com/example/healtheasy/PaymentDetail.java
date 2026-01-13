package com.example.healtheasy;

public class PaymentDetail {
    private String specialization;
    private String date;
    private double amount;

    public PaymentDetail(String specialization, String date, double amount) {
        this.specialization = specialization;
        this.date = date;
        this.amount = amount;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}