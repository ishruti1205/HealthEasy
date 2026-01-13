package com.example.healtheasy;
import java.io.Serializable;

public class Doctor implements Serializable {
    private int id;
    private String name;
    private String specialization;
    private String location;
    private int patients, imageResource;
    private String experience;
    private double rating, fee;
    private String hospital, degree;
    private String day1, day2;
    private String timeSlot;

    // Constructor
    public Doctor(int id, int imageResource, String name, String specialization, String degree, String location,
                  int patients, String experience, double rating, String hospital, double fee,
                  String day1, String day2, String timeSlot) {
        this.id = id;
        this.imageResource = imageResource;
        this.name = name;
        this.specialization = specialization;
        this.location = location;
        this.patients = patients;
        this.experience = experience;
        this.rating = rating;
        this.hospital = hospital;
        this.fee = fee;
        this.degree = degree;
        this.day1 = day1;
        this.day2 = day2;
        this.timeSlot = timeSlot;
    }

    // Getters for attributes
    public int getId(){
        return id;
    }
    public int getImageResource() {
        return imageResource;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getLocation() {
        return location;
    }

    public int getPatients() {
        return patients;
    }

    public String getExperience() {
        return experience;
    }

    public double getRating() {
        return rating;
    }

    public String getHospital() {
        return hospital;
    }

    public String getDegree() {
        return degree;
    }

    public double getFee() {
        return fee;
    }

    public String getDay1() {
        return day1;
    }

    public String getDay2() {
        return day2;
    }

    public String getTimeSlot() {
        return timeSlot;
    }
}
