package com.example.dogfood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectCategory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        // Find the button by its ID
        Button btnWetDogFood = findViewById(R.id.btnWetDogFood);
        Button btnKibblesAndBits = findViewById(R.id.btnKibblesAndBits);
        Button btnPuppyFood = findViewById(R.id.btnPuppyFood);
        Button btnSupplements = findViewById(R.id.btnSupplements);
        Button btnFreezeDriedDogFood = findViewById(R.id.btnFreezeDriedDogFood);
        Button btnRawDogFood = findViewById(R.id.btnRawDogFood);

        // Set an OnClickListener on the button
        btnWetDogFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, wetdog.class);
                startActivity(intent);
            }
        });
        btnKibblesAndBits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, Kibbles.class);
                startActivity(intent);
            }
        });
        btnPuppyFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, puppyf.class);
                startActivity(intent);
            }
        });
        btnSupplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, Supplements.class);
                startActivity(intent);
            }
        });
        btnFreezeDriedDogFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, Freeze.class);
                startActivity(intent);
            }
        });
        btnRawDogFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the WetDog activity
                Intent intent = new Intent(SelectCategory.this, RawFood.class);
                startActivity(intent);
            }
        });
    }
}
