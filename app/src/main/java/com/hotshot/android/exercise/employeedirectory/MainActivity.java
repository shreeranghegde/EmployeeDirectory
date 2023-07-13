package com.hotshot.android.exercise.employeedirectory;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.hotshot.android.exercise.employeedirectory.service.EmployeeService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
                                                    EmployeeService.NetworkCallCompletedListener {
    EmployeeListAdapter adapter;
    List<Employee> employeeList = new ArrayList<>();
    RecyclerView recyclerView;
    public static final String TAG = MainActivity.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    EmployeeService employeeService;

    EmployeeDirectoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        employeeService = new EmployeeService(this);
        viewModel = new ViewModelProvider(this).get(EmployeeDirectoryViewModel.class);

        this.adapter = new EmployeeListAdapter(this, viewModel.getEmployeesLiveData().getValue());
        recyclerView = findViewById(R.id.employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(viewModel.getEmployeesLiveData().getValue().size() == 0){
            employeeService.fetchEmployees(URL);
        }


        viewModel.getEmployeesLiveData().observe(this, new Observer<List<Employee>>() {
            @Override public void onChanged(List<Employee> employeeList) {
                // DiffUtil?
                Log.d(TAG, "INSIDE LIVE DATA OBSERVER");
                adapter.setEmployeeList(employeeList);
            }
        });

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                employeeService.fetchEmployees(URL);
            }
        });

    }

    @Override public void fetchCompleted() {
        viewModel.getEmployeesLiveData().setValue(employeeService.getEmployees());
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        employeeService.onDestroy();
    }
}