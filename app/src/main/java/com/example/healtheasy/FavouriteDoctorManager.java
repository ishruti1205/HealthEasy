package com.example.healtheasy;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FavouriteDoctorManager {

    private static final String PREF_NAME = "FavouriteDoctors";

    // Method to generate a unique key for a doctor based on multiple fields
    public static String generateDoctorKey(Doctor doctor) {
        return doctor.getImageResource() + "_" + doctor.getName() + "_" + doctor.getLocation() + "_" + doctor.getSpecialization()
                + "_" + doctor.getPatients() + "_" + doctor.getExperience() + "_" + doctor.getRating()
                + "_" + doctor.getHospital() + "_" + doctor.getDay1() + "_" + doctor.getDay2() + "_" + doctor.getDegree();
    }

    // Method to get the favorite doctor keys for a specific user
    public static Set<String> getFavouriteDoctorKeys(Context context, String username) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return preferences.getStringSet(getUserFavouritesKey(username), new HashSet<>());
    }

    // Method to add a doctor to favorites
    public static void addToFavourites(Context context, String username, Doctor doctor) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Get existing favorite doctors set for the specific user
        Set<String> favouriteDoctorKeys = new HashSet<>(getFavouriteDoctorKeys(context, username));

        // Generate a unique key and add the doctor to favorites
        favouriteDoctorKeys.add(generateDoctorKey(doctor));

        // Save the updated favorite doctors set
        editor.putStringSet(getUserFavouritesKey(username), favouriteDoctorKeys);
        editor.apply(); // Use apply() for asynchronous save
    }

    // Method to check if a doctor is in favorites
    public static boolean isFavourite(Context context, String username, Doctor doctor) {
        Set<String> favouriteDoctorKeys = getFavouriteDoctorKeys(context, username);

        // Generate a unique key and check if the doctor is in the favorite doctors set
        return favouriteDoctorKeys.contains(generateDoctorKey(doctor));
    }

    // Method to remove a doctor from favorites
    public static void removeFromFavourites(Context context, String username, Doctor doctor) {
        SharedPreferences preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        // Get existing favorite doctors set for the specific user
        Set<String> favouriteDoctorKeys = new HashSet<>(getFavouriteDoctorKeys(context, username));

        // Generate a unique key and remove the doctor from favorites
        favouriteDoctorKeys.remove(generateDoctorKey(doctor));

        // Save the updated favorite doctors set
        editor.putStringSet(getUserFavouritesKey(username), favouriteDoctorKeys);
        editor.commit(); // Use apply() for asynchronous save
    }

    // Method to get the key for storing favorite doctors for a specific user
    private static String getUserFavouritesKey(String username) {
        return username + "_favouriteDoctorKeys";
    }
}
