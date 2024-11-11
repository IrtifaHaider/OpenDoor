package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.opendoor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpRnt extends AppCompatActivity {

    private TextInputEditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton, cancelButton, loginButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_rnt);

        // Initialize Firebase Authentication
        auth = FirebaseAuth.getInstance();

        // Bind UI components
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        signUpButton = findViewById(R.id.sign_up_button);
        cancelButton = findViewById(R.id.cancel_button);
        loginButton = findViewById(R.id.login_button);

        // Set up button click listeners
        signUpButton.setOnClickListener(v -> createAccount());
        cancelButton.setOnClickListener(v -> finish());  // Closes the activity
        loginButton.setOnClickListener(v -> navigateToLogin());
    }

    private void createAccount() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate input
        if (validateInput(username, email, password, confirmPassword)) {
            // Create a new user with Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(SignUpRnt.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        } else {
                            Toast.makeText(SignUpRnt.this, "Account creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Please enter a username");
            return false;
        }
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Please enter an email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter a password");
            return false;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordEditText.setError("Please confirm your password");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Passwords do not match");
            return false;
        }
        return true;
    }

    private void navigateToLogin() {
        Intent intent = new Intent(SignUpRnt.this, Rent.class);  // Replace LoginActivity.class with your login activity
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignUpRnt.this, MainActivity.class);  // Replace MainActivity.class with your main activity
        startActivity(intent);
        finish();
    }
}
