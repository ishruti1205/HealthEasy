package com.example.healtheasy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create Table User_Info(User_Name Text, Email Text, Password Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

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

        cursor.close();
        db.close();

        return result;
    }

}
