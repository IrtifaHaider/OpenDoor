package com.example.swlab6.viewmodel.DivisionViewModel.java;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.swlab6.DivisionCalculatation;

public class DivisionViewModel extends ViewModel {

    private final MutableLiveData<Double> resultLiveData = new MutableLiveData<>();

    public LiveData<Double> getResultLiveData() {
        return resultLiveData;
    }

    // Method to perform the division logic and update LiveData with the result
    public void performDivision(double numerator, double denominator, Double multiplier) {
        try {
            DivisionCalculatation divisionCalculatation = new DivisionCalculatation(numerator, denominator);
            double result;

            if (multiplier != null) {
                result = divisionCalculatation.divide(numerator, denominator, multiplier);
            } else {
                result = divisionCalculatation.divide();
            }

            resultLiveData.setValue(result);
        } catch (ArithmeticException e) {
            resultLiveData.setValue(null);  // Error handling (e.g., division by zero)
        }
    }
}
