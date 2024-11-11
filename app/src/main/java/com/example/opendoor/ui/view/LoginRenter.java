package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.opendoor.R;
import com.example.opendoor.ui.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class LoginRenter extends AppCompatActivity {

    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton, forgotPasswordButton, signUpButton;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_renter);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Bind UI components
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        signUpButton = findViewById(R.id.sign_up_button);

        // Set up button click listeners
        loginButton.setOnClickListener(v -> loginUser());
        forgotPasswordButton.setOnClickListener(v -> resetPassword());
        signUpButton.setOnClickListener(v -> navigateToSignUp());

        // Observe login success/failure
        loginViewModel.getLoginSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(LoginRenter.this, "Login successful", Toast.LENGTH_SHORT).show();
                navigateToMainActivity();
            }
        });

        loginViewModel.getLoginError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(LoginRenter.this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateInput(email, password)) {
            loginViewModel.loginUser(email, password);
        }
    }

    private boolean validateInput(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Please enter your email");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter your password");
            return false;
        }
        return true;
    }

    private void resetPassword() {
        // Navigate to ResetPasswordActivity (to be implemented if needed)
        Toast.makeText(this, "Password reset functionality not implemented", Toast.LENGTH_SHORT).show();
    }

    private void navigateToSignUp() {
        Intent intent = new Intent(LoginRenter.this, SignUpRenter.class);
        startActivity(intent);
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginRenter.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
