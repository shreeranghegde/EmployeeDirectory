package com.hotshot.android.exercise.employeedirectory.types;

import com.fasterxml.jackson.annotation.JsonAlias;

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
    @JsonAlias("uuid")
    private String employeeId;

    @JsonAlias("full_name")
    private String fullName;

    @JsonAlias("phone_number")
    private String phoneNumber;

    @JsonAlias("email_address")
    private String emailAddress;

    @JsonAlias("biography")
    private String biography;

    @JsonAlias("photo_url_small")
    private String smallPhotoUrl;

    @JsonAlias("photo_url_large")
    private String largePhotoUrl;

    @JsonAlias("team")
    private String team;

    @JsonAlias("employee_type")
    private EmployeeType employeeType;

    /**
     * @return True if mandatory fields are present
     */
    public boolean isValid() {
        boolean mandatoryFieldsPresent =
                employeeId != null && fullName != null && emailAddress != null
                        && team != null && employeeType != null;

        boolean phoneNumberValidity = phoneNumber == null || phoneNumber.length() == 10;

        return mandatoryFieldsPresent && phoneNumberValidity;
    }
}
