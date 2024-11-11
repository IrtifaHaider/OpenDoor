package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.opendoor.R;
import com.example.opendoor.ui.viewmodel.SignUpViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpRenter extends AppCompatActivity {

    private TextInputEditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button signUpButton, cancelButton, loginButton;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_renter);

        // Initialize ViewModel
        signUpViewModel = new ViewModelProvider(this).get(SignUpViewModel.class);

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
        cancelButton.setOnClickListener(v -> finish());
        loginButton.setOnClickListener(v -> navigateToLogin());

        // Observe ViewModel LiveData for success or failure
        signUpViewModel.getUserLiveData().observe(this, firebaseUser -> {
            if (firebaseUser != null) {
                Toast.makeText(SignUpRenter.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            }
        });

        signUpViewModel.getSignUpFailedLiveData().observe(this, signUpFailed -> {
            if (signUpFailed) {
                Toast.makeText(SignUpRenter.this, "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (validateInput(username, email, password, confirmPassword)) {
            signUpViewModel.signUpUser(username, email, password);
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
        Intent intent = new Intent(SignUpRenter.this, LoginRenter.class);
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SignUpRenter.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
