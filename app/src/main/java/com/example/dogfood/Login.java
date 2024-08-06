package com.example.dogfood;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button forgetPasswordButton;
    private TextView signUpPrompt;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        usernameEditText = findViewById(R.id.etUsername);
        passwordEditText = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.btnLogin);
        forgetPasswordButton = findViewById(R.id.btnForgetPassword);
        signUpPrompt = findViewById(R.id.tvSignUpPrompt);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);

        // Set onClick listener for the sign-up prompt
        signUpPrompt.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Signup.class);
            startActivity(intent);
        });

        // Set onClick listener for the login button
        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Authenticate the user
            boolean isAuthenticated = dbHelper.authenticateUser(username, password);

            Log.d("LoginActivity", "Username: " + username);
            Log.d("LoginActivity", "Password: " + password);
            Log.d("LoginActivity", "Authentication Result: " + isAuthenticated);

            if (isAuthenticated) {
                Intent intent = new Intent(Login.this, SelectCategory.class);
                startActivity(intent);
                finish(); // Optional: finish the login activity
            } else {
                Toast.makeText(Login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Set onClick listener for the forget password button
        forgetPasswordButton.setOnClickListener(v -> {
            Toast.makeText(Login.this, "Forget password functionality not implemented yet", Toast.LENGTH_SHORT).show();
        });
    }
}
