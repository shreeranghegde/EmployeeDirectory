package com.hotshot.android.exercise.employeedirectory.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hotshot.android.exercise.employeedirectory.R;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.viewmodel.EmployeeDirectoryViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class EmployeeDetailDialog extends Dialog {
    private EmployeeDirectoryViewModel viewModel;
    private int employeeIndex;
    private Employee employee;
    private ImageView employeeDialogLargePhoto;
    private TextView employeeDialogBio;
    private TextView employeeDialogName;
    private TextView employeeDialogTeam;
    private TextView employeeDialogEmail;
    public static final String TAG = EmployeeDetailDialog.class.getSimpleName();

    public EmployeeDetailDialog(@NonNull Context context, EmployeeDirectoryViewModel viewModel) {
        super(context, R.style.roundedCorner1);
        this.viewModel = viewModel;
    }

    public int getEmployeeIndex() {
        return employeeIndex;
    }

    /**
     * Set the index of the selected employee.
     *
     * @param employeeIndex index of selected employee
     */
    public void setEmployeeIndex(int employeeIndex) {
        this.employeeIndex = employeeIndex;
        if (viewModel.getEmployeesLiveData() != null) {
            employee = Objects.requireNonNull(
                    viewModel.getEmployeesLiveData().getValue()).get(employeeIndex);
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employee_dialog);
        setCanceledOnTouchOutside(true);
    }

    @Override protected void onStart() {
        super.onStart();
        employeeDialogLargePhoto = findViewById(R.id.employeeDialogLargePhoto);
        employeeDialogBio = findViewById(R.id.employeeDialogBio);
        employeeDialogName = findViewById(R.id.employeeDialogName);
        employeeDialogTeam = findViewById(R.id.employeeDialogTeam);
        employeeDialogEmail = findViewById(R.id.employeeDialogEmail);

        Log.d(TAG, employee.toString());

        if (employee.getBiography() == null) {
            employeeDialogBio.setVisibility(View.GONE);
        }
        employeeDialogBio.setText(employee.getBiography());
        employeeDialogName.setText(employee.getFullName());
        employeeDialogTeam.setText(employee.getTeam());
        employeeDialogEmail.setText(employee.getEmailAddress());
        Picasso.get().load(employee.getLargePhotoUrl()).error(R.drawable.ic_no_image).into(
                employeeDialogLargePhoto);

    }
}
