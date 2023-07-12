package com.hotshot.android.exercise.employeedirectory.service;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotshot.android.exercise.employeedirectory.Employee;
import com.hotshot.android.exercise.employeedirectory.EmployeeListAdapter;
import com.hotshot.android.exercise.employeedirectory.EmployeeType;

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

public class EmployeeService {
    OkHttpClient client;
    Handler handler;
    Context context;
    EmployeeListAdapter adapter;
    List<Employee> employeeList = new ArrayList<>();
    public static final String TAG = EmployeeService.class.getSimpleName();

    public EmployeeService(Context context, OkHttpClient client) {
        this.context = context;
        handler = new Handler(context.getMainLooper());
        this.client = client;
        this.adapter = new EmployeeListAdapter(context, employeeList);
    }

    public EmployeeListAdapter getAdapter(){
        return adapter;
    }

    public void getEmployees(String url) {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Network Call failed.", e);
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                JSONObject jsonObject = null;
                JSONArray jsonArray = null;
                try {
                    //Optimize with Jackson?
                    jsonObject = new JSONObject(response.body().string());
                    jsonArray = jsonObject.getJSONArray("employees");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject element = (JSONObject) jsonArray.get(i);
                        Employee employee = new Employee(
                                UUID.fromString(element.get("uuid").toString()),
                                element.get("full_name").toString().trim(),
                                element.get("phone_number").toString().trim(),
                                element.get("email_address").toString().trim(),
                                element.get("biography").toString().trim(),
                                element.get("photo_url_small").toString().trim(),
                                element.get("photo_url_large").toString().trim(),
                                element.get("team").toString().trim(),
                                EmployeeType.valueOf(
                                        element.get("employee_type").toString().trim()),
                                null,
                                null
                        );
                        employeeList.add(employee);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                getImages(employeeList);
            }
        });

    }

    private void getImages(List<Employee> employeeList) {
        final AtomicInteger completedCalls = new AtomicInteger(0);
        for (Employee employee : employeeList) {
            Request request = new Request.Builder()
                    .url(employee.getSmallPhotoUrl())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    Log.e(TAG, "Network Call failed.", e);
                }

                @Override public void onResponse(@NonNull Call call, @NonNull Response response)
                        throws IOException {
                    InputStream inputStream = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    employee.setSmallPhoto(bitmap);
                    completedCalls.incrementAndGet();
                    if (completedCalls.get() == employeeList.size()) {
                        Log.d(TAG, "NETWORK CALL RESPONSE: " + employeeList.toString());
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
            });
        }
    }
}