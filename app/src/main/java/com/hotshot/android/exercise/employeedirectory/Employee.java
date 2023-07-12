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

//    public Employee(UUID employeeId, String fullName, String phoneNumber, String emailAddress,
//                    String biography, String smallPhotoUrl, String largePhotoUrl, String team,
//                    EmployeeType employeeType, Bitmap smallPhoto, Bitmap largePhoto) {
//        this.employeeId = employeeId;
//        this.fullName = fullName;
//        this.phoneNumber = phoneNumber;
//        this.emailAddress = emailAddress;
//        this.biography = biography;
//        this.smallPhotoUrl = smallPhotoUrl;
//        this.largePhotoUrl = largePhotoUrl;
//        this.team = team;
//        this.employeeType = employeeType;
//        this.smallPhoto = smallPhoto;
//        this.largePhoto = largePhoto;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public String getSmallPhotoUrl() {
//        return smallPhotoUrl;
//    }
//
//    public String getTeam() {
//        return team;
//    }
//
//    public UUID getEmployeeId() {
//        return employeeId;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//
//    public String getEmailAddress() {
//        return emailAddress;
//    }
//
//    public String getBiography() {
//        return biography;
//    }
//
//    public String getLargePhotoUrl() {
//        return largePhotoUrl;
//    }
//
//    public EmployeeType getEmployeeType() {
//        return employeeType;
//    }
//
//    public Bitmap getSmallPhoto() {
//        return smallPhoto;
//    }
//
//    public Bitmap getLargePhoto() {
//        return largePhoto;
//    }
//
//    public void setEmployeeId(UUID employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public void setPhoneNumber(String phoneNumber) {
//        this.phoneNumber = phoneNumber;
//    }
//
//    public void setEmailAddress(String emailAddress) {
//        this.emailAddress = emailAddress;
//    }
//
//    public void setBiography(String biography) {
//        this.biography = biography;
//    }
//
//    public void setSmallPhotoUrl(String smallPhotoUrl) {
//        this.smallPhotoUrl = smallPhotoUrl;
//    }
//
//    public void setLargePhotoUrl(String largePhotoUrl) {
//        this.largePhotoUrl = largePhotoUrl;
//    }
//
//    public void setTeam(String team) {
//        this.team = team;
//    }
//
//    public void setEmployeeType(EmployeeType employeeType) {
//        this.employeeType = employeeType;
//    }
//
//    public void setSmallPhoto(Bitmap smallPhoto) {
//        this.smallPhoto = smallPhoto;
//    }
//
//    public void setLargePhoto(Bitmap largePhoto) {
//        this.largePhoto = largePhoto;
//    }
//
//    @Override public String toString() {
//        return "Employee{" +
//                "employeeId=" + employeeId +
//                ", fullName='" + fullName + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", emailAddress='" + emailAddress + '\'' +
//                ", biography='" + biography + '\'' +
//                ", smallPhotoUrl='" + smallPhotoUrl + '\'' +
//                ", largePhotoUrl='" + largePhotoUrl + '\'' +
//                ", team='" + team + '\'' +
//                ", employeeType=" + employeeType +
//                ", smallPhoto=" + smallPhoto +
//                ", largePhoto=" + largePhoto +
//                '}';
//    }
}
