package com.example.dogfood;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private EditText emailOrPhoneEditText;
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private TextView loginPrompt; // Add this line

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        emailOrPhoneEditText = findViewById(R.id.email_or_phone);
        fullNameEditText = findViewById(R.id.fullname);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        signUpButton = findViewById(R.id.btnSignup);
        progressBar = findViewById(R.id.LProgressBar);
        loginPrompt = findViewById(R.id.tvSignUpPrompt); // Initialize the TextView

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set onClick listener for the sign-up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailOrPhone = emailOrPhoneEditText.getText().toString().trim();
                String fullName = fullNameEditText.getText().toString().trim();

                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate input
                if (emailOrPhone.isEmpty() || fullName.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show progress bar while processing
                progressBar.setVisibility(View.VISIBLE);

                // Register the user
                boolean isRegistered = dbHelper.registerUser(emailOrPhone, fullName, username, password);

                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                if (isRegistered) {
                    // Registration successful
                    Toast.makeText(Signup.this, "Registration successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to Login activity
                    Intent intent = new Intent(Signup.this, Login.class);
                    startActivity(intent);
                    finish(); // Optional: finish the signup activity
                } else {
                    // Registration failed
                    Toast.makeText(Signup.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClick listener for the login prompt TextView
        loginPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login activity
                Intent intent = new Intent(Signup.this, Login.class);
                startActivity(intent);
                finish(); // Optional: finish the signup activity
            }
        });
    }
}
