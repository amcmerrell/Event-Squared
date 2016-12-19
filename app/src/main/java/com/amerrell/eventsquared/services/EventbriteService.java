package com.amerrell.eventsquared.services;

import com.amerrell.eventsquared.Constants;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class EventbriteService {
    public static void findEventbriteEvents(String cityState, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EVENTBRITE_LOCATION_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.EVENTBRITE_TOKEN_PARAMETER, Constants.EVENTBRITE_API_TOKEN)
                .addQueryParameter(Constants.EVENTBRITE_LOCATION_PARAMETER, cityState);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}
