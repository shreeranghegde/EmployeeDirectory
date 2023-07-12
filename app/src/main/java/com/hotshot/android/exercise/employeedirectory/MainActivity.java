package com.hotshot.android.exercise.employeedirectory;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotshot.android.exercise.employeedirectory.networking.NetworkingClient;
import com.hotshot.android.exercise.employeedirectory.service.EmployeeService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements
                                                    EmployeeService.NetworkCallCompletedListener {
    EmployeeListAdapter adapter;
    List<Employee> employeeList = new ArrayList<>();
    RecyclerView recyclerView;
    public static final String TAG = MainActivity.class.getSimpleName();
    private OkHttpClient okHttpClient;
    private SwipeRefreshLayout swipeRefreshLayout;

    EmployeeService employeeService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        okHttpClient = NetworkingClient.getInstance();

        employeeService = new EmployeeService(this, okHttpClient);
        recyclerView = findViewById(R.id.employeeList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(employeeService.getAdapter());


        employeeService.getEmployees(URL);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                employeeService.getEmployees(URL);
            }
        });

    }

    @Override public void fetchCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }
}