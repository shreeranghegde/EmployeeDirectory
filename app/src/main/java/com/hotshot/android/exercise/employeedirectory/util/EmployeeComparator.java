package com.hotshot.android.exercise.employeedirectory.util;

import com.hotshot.android.exercise.employeedirectory.types.Employee;

import java.util.Comparator;

public class EmployeeComparator {
    /**
     * Compare based on Team name first and then on Full Name of Employee.
     *
     * @return Comparator for comparing tw values on defined parameters.
     */
    public static Comparator<Employee> getEmployeeComparator() {
        return Comparator.comparing(Employee::getTeam)
                .thenComparing(Employee::getFullName);
    }
}
