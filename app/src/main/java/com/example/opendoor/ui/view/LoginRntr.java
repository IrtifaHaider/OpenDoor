package com.example.opendoor.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opendoor.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRntr extends AppCompatActivity {

    private TextInputEditText usernameEditText, passwordEditText;
    private Button loginButton, forgotPasswordButton, signUpButton, cancelButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_rntr);

        initializeFirebaseAuth();
        bindUIComponents();
        setClickListeners();
    }

    // Initializes Firebase Authentication
    private void initializeFirebaseAuth() {
        auth = FirebaseAuth.getInstance();
    }

    // Binds UI components to class variables
    private void bindUIComponents() {
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordButton = findViewById(R.id.forgot_password_button);
        signUpButton = findViewById(R.id.sign_up_button);
        cancelButton = findViewById(R.id.cancel_button); // Added Cancel button
    }

    // Sets click listeners for buttons
    private void setClickListeners() {
        loginButton.setOnClickListener(v -> loginUser());
        forgotPasswordButton.setOnClickListener(v -> resetPassword());
        signUpButton.setOnClickListener(v -> navigateToSignUp());
        cancelButton.setOnClickListener(v -> finish()); // Close activity on Cancel
    }

    // Handles user login
    private void loginUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (validateInput(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginRntr.this, "Login successful", Toast.LENGTH_SHORT).show();
                            navigateToMainActivity();
                        } else {
                            Log.w("LoginRntr", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginRntr.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Validates email and password fields
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

    // Resets password via Firebase
    private void resetPassword() {
        String email = usernameEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Enter your registered email");
            return;
        }

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginRntr.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginRntr.this, "Failed to send reset email", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Navigates to the Sign-Up activity
    private void navigateToSignUp() {
        Intent intent = new Intent(LoginRntr.this, SignUpActivity.class);
        startActivity(intent);
    }

    // Navigates to the Main activity after successful login
    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginRntr.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
