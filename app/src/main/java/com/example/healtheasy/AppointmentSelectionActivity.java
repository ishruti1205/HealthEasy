package com.example.healtheasy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AppointmentSelectionActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private Button setAppointmentBtn;
    private String selectedDate;
    private String selectedTimeSlot;
    private int selectedDayOfWeek = -1; // Initialize with an invalid value
    private Doctor doctor;
    private Button lastSelectedTimeSlotButton = null;
    private Set<Integer> availableDaysOfWeek;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_selection);

        doctor = (Doctor) getIntent().getSerializableExtra("doctor");

        ImageView backBtn = findViewById(R.id.backBtn);
        calendarView = findViewById(R.id.calendarView);
        setAppointmentBtn = findViewById(R.id.setAppointmentBtn);

        calendarView.setMinDate(System.currentTimeMillis());

        Calendar maxDateCalendar = Calendar.getInstance();
        maxDateCalendar.add(Calendar.MONTH, 2);
        calendarView.setMaxDate(maxDateCalendar.getTimeInMillis());

        availableDaysOfWeek = new HashSet<>();
        availableDaysOfWeek.add(getDayOfWeek(doctor.getDay1()));
        availableDaysOfWeek.add(getDayOfWeek(doctor.getDay2()));

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(year, month, dayOfMonth);

            // Store the selected day of the week
            selectedDayOfWeek = selectedCalendar.get(Calendar.DAY_OF_WEEK);

            // Clear previous time slot selection
            if (lastSelectedTimeSlotButton != null) {
                lastSelectedTimeSlotButton.setSelected(false);
                lastSelectedTimeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.blackColor));
                lastSelectedTimeSlotButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.whiteColor));
                lastSelectedTimeSlotButton = null;
                selectedTimeSlot = null;
            }

            if (availableDaysOfWeek.contains(selectedDayOfWeek)) {
                selectedDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(selectedCalendar.getTime());
                setupTimeSlots(true, selectedCalendar); // Doctor available
            } else {
                Toast.makeText(this, "Doctor is not available on this day", Toast.LENGTH_SHORT).show();
                flag = 1;
                selectedDate = null;
                setupTimeSlots(false, null); // Doctor not available
            }
        });

        backBtn.setOnClickListener(v -> onBackPressed());

//        setAppointmentBtn.setOnClickListener(v -> {
//            if (selectedDate == null && selectedTimeSlot == null) {
//                Toast.makeText(this, "Please select date and time slot", Toast.LENGTH_SHORT).show();
//            }
//
//            else if (selectedDate == null) {
//                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
//            }
//
//            else if (selectedTimeSlot == null) {
//                Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
//            }
//
//            else if (selectedDate != null && selectedTimeSlot != null) {
//                String appointmentDetails = "Appointment set for \n" + selectedDate + " at " + selectedTimeSlot;
//                Toast.makeText(this, appointmentDetails, Toast.LENGTH_LONG).show();
//
//                Intent intent = new Intent(AppointmentSelectionActivity.this, PaymentActivity.class);
//                intent.putExtra("doctor", doctor);
//                startActivity(intent);
//            }
//
//            else if (flag == 1) {
//                Toast.makeText(this, "Please select a date from available weekdays only", Toast.LENGTH_SHORT).show();
//            }
//        });

        setAppointmentBtn.setOnClickListener(v -> {
            if (selectedDate == null && selectedTimeSlot == null) {
                Toast.makeText(this, "Please select date and time slot", Toast.LENGTH_SHORT).show();
            } else if (selectedDate == null) {
                Toast.makeText(this, "Please select a date", Toast.LENGTH_SHORT).show();
            } else if (selectedTimeSlot == null) {
                Toast.makeText(this, "Please select a time slot", Toast.LENGTH_SHORT).show();
            } else {
                // Fetch logged-in user name
                SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
                String userName = preferences.getString("username", "");

                if (userName.isEmpty()) {
                    Toast.makeText(this, "User not logged in. Please log in first.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Log the values for debugging
                Log.d("Debug", "Doctor ID: " + doctor.getId());
                Log.d("Debug", "Username: " + userName);
                Log.d("Debug", "Selected Date: " + selectedDate);
                Log.d("Debug", "Selected Time Slot: " + selectedTimeSlot);
                Log.d("Debug", "Specialization: " + doctor.getSpecialization());
                Log.d("Debug", "Amount: " + doctor.getFee());

                // Save appointment to the database
                Database db = new Database(this, "HealthEasy", null, 4);
                boolean success = db.saveAppointment(
                        doctor.getId(),
                        userName,
                        doctor.getSpecialization(),
                        doctor.getFee(),
                        selectedDate,
                        selectedTimeSlot
                );

                if (success) {
                    Toast.makeText(this, "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AppointmentSelectionActivity.this, PaymentActivity.class);
                    intent.putExtra("doctor", doctor);
                    intent.putExtra("selectedDate", selectedDate);
                    intent.putExtra("selectedTimeSlot", selectedTimeSlot);
                    startActivity(intent);                } else {
                    Toast.makeText(this, "Failed to save appointment. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setupTimeSlots(false, null); // Initial state with no date selected
    }

    private void setupTimeSlots(boolean isDoctorAvailable, Calendar selectedCalendar) {
        String[] timeSlots = doctor.getTimeSlot().split("-");
        String startTime = timeSlots[0].trim();
        String endTime = timeSlots[1].trim();
        int numSlots = 4;
        int slotMinutes = 30;

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());

        // Get the current time
        Calendar currentTime = Calendar.getInstance();
        currentTime.set(Calendar.SECOND, 0);
        currentTime.set(Calendar.MILLISECOND, 0);

        boolean isToday = selectedCalendar != null && isToday(selectedCalendar, currentTime);

        boolean allSlotsPast = true; // Flag to track if all time slots are past

        for (int i = 0; i < numSlots; i++) {
            Button timeSlotButton = getTimeSlotButton(i + 1);
            if (timeSlotButton != null) {
                calendar.setTime(getTime(startTime));
                calendar.add(Calendar.MINUTE, i * slotMinutes);
                String timeSlot = sdf.format(calendar.getTime());
                timeSlotButton.setText(timeSlot);

                if (!isDoctorAvailable || !availableDaysOfWeek.contains(selectedDayOfWeek)) {
                    // Doctor unavailable or selected day not available
                    timeSlotButton.setEnabled(false);
                    timeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.grayColor));
                } else if (isToday) {
                    // Enable or disable slots based on current time
                    if (isPastTime(calendar, currentTime)) {
                        // Disable past slots for today
                        timeSlotButton.setEnabled(false);
                        timeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.grayColor));
                        Log.d("Debug", "Past Time Slot: " + timeSlot);
                    } else {
                        // Enable future slots for today
                        timeSlotButton.setEnabled(true);
                        timeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.blackColor));
                        timeSlotButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.whiteColor));
                        allSlotsPast = false; // At least one slot is future, so not all are past
                    }
                } else {
                    // Enable slots for future dates
                    timeSlotButton.setEnabled(true);
                    timeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.blackColor));
                    timeSlotButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.whiteColor));
                    allSlotsPast = false; // Not a today, so definitely not all past
                }

                // Set click listener to select the time slot
                timeSlotButton.setOnClickListener(v -> selectTimeSlot(timeSlotButton));
            }
        }

        // Show toast if all time slots are past for today
        if (isToday && allSlotsPast) {
            Toast.makeText(this, "All time slots are over for today!", Toast.LENGTH_SHORT).show();
        }
    }

    // Helper method to check if the selected day is today
    private boolean isToday(Calendar selectedCalendar, Calendar currentTime) {
        return selectedCalendar.get(Calendar.YEAR) == currentTime.get(Calendar.YEAR)
                && selectedCalendar.get(Calendar.DAY_OF_YEAR) == currentTime.get(Calendar.DAY_OF_YEAR);
    }

    // Helper method to check if a given time is in the past compared to the current time
    private boolean isPastTime(Calendar selectedTime, Calendar currentTime) {
        // Compare date first
        int compareDate = selectedTime.get(Calendar.DAY_OF_YEAR) - currentTime.get(Calendar.DAY_OF_YEAR);

        if (compareDate < 0) {
            // Selected date is before current date
            return true;
        } else if (compareDate > 0) {
            // Selected date is after current date
            return false;
        } else {
            // Dates are the same, compare time
            return selectedTime.getTimeInMillis() <= currentTime.getTimeInMillis();
        }
    }

    private Button getTimeSlotButton(int slotNumber) {
        switch (slotNumber) {
            case 1:
                return findViewById(R.id.timeSlot1);
            case 2:
                return findViewById(R.id.timeSlot2);
            case 3:
                return findViewById(R.id.timeSlot3);
            case 4:
                return findViewById(R.id.timeSlot4);
            default:
                return null;
        }
    }

    private void selectTimeSlot(Button selectedButton) {
        if (!selectedButton.isEnabled()) {
            Toast.makeText(this, "Time slot not available", Toast.LENGTH_SHORT).show();
            return;
        }

        if (lastSelectedTimeSlotButton != null) {
            lastSelectedTimeSlotButton.setSelected(false);
            lastSelectedTimeSlotButton.setTextColor(ContextCompat.getColor(this, R.color.blackColor));
            lastSelectedTimeSlotButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.whiteColor));
        }

        selectedButton.setSelected(true);
        selectedButton.setTextColor(ContextCompat.getColor(this, R.color.whiteColor));
        selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blueColor));

        lastSelectedTimeSlotButton = selectedButton;
        selectedTimeSlot = selectedButton.getText().toString();
    }

    private int getDayOfWeek(String day) {
        switch (day.toLowerCase()) {
            case "sunday":
                return Calendar.SUNDAY;
            case "monday":
                return Calendar.MONDAY;
            case "tuesday":
                return Calendar.TUESDAY;
            case "wednesday":
                return Calendar.WEDNESDAY;
            case "thursday":
                return Calendar.THURSDAY;
            case "friday":
                return Calendar.FRIDAY;
            case "saturday":
                return Calendar.SATURDAY;
            default:
                return -1;
        }
    }

    private Date getTime(String time) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            return sdf.parse(time);  // Parse the time string directly
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Return null if parsing fails
        }
    }

}
