package com.hotshot.android.exercise.employeedirectory.service;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotshot.android.exercise.employeedirectory.types.Employee;
import com.hotshot.android.exercise.employeedirectory.networking.NetworkingClient;
import com.hotshot.android.exercise.employeedirectory.types.ResponseType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EmployeeService {
    public static final String TAG = EmployeeService.class.getSimpleName();
    private Context context;
    private List<Employee> employeeList = new ArrayList<>();
    @VisibleForTesting
    OkHttpClient client;
    @VisibleForTesting
    NetworkCallCompletedListener listener;

    public EmployeeService(Context context) {
        this.context = context;
        this.client = NetworkingClient.getInstance();

        if (context instanceof NetworkCallCompletedListener) {
            this.listener = (NetworkCallCompletedListener) context;
        }
    }

    /**
     * Returns list of employees retrieved from the remote source.
     *
     * @return list of employees
     */
    public List<Employee> getEmployees() {
        return employeeList;
    }

    /**
     * Makes network call to fetch employees from remote source.
     *
     * @param url for remote source.
     */
    public void fetchEmployees(String url) {
        if (url == null) {
            listener.fetchCompleted(ResponseType.EMPTY);
            return;
        }

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e(TAG, "Network Call failed.", e);
                if (listener != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        @Override public void run() {
                            listener.fetchCompleted(ResponseType.NETWORK_ERROR);
                        }
                    });

                }
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response)
                    throws IOException {
                Log.i(TAG, "Received response from url: " + url);
                handleResponse(response.body().string());

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override public void run() {
                        handleUiThreadTasks();
                    }
                });
            }
        });
    }

    @VisibleForTesting
    void handleUiThreadTasks() {
        ResponseType responseType = ResponseType.VALID;
        if (listener != null) {
            Log.d(TAG, employeeList.toString());
            if (employeeList.size() == 0) {
                responseType = ResponseType.EMPTY;
            } else if (employeeList.stream().anyMatch(employee -> !employee.isValid())) {
                responseType = ResponseType.MALFORMED;
            }
            listener.fetchCompleted(responseType);
        }
    }

    @VisibleForTesting
    void handleResponse(String responseString) {
        if (responseString.isEmpty()) {
            return;
        }
        JSONObject jsonObject;
        JSONArray jsonArray;
        try {
            jsonObject = new JSONObject(responseString);
            jsonArray = jsonObject.getJSONArray("employees");
            ObjectMapper objectMapper = new ObjectMapper();
            employeeList = objectMapper.readValue(String.valueOf(jsonArray),
                                                  new TypeReference<List<Employee>>() {
                                                  });
        } catch (JSONException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Cleanup logic to prevent memory leaks.
     */
    public void onDestroy() {
        // Release reference to MainActivity to avoid memory leak
        listener = null;
    }

    public interface NetworkCallCompletedListener {
        /**
         * Executes logic to set UI based on response type after network call completed.
         *
         * @param responseType
         */
        public void fetchCompleted(ResponseType responseType);
    }
}
