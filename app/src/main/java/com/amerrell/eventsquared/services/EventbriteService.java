package com.amerrell.eventsquared.services;

import android.util.Log;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                .addQueryParameter(Constants.EVENTBRITE_LOCATION_PARAMETER, cityState)
                .addQueryParameter(Constants.EVENTBRITE_EXPAND_PARAMETER, Constants.EVENTBRITE_VENUE_VALUE)
                .addQueryParameter(Constants.EVENTBRITE_TOKEN_PARAMETER, Constants.EVENTBRITE_API_TOKEN);

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
                    JSONObject eventJSON = eventsJSON.getJSONObject(i);
                    String id = eventJSON.getString("id");
                    String name = eventJSON.getJSONObject("name").getString("text");
                    String dateTime = eventJSON.getJSONObject("start").getString("local");
                    String venue = eventJSON.getJSONObject("venue").getString("name");
                    String imageURL = eventJSON.getJSONObject("logo").getString("url");
                    Event event = new Event(id, name, dateTime, venue, imageURL);
                    events.add(event);
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
