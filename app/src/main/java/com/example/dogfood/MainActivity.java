package com.example.dogfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWetDogFood = findViewById(R.id.btnWetDogFood);
        btnWetDogFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the wetdog activity
                Intent intent = new Intent(MainActivity.this, wetdog.class);
                startActivity(intent);
            }
        });
    }
}
