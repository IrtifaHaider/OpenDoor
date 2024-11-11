package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opendoor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRnt extends AppCompatActivity {

    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton, forgotPasswordButton, signUpButton, cancelButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_rnt);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Bind UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        signUpButton = findViewById(R.id.sign_up_button);
        cancelButton = findViewById(R.id.cancel_button);

        // Login button click listener
        loginButton.setOnClickListener(v -> loginUser());

        // Forgot Password button click listener
        forgotPasswordButton.setOnClickListener(v -> resetPassword());

        // Sign Up button click listener
        signUpButton.setOnClickListener(v -> navigateToSignUp());

        // Cancel button click listener (goes back to previous screen)
        cancelButton.setOnClickListener(v -> finish());
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateInput(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(LoginRnt.this, "Login successful", Toast.LENGTH_SHORT).show();

                            // Navigate to MainActivity (replace with your main screen)
                            Intent intent = new Intent(LoginRnt.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginRnt.this, "Authentication failed", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(LoginRnt.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginRnt.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void navigateToSignUp() {
        // Navigate to SignUpActivity
        Intent intent = new Intent(LoginRnt.this, SignUpRnt.class);
        startActivity(intent);
    }
}
