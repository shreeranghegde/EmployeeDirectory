package com.hotshot.android.exercise.employeedirectory.types;

import org.junit.Assert;
import org.junit.Test;

public class EmployeeTest {
    public static final String testEmployeeId = "some-uuid";
    public static final String testFullName = "Eric Rogers";
    public static final String testPhoneNumber = "5556669870";
    public static final String testEmailAddress = "erogers.demo@squareup.com";
    public static final String testBiography = "A short biography describing the employee.";
    public static final String testPhotoUrlSmall = "https://some.url/path1.jpg";
    public static final String testPhotoUrlLarge = "https://some.url/path2.jpg";
    public static final String testTeam = "Seller";
    public static final String testEmployeeType = "FULL_TIME";

    @Test
    public void testEmployee_AllArgsConstructor() {
        Employee actualEmployee = new Employee(testEmployeeId, testFullName, testPhoneNumber,
                                               testEmailAddress, testBiography, testPhotoUrlSmall,
                                               testPhotoUrlLarge, testTeam,
                                               EmployeeType.valueOf(testEmployeeType));

        Assert.assertNotNull(actualEmployee);
        validateValidEmployee(actualEmployee);
    }

    @Test
    public void testEmployee_NoArgsConstructor() {
        Employee actualEmployee = new Employee();
        Assert.assertNotNull(actualEmployee);
    }

    @Test
    public void testEmployee_Setters() {
        Employee actualEmployee = new Employee();
        actualEmployee.setEmployeeId(testEmployeeId);
        actualEmployee.setFullName(testFullName);
        actualEmployee.setPhoneNumber(testPhoneNumber);
        actualEmployee.setEmailAddress(testEmailAddress);
        actualEmployee.setBiography(testBiography);
        actualEmployee.setSmallPhotoUrl(testPhotoUrlSmall);
        actualEmployee.setLargePhotoUrl(testPhotoUrlLarge);
        actualEmployee.setTeam(testTeam);
        actualEmployee.setEmployeeType(EmployeeType.valueOf(testEmployeeType));

        validateValidEmployee(actualEmployee);
    }

    @Test
    public void testEmployee_Builder() {
        Employee actualEmployee = Employee.builder()
                .employeeId(testEmployeeId)
                .fullName(testFullName)
                .phoneNumber(testPhoneNumber)
                .emailAddress(testEmailAddress)
                .biography(testBiography)
                .smallPhotoUrl(testPhotoUrlSmall)
                .largePhotoUrl(testPhotoUrlLarge)
                .team(testTeam)
                .employeeType(EmployeeType.valueOf(testEmployeeType))
                .build();

        validateValidEmployee(actualEmployee);
    }

    @Test
    public void testEmployee_IsValidAllFields() {
        Employee actualEmployee = new Employee(testEmployeeId, testFullName, testPhoneNumber,
                                               testEmailAddress, testBiography, testPhotoUrlSmall,
                                               testPhotoUrlLarge, testTeam,
                                               EmployeeType.valueOf(testEmployeeType));

        Assert.assertTrue(actualEmployee.isValid());
    }

    @Test
    public void testEmployee_IsValidMandatoryFields() {
        Employee actualEmployee = Employee.builder()
                .employeeId(testEmployeeId)
                .fullName(testFullName)
                .emailAddress(testEmailAddress)
                .team(testTeam)
                .employeeType(EmployeeType.valueOf(testEmployeeType))
                .build();

        Assert.assertTrue(actualEmployee.isValid());
    }

    @Test
    public void testEmployee_IsValidMandatoryFields_WithPhoneNumber() {
        Employee actualEmployee = Employee.builder()
                .employeeId(testEmployeeId)
                .fullName(testFullName)
                .phoneNumber(testPhoneNumber)
                .emailAddress(testEmailAddress)
                .team(testTeam)
                .employeeType(EmployeeType.valueOf(testEmployeeType))
                .build();

        Assert.assertTrue(actualEmployee.isValid());
    }

    @Test
    public void testEmployee_IsValidMandatoryFields_WithInvalidPhoneNumber() {
        Employee actualEmployee = Employee.builder()
                .employeeId(testEmployeeId)
                .fullName(testFullName)
                .phoneNumber("12345")
                .emailAddress(testEmailAddress)
                .team(testTeam)
                .employeeType(EmployeeType.valueOf(testEmployeeType))
                .build();

        Assert.assertFalse(actualEmployee.isValid());
    }

    @Test
    public void testEmployee_IsValidMandatoryFields_WithMandatoryFieldMissing() {
        Employee actualEmployee = Employee.builder()
                .fullName(testFullName)
                .emailAddress(testEmailAddress)
                .team(testTeam)
                .employeeType(EmployeeType.valueOf(testEmployeeType))
                .build();

        Assert.assertFalse(actualEmployee.isValid());
    }

    private void validateValidEmployee(Employee actualEmployee) {
        Assert.assertEquals(testEmployeeId, actualEmployee.getEmployeeId());
        Assert.assertEquals(testFullName, actualEmployee.getFullName());
        Assert.assertEquals(testPhoneNumber, actualEmployee.getPhoneNumber());
        Assert.assertEquals(testEmailAddress, actualEmployee.getEmailAddress());
        Assert.assertEquals(testBiography, actualEmployee.getBiography());
        Assert.assertEquals(testPhotoUrlSmall, actualEmployee.getSmallPhotoUrl());
        Assert.assertEquals(testPhotoUrlLarge, actualEmployee.getLargePhotoUrl());
        Assert.assertEquals(testTeam, actualEmployee.getTeam());
        Assert.assertEquals(EmployeeType.valueOf(testEmployeeType),
                            actualEmployee.getEmployeeType());

    }
}
