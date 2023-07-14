package com.hotshot.android.exercise.employeedirectory.types;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Employee {
    @SerializedName("uuid")
    @JsonAlias("uuid")
    private String employeeId;

    @SerializedName("full_name")
    @JsonAlias("full_name")
    private String fullName;

    @SerializedName("phone_number")
    @JsonAlias("phone_number")
    private String phoneNumber;

    @SerializedName("email_address")
    @JsonAlias("email_address")
    private String emailAddress;

    @SerializedName("biography")
    @JsonAlias("biography")
    private String biography;

    @SerializedName("photo_url_small")
    @JsonAlias("photo_url_small")
    private String smallPhotoUrl;

    @SerializedName("photo_url_large")
    @JsonAlias("photo_url_large")
    private String largePhotoUrl;

    @SerializedName("team")
    @JsonAlias("team")
    private String team;

    @SerializedName("employee_type")
    @JsonAlias("employee_type")
    private EmployeeType employeeType;

    public boolean isValid() {
        boolean mandatoryFieldsPresent =
                employeeId != null && fullName != null && emailAddress != null
                        && team != null && employeeType != null;

        boolean phoneNumberValidity = phoneNumber == null || phoneNumber.length() == 10;

        return mandatoryFieldsPresent && phoneNumberValidity;
    }
}
