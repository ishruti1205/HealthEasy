package com.example.healtheasy;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {


    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table User_Info(User_Name Text, Email Text, Password Text, login_at TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS Appointments (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "doctor_id INTEGER, " +
                "user_name TEXT, " +
                "specialization TEXT, " +
                "amount REAL, " +
                "date TEXT, " +
                "time TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE User_Info ADD COLUMN login_at TEXT");
        }
        if (oldVersion < 3) {
            db.execSQL("CREATE TABLE Appointments ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "doctor_id INTEGER, "
                    + "user_name TEXT, "
                    +  "specialization TEXT, " +
                    "amount REAL, " +
                        "date TEXT, "
                    + "time TEXT)");
        }

        if (oldVersion < 4) {
            // Check if columns exist before adding them
            Cursor cursor = db.rawQuery("PRAGMA table_info(Appointments)", null);
            boolean specializationExists = false;
            boolean amountExists = false;

            while (cursor.moveToNext()) {
                String columnName = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                if ("specialization".equalsIgnoreCase(columnName)) {
                    specializationExists = true;
                }
                if ("amount".equalsIgnoreCase(columnName)) {
                    amountExists = true;
                }
            }
            cursor.close();

            if (!specializationExists) {
                db.execSQL("ALTER TABLE Appointments ADD COLUMN specialization TEXT");
            }
            if (!amountExists) {
                db.execSQL("ALTER TABLE Appointments ADD COLUMN amount REAL");
            }
        }
    }

    public void signUp(String User_Name, String Email, String Password){
        ContentValues cv = new ContentValues();
        cv.put("User_Name",User_Name);
        cv.put("Email",Email);
        cv.put("Password",Password);

        SQLiteDatabase db = getWritableDatabase();

//        db.insert(User_Name, null, cv);

        long result = db.insert("User_Info", null, cv); // Use table name here

        if (result == -1) {
            Log.e("Database", "Error inserting data into User_Info table");
        } else {
            Log.d("Database", "Data inserted successfully into User_Info table");
        }

        db.close();
    }

    public boolean login(String User_Name, String Password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM User_Info WHERE User_Name = ? AND Password = ?", new String[]{User_Name, Password});

        boolean result = cursor.moveToFirst();

        if (result) {
            // Update login_at timestamp
            ContentValues contentValues = new ContentValues();
            contentValues.put("login_at", String.valueOf(System.currentTimeMillis())); // Store current timestamp in milliseconds

            db = this.getWritableDatabase();
            db.update("User_Info", contentValues, "User_Name = ?", new String[]{User_Name});
        }

        cursor.close();
        db.close();

        return result;
    }

    public String getLastLoginTime(String User_Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT login_at FROM User_Info WHERE User_Name = ?", new String[]{User_Name});

        String loginAt = null;
        if (cursor.moveToFirst()) {
            loginAt = cursor.getString(cursor.getColumnIndexOrThrow("login_at")); // Use getColumnIndexOrThrow
        }

        cursor.close();
        db.close();

        return loginAt;
    }


    public boolean changePassword(String userName, String oldPassword, String newPassword) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM User_Info WHERE User_Name = ? AND Password = ?",
                new String[]{userName, oldPassword}
        );

        if (cursor.moveToFirst()) {
            // Old password matches; update to the new password
            ContentValues contentValues = new ContentValues();
            contentValues.put("Password", newPassword);

            db = this.getWritableDatabase();
            int rowsAffected = db.update(
                    "User_Info",
                    contentValues,
                    "User_Name = ?",
                    new String[]{userName}
            );

            cursor.close();
            db.close();

            return rowsAffected > 0; // Return true if update was successful
        } else {
            // Old password does not match; log for debugging
            Log.e("Database", "Old password does not match for user: " + userName);
            cursor.close();
            db.close();
            return false;
        }
    }

    public void ensureLoginAtColumn() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            // Check if 'login_at' column exists
            cursor = db.rawQuery("PRAGMA table_info(User_Info)", null);
            boolean columnExists = false;

            // Ensure "name" column exists in the result set
            int nameColumnIndex = cursor.getColumnIndex("name");
            if (nameColumnIndex == -1) {
                throw new IllegalStateException("'name' column not found in PRAGMA table_info(User_Info)");
            }

            // Iterate over the cursor to check for "login_at" column
            while (cursor.moveToNext()) {
                String columnName = cursor.getString(nameColumnIndex);
                if ("login_at".equalsIgnoreCase(columnName)) {
                    columnExists = true;
                    break;
                }
            }

            // If column doesn't exist, add it
            if (!columnExists) {
                db.execSQL("ALTER TABLE User_Info ADD COLUMN login_at TEXT");
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
        } finally {
            // Close the cursor and database to avoid memory leaks
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }
    }

    public boolean saveAppointment(int doctorId, String userName, String specialization, double amount, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("doctor_id", doctorId);
        contentValues.put("user_name", userName);
        contentValues.put("specialization", specialization);
        contentValues.put("amount", amount);
        contentValues.put("date", date);
        contentValues.put("time", time);

        long result = -1;
        try {
            result = db.insert("Appointments", null, contentValues);
            if (result == -1) {
                Log.e(TAG, "Failed to insert appointment into Appointments table.");
            } else {
                Log.d(TAG, "Appointment saved successfully with ID: " + result);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting appointment: " + e.getMessage());
        } finally {
            db.close(); // Ensure database is closed
        }

        return result != -1; // Return true if insertion is successful
    }

}