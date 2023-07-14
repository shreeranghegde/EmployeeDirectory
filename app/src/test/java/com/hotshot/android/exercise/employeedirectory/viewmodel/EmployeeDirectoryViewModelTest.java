package com.hotshot.android.exercise.employeedirectory.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hotshot.android.exercise.employeedirectory.constants.TestData;
import com.hotshot.android.exercise.employeedirectory.types.Employee;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
public class EmployeeDirectoryViewModelTest {
    private EmployeeDirectoryViewModel viewModel;

    @Before
    public void setup() {
        viewModel = new EmployeeDirectoryViewModel();
    }

    @Test
    public void testEmployeeDirectoryViewModel_SanityTest() {
        MutableLiveData<List<Employee>> testEmployeeLiveData = viewModel.getEmployeesLiveData();
        Assert.assertNotNull(testEmployeeLiveData);
        Assert.assertEquals(0, testEmployeeLiveData.getValue().size());
    }

    @Test
    public void testEmployeeDirectoryViewModel_SetEmployeesLiveData() {
        List<Employee> testEmployeeList = new ArrayList<>();
        testEmployeeList.add(TestData.getEmployee());
        testEmployeeList.add(TestData.getEmployee());
        testEmployeeList.add(TestData.getEmployee());

        viewModel.setEmployeesLiveData(testEmployeeList);
        Assert.assertEquals(3, viewModel.getEmployeesLiveData().getValue().size());
        Assert.assertEquals(testEmployeeList, viewModel.getEmployeesLiveData().getValue());
    }
}
