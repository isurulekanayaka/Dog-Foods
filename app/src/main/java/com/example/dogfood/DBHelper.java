package com.example.dogfood;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

// DBHelper.java
public class DBHelper extends SQLiteOpenHelper {

    // Database constants
    private static final String DATABASE_NAME = "DogFood.db";
    private static final int DATABASE_VERSION = 2; // Increment the version to trigger onUpgrade

    // Table and column names
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL_PHONE = "email_or_phone";
    private static final String COLUMN_FULL_NAME = "full_name";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role"; // New column for user role

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the users table
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL_PHONE + " TEXT UNIQUE, "
                + COLUMN_FULL_NAME + " TEXT, "
                + COLUMN_USERNAME + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT, "
                + COLUMN_ROLE + " TEXT DEFAULT 'user'" + ")"; // Set default role as 'user'
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            // Alter the table to add the new role column
            db.execSQL("ALTER TABLE " + TABLE_USERS + " ADD COLUMN " + COLUMN_ROLE + " TEXT DEFAULT 'user'");
        }
    }

    // Check if email or phone number already exists
    public boolean isEmailOrPhoneExists(String emailOrPhone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_ID},
                COLUMN_EMAIL_PHONE + "=?", new String[]{emailOrPhone.trim()}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Method to register a new user
    public boolean registerUser(String emailOrPhone, String fullName, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Check if email or phone number already exists
        if (isEmailOrPhoneExists(emailOrPhone)) {
            Toast.makeText(context, "This email or phone number is already registered.", Toast.LENGTH_SHORT).show();
            return false;
        }

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL_PHONE, emailOrPhone.trim());
        values.put(COLUMN_FULL_NAME, fullName.trim());
        values.put(COLUMN_USERNAME, username.trim());
        values.put(COLUMN_PASSWORD, password.trim());
        values.put(COLUMN_ROLE, "user"); // Set role as 'user'

        // Insert row
        long result = db.insert(TABLE_USERS, null, values);

        // Check if insert was successful
        if (result == -1) {
            Toast.makeText(context, "Registration failed!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    // Authenticate user
    public boolean authenticateUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=? AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username.trim(), password.trim()};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        // Log the actual query for debugging
        Log.d("DBHelper", "Authenticate User Query - Username: " + username + ", Password: " + password);
        Log.d("DBHelper", "Query Result Count: " + cursor.getCount());

        boolean isAuthenticated = cursor.getCount() > 0;
        cursor.close();

        Log.d("DBHelper", "Authenticate User - Username: " + username + ", Authenticated: " + isAuthenticated);
        return isAuthenticated;
    }
}
