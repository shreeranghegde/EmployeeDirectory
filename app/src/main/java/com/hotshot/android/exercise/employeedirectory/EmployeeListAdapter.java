package com.hotshot.android.exercise.employeedirectory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter {
    private Context context;
    List<Employee> employeeList = new ArrayList<>();
    public static final String TAG = EmployeeListAdapter.class.getSimpleName();
    private Picasso picasso;

    public EmployeeListAdapter(Context context, List<Employee> employeeList) {
        this.context = context;
        this.employeeList = employeeList;
        picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ImageView employeePhoto;
        TextView employeeFullName;
        TextView employeeTeam;

        public EmployeeViewHolder(@NonNull View view) {
            super(view);
            employeePhoto = view.findViewById(R.id.employeePhoto);
            employeeFullName = view.findViewById(R.id.employeeFullName);
            employeeTeam = view.findViewById(R.id.employeeTeam);
        }
    }

    @NonNull @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View employeeListView = LayoutInflater.from(this.context).inflate(R.layout.employee_item,
                                                                          parent, false);

        return new EmployeeViewHolder(employeeListView);
    }

    @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        Log.d(TAG, employeeList.get(position).toString());
        if (holder instanceof EmployeeViewHolder) {
            EmployeeViewHolder employeeViewHolder = (EmployeeViewHolder) holder;
            Employee employee = employeeList.get(position);
            employeeViewHolder.employeeFullName.setText(employee.getFullName());
            employeeViewHolder.employeeTeam.setText(employee.getTeam());

            picasso.load(employee.getSmallPhotoUrl()).into(employeeViewHolder.employeePhoto);
        }
    }

    @Override public int getItemCount() {
        return employeeList.size();
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        notifyDataSetChanged();
    }
}
