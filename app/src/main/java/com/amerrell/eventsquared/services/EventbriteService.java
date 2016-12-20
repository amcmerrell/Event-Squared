package com.amerrell.eventsquared.services;

import android.util.Log;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventbriteService {
    public static void findEventbriteEvents(String cityState, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EVENTBRITE_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.EVENTBRITE_SEARCH_PATH)
                .addQueryParameter(Constants.EVENTBRITE_TOKEN_PARAMETER, Constants.EVENTBRITE_API_TOKEN)
                .addQueryParameter(Constants.EVENTBRITE_LOCATION_PARAMETER, cityState);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void findEventTicketPrice(String eventId, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EVENTBRITE_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(eventId)
                .addPathSegment(Constants.EVENTBRITE_TICKET_PATH)
                .addQueryParameter(Constants.EVENTBRITE_TOKEN_PARAMETER, Constants.EVENTBRITE_API_TOKEN);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Event> processResults(Response response) {
        ArrayList<Event> events = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject eventbriteJSON = new JSONObject(jsonData);
                JSONArray eventsJSON = eventbriteJSON.getJSONArray("events");
                for (int i = 0; i < eventsJSON.length(); i++) {
                    String id = eventsJSON.getJSONObject(i).getString("id");
                    String name = eventsJSON.getJSONObject(i).getJSONObject("name").getString("text");
                    Log.d("id", id);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return events;
    }

    public static void setEventbritePrice(String eventId) {


    }
}
