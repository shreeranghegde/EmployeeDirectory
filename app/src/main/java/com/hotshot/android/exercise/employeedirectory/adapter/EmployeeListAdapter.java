package com.hotshot.android.exercise.employeedirectory.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.R;
import com.hotshot.android.exercise.employeedirectory.view.EmployeeDetailDialog;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter {
    public static final String TAG = EmployeeListAdapter.class.getSimpleName();
    private Context context;
    private List<Employee> employeeList;
    private Picasso picasso;
    private EmployeeDetailDialog employeeDetailDialog;

    public EmployeeListAdapter(Context context, List<Employee> employeeList,
                               EmployeeDetailDialog employeeDetailDialog) {
        this.context = context;
        this.employeeList = employeeList;
        this.employeeDetailDialog = employeeDetailDialog;
        picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        ImageView employeePhoto;
        TextView employeeFullName;
        TextView employeeTeam;
        EmployeeDetailDialog employeeDialog;
        View employeeView;

        public EmployeeViewHolder(@NonNull View view, EmployeeDetailDialog dialog,
                                  Context context) {
            super(view);
            employeeView = view;
            employeePhoto = view.findViewById(R.id.employeePhoto);
            employeeFullName = view.findViewById(R.id.employeeFullName);
            employeeTeam = view.findViewById(R.id.employeeTeam);
            employeeDialog = dialog;

            view.setOnClickListener(this);
        }

        /**
         * Open Employee Summary dialog box for the view that was clicked.
         *
         * @param v The view that was clicked.
         */
        @Override public void onClick(View v) {
            int position = getAdapterPosition();
            employeeDialog.setEmployeeIndex(position);
            employeeDialog.show();
        }
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View employeeListView = LayoutInflater.from(this.context).inflate(R.layout.employee_item,
                                                                          parent, false);
        return new EmployeeViewHolder(employeeListView, employeeDetailDialog, this.context);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmployeeViewHolder) {
            EmployeeViewHolder employeeViewHolder = (EmployeeViewHolder) holder;
            Employee employee = employeeList.get(position);
            employeeViewHolder.employeeFullName.setText(employee.getFullName());
            employeeViewHolder.employeeTeam.setText(employee.getTeam());
            picasso.load(employee.getSmallPhotoUrl()).error(R.drawable.ic_no_image).into(
                    employeeViewHolder.employeePhoto);
        }
    }

    @Override public int getItemCount() {
        return employeeList.size();
    }

    private void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void updateEmployees(List<Employee> newEmployees) {
        EmployeeDiffCallback callback = new EmployeeDiffCallback(employeeList, newEmployees);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(callback);
        setEmployeeList(newEmployees);
        result.dispatchUpdatesTo(this);
    }
}
