package com.example.dogfood;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PlaceOrderSupplements extends AppCompatActivity {

    private EditText quantityEditText;
    private EditText addressEditText;
    private EditText nameEditText;
    private Button submitButton;

    private static final String DATABASE_NAME = "DogFood.db";
    private static final int DATABASE_VERSION = 1;

    // Table and Column names
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order_supplements);

        // Initialize views
        quantityEditText = findViewById(R.id.quantity);
        addressEditText = findViewById(R.id.address);
        nameEditText = findViewById(R.id.name);
        submitButton = findViewById(R.id.submit_button);

        // Initialize database
        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(this, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String TABLE_CREATE =
                        "CREATE TABLE " + TABLE_ORDERS + " (" +
                                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                COLUMN_QUANTITY + " INTEGER, " +
                                COLUMN_ADDRESS + " TEXT, " +
                                COLUMN_NAME + " TEXT, " +
                                COLUMN_TYPE + " TEXT);";
                db.execSQL(TABLE_CREATE);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
                onCreate(db);
            }
        };
        db = dbHelper.getWritableDatabase();

        // Set onClick listener for the submit button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrder();
            }
        });
    }

    private void saveOrder() {
        // Get input values
        String quantity = quantityEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String type = "Supplements";

        // Validate input
        if (quantity.isEmpty() || address.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create ContentValues object
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);

        // Insert into database
        long newRowId = db.insert(TABLE_ORDERS, null, values);

        // Show a message based on the result
        if (newRowId != -1) {
            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to place order", Toast.LENGTH_SHORT).show();
        }

        // Clear fields
        quantityEditText.setText("");
        addressEditText.setText("");
        nameEditText.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
