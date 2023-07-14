package com.hotshot.android.exercise.employeedirectory.service;

import static com.hotshot.android.exercise.employeedirectory.constants.Network.URL;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.types.EmployeeType;
import com.hotshot.android.exercise.employeedirectory.networking.NetworkingClient;
import com.hotshot.android.exercise.employeedirectory.types.ResponseType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
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
    Gson gson;

    public EmployeeService(Context context) {
        this.context = context;
        gson = new Gson();
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
            listener.fetchCompleted(ResponseType.EMPTY);
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
                    jsonObject = new JSONObject(response.body().string());
                    jsonArray = jsonObject.getJSONArray("employees");
                    ObjectMapper objectMapper = new ObjectMapper();
                    employeeList = objectMapper.readValue(String.valueOf(jsonArray),
                                                          new TypeReference<List<Employee>>(){});
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override public void run() {
                        ResponseType responseType = ResponseType.VALID;
                        if (listener != null) {
                            Log.d(TAG, employeeList.toString());
                            if(employeeList.size() == 0) {
                                responseType = ResponseType.EMPTY;
                            } else if(employeeList.stream().anyMatch(employee -> !employee.isValid())) {
                                responseType = ResponseType.MALFORMED;
                            }
                            listener.fetchCompleted(responseType);
                        }
                    }
                });
            }
        });
    }

    public interface NetworkCallCompletedListener {
        public void fetchCompleted(ResponseType responseType);
    }

    public void onDestroy() {
        listener = null;
    }
}
