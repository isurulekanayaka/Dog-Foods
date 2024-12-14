package com.example.dogfood;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminRegister extends AppCompatActivity {

    private EditText emailOrPhoneEditText;
    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button signUpButton;
    private ProgressBar progressBar;
    private TextView loginPrompt;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        // Initialize views
        emailOrPhoneEditText = findViewById(R.id.email_or_phone_admin);
        fullNameEditText = findViewById(R.id.fullname_admin);
        usernameEditText = findViewById(R.id.username_admin);
        passwordEditText = findViewById(R.id.password_admin);
        signUpButton = findViewById(R.id.btnAdminSignup);
        progressBar = findViewById(R.id.LProgressBarAdmin);
        loginPrompt = findViewById(R.id.have_account_admin);

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
                    Toast.makeText(AdminRegister.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Show progress bar while processing
                progressBar.setVisibility(View.VISIBLE);

                // Register the admin
                boolean isRegistered = dbHelper.registerAdmin(emailOrPhone, fullName, username, password);

                // Hide progress bar
                progressBar.setVisibility(View.GONE);

                if (isRegistered) {
                    // Registration successful
                    Toast.makeText(AdminRegister.this, "Admin registration successful!", Toast.LENGTH_SHORT).show();

                    // Navigate to AdminSettings activity
                    Intent intent = new Intent(AdminRegister.this, AdminSetting.class);
                    startActivity(intent);
                    finish(); // Optional: finish the admin register activity
                } else {
                    // Registration failed
                    Toast.makeText(AdminRegister.this, "Registration failed. Try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClick listener for the login prompt TextView
        loginPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Login activity
                Intent intent = new Intent(AdminRegister.this, Login.class);
                startActivity(intent);
                finish(); // Optional: finish the admin register activity
            }
        });
    }
}
