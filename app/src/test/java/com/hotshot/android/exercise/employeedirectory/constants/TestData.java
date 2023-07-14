package com.hotshot.android.exercise.employeedirectory.constants;

import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.EmployeeType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TestData {
    public static final String testEmployeeId = "some-uuid";
    public static final String testFullName = "Eric Rogers";
    public static final String testPhoneNumber = "5556669870";
    public static final String testEmailAddress = "erogers.demo@squareup.com";
    public static final String testBiography = "A short biography describing the employee.";
    public static final String testPhotoUrlSmall = "https://some.url/path1.jpg";
    public static final String testPhotoUrlLarge = "https://some.url/path2.jpg";
    public static final String testTeam = "Seller";
    public static final String testEmployeeType = "FULL_TIME";

    public static final String responseBodyValidDataString = "{\n" +
            "\t\"employees\" : [\n" +
            "\t\t{\n" +
            "      \"uuid\" : \"some-uuid\",\n" +
            "\n" +
            "      \"full_name\" : \"Eric Rogers\",\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"uuid\" : \"some-uuid\",\n" +
            "\n" +
            "      \"full_name\" : \"Eric Rogers\",\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"uuid\" : \"some-uuid\",\n" +
            "\n" +
            "      \"full_name\" : \"Eric Rogers\",\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    }\n" +
            "\t]\n" +
            "}";

    public static final String responseBodyMalformedDataString = "{\n" +
            "\t\"employees\" : [\n" +
            "\t\t{\n" +
            "      \"full_name\" : \"Eric Rogers\",\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"uuid\" : \"some-uuid\",\n" +
            "\n" +
            "      \"full_name\" : \"Eric Rogers\",\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"uuid\" : \"some-uuid\",\n" +
            "\n" +
            "      \"phone_number\" : \"5556669870\",\n" +
            "      \"email_address\" : \"erogers.demo@squareup.com\",\n" +
            "      \"biography\" : \"A short biography describing the employee.\",\n" +
            "\n" +
            "      \"photo_url_small\" : \"https://some.url/path1.jpg\",\n" +
            "      \"photo_url_large\" : \"https://some.url/path2.jpg\",\n" +
            "\n" +
            "      \"team\" : \"Seller\",\n" +
            "      \"employee_type\" : \"FULL_TIME\"\n" +
            "    }\n" +
            "\t]\n" +
            "}";

    public static Employee getEmployee() {
        return new Employee(testEmployeeId, testFullName, testPhoneNumber,
                            testEmailAddress, testBiography, testPhotoUrlSmall,
                            testPhotoUrlLarge, testTeam,
                            EmployeeType.valueOf(testEmployeeType));
    }

    public static List<Employee> getEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        Employee employee1 = new Employee(testEmployeeId, testFullName, testPhoneNumber,
                                          testEmailAddress, testBiography, testPhotoUrlSmall,
                                          testPhotoUrlLarge, testTeam,
                                          EmployeeType.valueOf(testEmployeeType));

        Employee employee2 = new Employee(null, testFullName, testPhoneNumber,
                                          testEmailAddress, testBiography, testPhotoUrlSmall,
                                          testPhotoUrlLarge, testTeam,
                                          EmployeeType.valueOf(testEmployeeType));

        Employee employee3 = new Employee(testEmployeeId, testFullName, null,
                                          testEmailAddress, testBiography, testPhotoUrlSmall,
                                          testPhotoUrlLarge, testTeam,
                                          EmployeeType.valueOf(testEmployeeType));
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
        return employeeList;
    }

    public static String getResponseBodyValidDataString() {
        return responseBodyValidDataString;
    }

    public static String getResponseBodyMalformedDataString() {
        return responseBodyMalformedDataString;
    }

    public static String getJsonString() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uuid", testEmployeeId);
            jsonObject.put("full_name", testEmployeeId);
            jsonObject.put("phone_number", testEmployeeId);
            jsonObject.put("phone_number", testEmployeeId);
            jsonObject.put("biography", testEmployeeId);
            jsonObject.put("photo_url_small", testEmployeeId);
            jsonObject.put("photo_url_large", testEmployeeId);
            jsonObject.put("team", testEmployeeId);
            jsonObject.put("employee_type", testEmployeeId);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        return jsonObject.toString();
    }
}
