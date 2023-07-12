package com.hotshot.android.exercise.employeedirectory.networking;

import okhttp3.OkHttpClient;

public class NetworkingClient {
    private static OkHttpClient client;
    private NetworkingClient() {}

    public static OkHttpClient getInstance() {
        if(client == null) {
            client = new OkHttpClient();
        }
        return client;
    }
}
