package com.hotshot.android.exercise.employeedirectory;

import android.graphics.Bitmap;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Add Lombok?
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee {
    private UUID employeeId;
    private String fullName;
    private String phoneNumber;
    private String emailAddress;
    private String biography;
    private String smallPhotoUrl;
    private String largePhotoUrl;
    private String team;
    private EmployeeType employeeType;
    private Bitmap smallPhoto;
    private Bitmap largePhoto;
}
