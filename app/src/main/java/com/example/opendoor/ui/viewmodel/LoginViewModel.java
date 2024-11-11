package com.example.opendoor.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends ViewModel {

    private final FirebaseAuth auth;
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    public LoginViewModel() {
        auth = FirebaseAuth.getInstance();
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginSuccess.setValue(true);
                    } else {
                        loginError.setValue(task.getException() != null ? task.getException().getMessage() : "Login failed");
                    }
                });
    }

    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
