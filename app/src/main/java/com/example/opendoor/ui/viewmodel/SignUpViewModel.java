package com.example.opendoor.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpViewModel extends ViewModel {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;
    private final MutableLiveData<FirebaseUser> userLiveData;
    private final MutableLiveData<Boolean> signUpFailedLiveData;

    public SignUpViewModel() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userLiveData = new MutableLiveData<>();
        signUpFailedLiveData = new MutableLiveData<>();
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<Boolean> getSignUpFailedLiveData() {
        return signUpFailedLiveData;
    }

    public void signUpUser(String username, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            saveUserToFirestore(user.getUid(), username, email);
                            userLiveData.setValue(user);
                        }
                    } else {
                        signUpFailedLiveData.setValue(true);
                    }
                });
    }

    private void saveUserToFirestore(String userId, String username, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("username", username);
        user.put("email", email);

        db.collection("users").document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> userLiveData.setValue(auth.getCurrentUser()))
                .addOnFailureListener(e -> signUpFailedLiveData.setValue(true));
    }
}
