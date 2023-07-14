package com.hotshot.android.exercise.employeedirectory.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hotshot.android.exercise.employeedirectory.types.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectoryViewModel extends ViewModel {
    private MutableLiveData<List<Employee>> employeesLiveData;

    public EmployeeDirectoryViewModel() {
        this.employeesLiveData = new MutableLiveData<>();
        this.employeesLiveData.setValue(new ArrayList<>());
    }

    public MutableLiveData<List<Employee>> getEmployeesLiveData() {
        return employeesLiveData;
    }

    public void setEmployeesLiveData(List<Employee> employeeList) {
        employeesLiveData.setValue(employeeList);
    }
}
