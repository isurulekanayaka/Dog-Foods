package com.example.dogfood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetails extends AppCompatActivity {

    private static final String DATABASE_NAME = "DogFood.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_TYPE = "type";

    private SQLiteDatabase db;
    private TableLayout orderTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        orderTable = findViewById(R.id.order_table);

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
        db = dbHelper.getReadableDatabase();

        // Load orders from database and display
        loadOrders();
    }

    private void loadOrders() {
        Cursor cursor = db.query(TABLE_ORDERS, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                TableRow row = new TableRow(this);
                row.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                // Create TextViews for each column
                TextView orderIdTextView = new TextView(this);
                orderIdTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                orderIdTextView.setPadding(3, 3, 3, 3);

                TextView productTypeTextView = new TextView(this);
                productTypeTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));
                productTypeTextView.setPadding(3, 3, 3, 3);

                TextView quantityTextView = new TextView(this);
                quantityTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                quantityTextView.setPadding(3, 3, 3, 3);

                TextView addressTextView = new TextView(this);
                addressTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS)));
                addressTextView.setPadding(3, 3, 3, 3);

                TextView nameTextView = new TextView(this);
                nameTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                nameTextView.setPadding(3, 3, 3, 3);

                // Add TextViews to TableRow
                row.addView(orderIdTextView);
                row.addView(productTypeTextView);
                row.addView(quantityTextView);
                row.addView(addressTextView);
                row.addView(nameTextView);

                // Add TableRow to TableLayout
                orderTable.addView(row);
            }
            cursor.close();
        } else {
            Toast.makeText(this, "No orders found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }
}
