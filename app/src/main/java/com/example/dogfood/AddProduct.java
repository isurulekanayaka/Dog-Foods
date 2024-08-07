package com.example.dogfood;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AddProduct extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final String DATABASE_NAME = "DogFood.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_BRAND = "brand";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_IMAGE_URI = "image_uri";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_DESCRIPTION + " TEXT, " +
                    COLUMN_PRICE + " REAL, " +
                    COLUMN_BRAND + " TEXT, " +
                    COLUMN_QUANTITY + " INTEGER, " +
                    COLUMN_IMAGE_URI + " TEXT" +
                    ");";

    private SQLiteDatabase db;
    private Uri imageUri;
    private ImageView productImage;
    private Button selectImageButton, submitProductButton;
    private EditText productName, productDescription, productPrice, productBrand, productQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productImage = findViewById(R.id.productImage);
        selectImageButton = findViewById(R.id.selectImageButton);
        submitProductButton = findViewById(R.id.submitProductButton);
        productName = findViewById(R.id.productName);
        productDescription = findViewById(R.id.productDescription);
        productPrice = findViewById(R.id.productPrice);
        productBrand = findViewById(R.id.productBrand);
        productQuantity = findViewById(R.id.productQuantity);

        // Open or create the database
        db = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        db.execSQL(TABLE_CREATE);

        selectImageButton.setOnClickListener(v -> openImageChooser());
        submitProductButton.setOnClickListener(v -> addProductToDatabase());
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            productImage.setImageURI(imageUri);
        }
    }

    private void addProductToDatabase() {
        String name = productName.getText().toString().trim();
        String description = productDescription.getText().toString().trim();
        String priceStr = productPrice.getText().toString().trim();
        String brand = productBrand.getText().toString().trim();
        String quantityStr = productQuantity.getText().toString().trim();

        if (name.isEmpty() || description.isEmpty() || priceStr.isEmpty() || brand.isEmpty() || quantityStr.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        int quantity = Integer.parseInt(quantityStr);

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_BRAND, brand);
        values.put(COLUMN_QUANTITY, quantity);
        values.put(COLUMN_IMAGE_URI, imageUri.toString());

        long newRowId = db.insert(TABLE_PRODUCTS, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Error adding product", Toast.LENGTH_SHORT).show();
        }
    }
}
