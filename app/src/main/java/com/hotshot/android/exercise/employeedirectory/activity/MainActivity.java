package com.hotshot.android.exercise.employeedirectory.activity;

import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hotshot.android.exercise.employeedirectory.R;
import com.hotshot.android.exercise.employeedirectory.adapter.EmployeeListAdapter;
import com.hotshot.android.exercise.employeedirectory.service.EmployeeService;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.ResponseType;
import com.hotshot.android.exercise.employeedirectory.util.EmployeeComparator;
import com.hotshot.android.exercise.employeedirectory.view.EmployeeDetailDialog;
import com.hotshot.android.exercise.employeedirectory.viewmodel.EmployeeDirectoryViewModel;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements
                                                    EmployeeService.NetworkCallCompletedListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private EmployeeListAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmployeeService employeeService;

    private EmployeeDirectoryViewModel viewModel;
    private View emptyStateView;
    private EmployeeDetailDialog employeeDetailDialog;
    private boolean isDialogShowing = false;
    public static final String IS_DIALOG_SHOWING = "isDialogShowing";
    public static final String EMPLOYEE_DIALOG_POSITION = "employeeDialogPosition";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        recyclerView = findViewById(R.id.employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employeeService = new EmployeeService(this);
        viewModel = new ViewModelProvider(this).get(EmployeeDirectoryViewModel.class);
        employeeDetailDialog = new EmployeeDetailDialog(this, viewModel);
        adapter = new EmployeeListAdapter(this, viewModel.getEmployeesLiveData().getValue(),
                                          employeeDetailDialog);

        // Maintain Dialog box state when orientation changes
        if (savedInstanceState != null && savedInstanceState.containsKey(IS_DIALOG_SHOWING) &&
                savedInstanceState.getBoolean(IS_DIALOG_SHOWING)) {
            Log.i(TAG, "Retrieving active dialog state.");
            employeeDetailDialog.setEmployeeIndex(
                    savedInstanceState.getInt(EMPLOYEE_DIALOG_POSITION));
            isDialogShowing = savedInstanceState.getBoolean(IS_DIALOG_SHOWING);
            employeeDetailDialog.show();
        }
        recyclerView.setAdapter(adapter);

        // Fetch data only if necessary. Avoid fetching in case of orientation changes.
        if (Objects.requireNonNull(viewModel.getEmployeesLiveData().getValue()).size() == 0) {
            employeeService.fetchEmployees(URL);
        } else {
            removeProgressBar();
        }

        handleSwipeRefresh();
        handleEmployeeLiveDataChanges();
    }


    private void handleEmployeeLiveDataChanges() {
        viewModel.getEmployeesLiveData().observe(this, new Observer<List<Employee>>() {
            @Override public void onChanged(List<Employee> employeeList) {
                Log.i(TAG, "Setting data in the RecycleView.");
                adapter.updateEmployees(employeeList);
            }
        });
    }

    private void handleSwipeRefresh() {
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                Log.i(TAG, "Refreshing Employee data.");
                if (emptyStateView != null) {
                    emptyStateView.setVisibility(View.GONE);
                }
                employeeService.fetchEmployees(URL);
            }
        });
    }

    @Override public void fetchCompleted(ResponseType responseType) {
        removeProgressBar();
        swipeRefreshLayout.setRefreshing(false);

        if (responseType != ResponseType.VALID) {
            TextView errorTitle = findViewById(R.id.errorStateTitle);
            TextView errorSubTitle = findViewById(R.id.errorStateSubtitle);
            setErrorStateUi(responseType, errorTitle, errorSubTitle);
        } else {
            List<Employee> employees = employeeService.getEmployees();
            employees.sort(EmployeeComparator.getEmployeeComparator());
            viewModel.setEmployeesLiveData(employees);
            setValidStateUi();
        }
    }

    private void setValidStateUi() {
        if (emptyStateView != null) {
            emptyStateView.setVisibility(View.GONE);
        }
        if (recyclerView.getVisibility() == View.GONE) {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void setErrorStateUi(ResponseType responseType, TextView errorTitle,
                                 TextView errorSubTitle) {
        // Set different UI state for response types like empty, malformed, or network error
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
    }

    private void removeProgressBar() {
        ProgressBar bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.GONE);
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        // Saving dialog state
        isDialogShowing = employeeDetailDialog.isShowing();
        outState.putBoolean(IS_DIALOG_SHOWING, isDialogShowing);
        if (isDialogShowing) {
            outState.putInt(EMPLOYEE_DIALOG_POSITION, employeeDetailDialog.getEmployeeIndex());
        }

        super.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        employeeService.onDestroy();
        viewModel.getEmployeesLiveData().removeObservers(this);
        employeeDetailDialog.dismiss();
    }
}