package com.hotshot.android.exercise.employeedirectory.activity;

import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.DEFAULT_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.EMPTY_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.MALFORMED_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_SUBTITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.ErrorStates.NETWORK_ERROR_DATA_TITLE;
import static com.hotshot.android.exercise.employeedirectory.constants.Network.EMPTY_DATA_URL;
import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
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

public class MainActivity extends AppCompatActivity implements
                                                    EmployeeService.NetworkCallCompletedListener,
                                                    EmployeeListAdapter.EmployeeViewHolder.EmployeeClickListener,
                                                    DialogInterface.OnDismissListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private EmployeeListAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EmployeeService employeeService;

    private EmployeeDirectoryViewModel viewModel;
    private View emptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize components
        employeeService = new EmployeeService(this);
        viewModel = new ViewModelProvider(this).get(EmployeeDirectoryViewModel.class);
        EmployeeDetailDialog employeeDetailDialog = new EmployeeDetailDialog(this, viewModel);
        adapter = new EmployeeListAdapter(this, viewModel.getEmployeesLiveData().getValue(),
                                          employeeDetailDialog);

        recyclerView = findViewById(R.id.employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Fetch data only if necessary. Avoid fetching in case of orientation changes.
        if (viewModel.getEmployeesLiveData().getValue().size() == 0) {
            employeeService.fetchEmployees(EMPTY_DATA_URL);
        } else {
            removeProgressBar();
        }

        handleSwipeRefresh();
        handleEmployeeLiveDataChanges();
    }


    private void handleEmployeeLiveDataChanges() {
        viewModel.getEmployeesLiveData().observe(this, new Observer<List<Employee>>() {
            @Override public void onChanged(List<Employee> employeeList) {
                // DiffUtil?
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

    @Override public void onEmployeeClicked(int position) {
        // Make content of non-selected employees invisible before making dialog box visible
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            if(i == position) {
                continue;
            }
            View itemView = recyclerView.getChildAt(i);
            View itemRow = itemView.findViewById(R.id.employeeItemRow);
            itemRow.setVisibility(View.INVISIBLE);
        }
    }

    @Override public void onDismiss(DialogInterface dialog) {
        // Reset invisible employees content after dialog box is dismissed
        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View itemView = recyclerView.getChildAt(i);
            View itemRow = itemView.findViewById(R.id.employeeItemRow);
            itemRow.setVisibility(View.VISIBLE);
        }
        dialog.dismiss();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        employeeService.onDestroy();
        viewModel.getEmployeesLiveData().removeObservers(this);
    }
}