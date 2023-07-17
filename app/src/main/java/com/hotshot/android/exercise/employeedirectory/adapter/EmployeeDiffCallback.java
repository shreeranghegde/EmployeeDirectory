package com.hotshot.android.exercise.employeedirectory.adapter;

import android.util.Log;

import androidx.recyclerview.widget.DiffUtil;

import com.hotshot.android.exercise.employeedirectory.types.Employee;

import java.util.List;

public class EmployeeDiffCallback extends DiffUtil.Callback {
    public static final String TAG = EmployeeDiffCallback.class.getSimpleName();
    private List<Employee> oldEmployees;
    private List<Employee> newEmployees;

    public EmployeeDiffCallback(List<Employee> oldEmployees, List<Employee> newEmployees) {
        this.oldEmployees = oldEmployees;
        this.newEmployees = newEmployees;
    }

    @Override public int getOldListSize() {
        return oldEmployees.size();
    }

    @Override public int getNewListSize() {
        return newEmployees.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Employee oldEmployee = oldEmployees.get(oldItemPosition);
        Employee newEmployee = newEmployees.get(newItemPosition);
        return oldEmployee.getEmployeeId() == newEmployee.getEmployeeId();
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Employee oldEmployee = oldEmployees.get(oldItemPosition);
        Employee newEmployee = newEmployees.get(newItemPosition);
        return oldEmployee.equals(newEmployee);
    }
}
