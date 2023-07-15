package com.hotshot.android.exercise.employeedirectory.activity;

import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.Network.MALFORMED_DATA_URL;
import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hotshot.android.exercise.employeedirectory.R;
import com.hotshot.android.exercise.employeedirectory.adapter.EmployeeListAdapter;
import com.hotshot.android.exercise.employeedirectory.service.EmployeeService;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.ResponseType;
import com.hotshot.android.exercise.employeedirectory.viewmodel.EmployeeDirectoryViewModel;

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
    View rootContainer;
    View emptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootContainer = findViewById(R.id.rootContainer);

        employeeService = new EmployeeService(this);
        viewModel = new ViewModelProvider(this).get(EmployeeDirectoryViewModel.class);

        this.adapter = new EmployeeListAdapter(this, viewModel.getEmployeesLiveData().getValue());
        recyclerView = findViewById(R.id.employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (viewModel.getEmployeesLiveData().getValue().size() == 0) {
            employeeService.fetchEmployees(URL);
        }

        handleSwipeRefresh();
        handleEmployeeLiveDataChanges();
    }

    private void handleEmployeeLiveDataChanges() {
        viewModel.getEmployeesLiveData().observe(this, new Observer<List<Employee>>() {
            @Override public void onChanged(List<Employee> employeeList) {
                // DiffUtil?
                Log.d(TAG, "INSIDE LIVE DATA OBSERVER: " + employeeList.toString());
                adapter.setEmployeeList(employeeList);
            }
        });
    }

    private void handleSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                if (emptyStateView != null) {
                    emptyStateView.setVisibility(View.GONE);
                }
                employeeService.fetchEmployees(URL);
            }
        });
    }

    @Override public void fetchCompleted(ResponseType responseType) {
        List<Employee> employees = employeeService.getEmployees();
        employees.sort((employee) -> (employee.getTeam(), employee.getName()));
        viewModel.setEmployeesLiveData(employeeService.getEmployees());
        swipeRefreshLayout.setRefreshing(false);

        if (responseType != ResponseType.VALID) {
            TextView errorTitle = findViewById(R.id.errorStateTitle);
            TextView errorSubTitle = findViewById(R.id.errorStateSubtitle);
            Log.d(TAG, responseType.toString());
            switch (responseType) {
                case EMPTY:
                    errorTitle.setText(EMPTY_DATA_TITLE);
                    errorSubTitle.setText(EMPTY_DATA_SUBTITLE);
                    break;
                case MALFORMED:
                    errorTitle.setText(MALFORMED_DATA_TITLE);
                    errorSubTitle.setText(MALFORMED_DATA_SUBTITLE);
                    break;
                case NETWORK_ERROR:
                    errorTitle.setText(NETWORK_ERROR_DATA_TITLE);
                    errorSubTitle.setText(NETWORK_ERROR_DATA_SUBTITLE);
                    break;
                default:
                    errorTitle.setText(DEFAULT_DATA_TITLE);
                    errorSubTitle.setText(DEFAULT_DATA_SUBTITLE);
            }

            emptyStateView = findViewById(R.id.errorStateView);
            emptyStateView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            if (emptyStateView != null) {
                emptyStateView.setVisibility(View.GONE);
            }
            if (recyclerView.getVisibility() == View.GONE) {
                recyclerView.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        employeeService.onDestroy();
        viewModel.getEmployeesLiveData().removeObservers(this);
    }
}