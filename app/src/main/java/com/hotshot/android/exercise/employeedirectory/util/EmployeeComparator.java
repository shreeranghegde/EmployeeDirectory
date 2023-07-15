package com.hotshot.android.exercise.employeedirectory.util;

import com.hotshot.android.exercise.employeedirectory.types.Employee;

import java.util.Comparator;

public class EmployeeComparator {
    public static Comparator<Employee> getEmployeeComparator() {
        return Comparator.comparing(Employee::getTeam)
                .thenComparing(Employee::getFullName);
    }
}
