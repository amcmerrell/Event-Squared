package com.amerrell.eventsquared.services;

import android.util.Log;

import com.amerrell.eventsquared.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class TicketmasterService {
    public static void findTMEvents(String city, String state, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TM_LOCATION_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.TM_KEY_PARAMETER, Constants.TM_API_KEY)
            .addQueryParameter(Constants.TM_CITY_PARAMETER, city)
            .addQueryParameter(Constants.TM_STATE_PARAMETER, state);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
