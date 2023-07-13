package com.hotshot.android.exercise.employeedirectory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectoryViewModel extends ViewModel {
    private MutableLiveData<List<Employee>> employeesLiveData;

    public MutableLiveData<List<Employee>> getEmployeesLiveData() {
        if(employeesLiveData == null) {
            employeesLiveData = new MutableLiveData<>();
            employeesLiveData.setValue(new ArrayList<Employee>());
        }
        return employeesLiveData;
    }

    @Override protected void onCleared() {
        super.onCleared();
    }
}
