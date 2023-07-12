package com.hotshot.android.exercise.employeedirectory;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MainActivity extends AppCompatActivity {
    EmployeeListAdapter adapter;
    List<Employee> employeeList = new ArrayList<>();
    RecyclerView recyclerView;
    public static final String TAG = MainActivity.class.getSimpleName();
    private OkHttpClient okHttpClient;

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
    }

//    private void getEmployees() {
//
//        Request request = new Request.Builder()
//                .url(URL)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                Log.e(TAG, "Network Call failed.", e);
//            }
//
//            @Override public void onResponse(@NonNull Call call, @NonNull Response response)
//                    throws IOException {
//                List<Employee> employees = new ArrayList<>();
//                ObjectMapper objectMapper = new ObjectMapper();
//                JSONObject jsonObject= null;
//                JSONArray jsonArray = null;
//                try {
//                    //Optimize with Jackson?
//                    jsonObject = new JSONObject(response.body().string());
//                    jsonArray = jsonObject.getJSONArray("employees");
//                    for(int i = 0; i<jsonArray.length(); i++) {
//                        JSONObject element = (JSONObject) jsonArray.get(i);
//                        Employee employee = new Employee(
//                                UUID.fromString(element.get("uuid").toString()),
//                                element.get("full_name").toString().trim(),
//                                element.get("phone_number").toString().trim(),
//                                element.get("email_address").toString().trim(),
//                                element.get("biography").toString().trim(),
//                                element.get("photo_url_small").toString().trim(),
//                                element.get("photo_url_large").toString().trim(),
//                                element.get("team").toString().trim(),
//                                EmployeeType.valueOf(element.get("employee_type").toString().trim()),
//                                null,
//                                null
//                        );
//                        employeeList.add(employee);
//
//                    }
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//
//                getImages();
//            }
//        });
//    }
//
//    private void getImages() {
//        final AtomicInteger completedCalls = new AtomicInteger(0);
//        for (Employee employee: employeeList) {
//            Request request = new Request.Builder()
//                    .url(employee.getSmallPhotoUrl())
//                    .build();
//
//            okHttpClient.newCall(request).enqueue(new Callback() {
//                @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    Log.e(TAG, "Network Call failed.", e);
//                }
//
//                @Override public void onResponse(@NonNull Call call, @NonNull Response response)
//                        throws IOException {
//                    InputStream inputStream = response.body().byteStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    employee.setSmallPhoto(bitmap);
//                    completedCalls.incrementAndGet();
//                    if(completedCalls.get() == employeeList.size()){
//                        runOnUiThread(new Runnable() {
//                            @Override public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }
//
//    private void getImages() {
//        final AtomicInteger completedCalls = new AtomicInteger(0);
//        for (Employee employee: employeeList) {
//            Request request = new Request.Builder()
//                    .url(employee.getSmallPhotoUrl())
//                    .build();
//
//            okHttpClient.newCall(request).enqueue(new Callback() {
//                @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    Log.e(TAG, "Network Call failed.", e);
//                }
//
//                @Override public void onResponse(@NonNull Call call, @NonNull Response response)
//                        throws IOException {
//                    InputStream inputStream = response.body().byteStream();
//                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    employee.setSmallPhoto(bitmap);
//                    completedCalls.incrementAndGet();
//                    if(completedCalls.get() == employeeList.size()){
//                        runOnUiThread(new Runnable() {
//                            @Override public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                }
//            });
//        }
//    }
}