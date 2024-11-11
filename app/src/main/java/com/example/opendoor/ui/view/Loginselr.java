package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opendoor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginselr extends AppCompatActivity {

    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton, forgotPasswordButton, signUpButton, cancelButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loginselr);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Bind UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        signUpButton = findViewById(R.id.sign_up_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Set up button click listeners
        loginButton.setOnClickListener(v -> loginUser());
        forgotPasswordButton.setOnClickListener(v -> resetPassword());
        signUpButton.setOnClickListener(v -> navigateToSignUp());
        cancelButton.setOnClickListener(v -> finish()); // Close the activity on cancel
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateInput(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(Loginselr.this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Navigate to the main activity or home screen
                            Intent intent = new Intent(Loginselr.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Loginselr.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            usernameEditText.setError("Please enter your email");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Please enter your password");
            return false;
        }
        return true;
    }

    private void resetPassword() {
        String email = usernameEditText.getText().toString().trim();

        if (email.isEmpty()) {
            usernameEditText.setError("Enter your registered email");
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(Loginselr.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Loginselr.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToSignUp() {
        // Navigate to SignUpActivity
        Intent intent = new Intent(Loginselr.this, SignUpselr.class);
        startActivity(intent);
    }
}
