package com.hotshot.android.exercise.employeedirectory.service;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.EmployeeType;
import com.hotshot.android.exercise.employeedirectory.networking.NetworkingClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmployeeService {
    OkHttpClient client;
    Context context;

    List<Employee> employeeList = new ArrayList<>();
    NetworkCallCompletedListener listener;
    public static final String TAG = EmployeeService.class.getSimpleName();

    public EmployeeService(Context context) {
        this.context = context;
        this.client = NetworkingClient.getInstance();
        ;
        if (context instanceof NetworkCallCompletedListener) {
            this.listener = (NetworkCallCompletedListener) context;
        }
    }

    public List<Employee> getEmployees() {
        return employeeList;
    }

    public void fetchEmployees(String url) {
        if (url == null) {
            Log.d(TAG, "INSIDE EMPTY URL");
            listener.fetchCompleted();
            return;
        }
        Log.d(TAG, "MAKING NETWORK CALL: " + url);
        Request request = new Request.Builder()
                .url(url)
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
                        Log.d(TAG, element.toString());
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
                                        element.get("employee_type").toString().trim())
                        );
                        employeeList.add(employee);

                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override public void run() {
                        if (listener != null) {
                            Log.d(TAG, employeeList.toString());
                            listener.fetchCompleted();
                        }
                    }
                });
            }
        });
    }

    public interface NetworkCallCompletedListener {
        public void fetchCompleted();
    }

    public void onDestroy() {
        listener = null;
    }
}
