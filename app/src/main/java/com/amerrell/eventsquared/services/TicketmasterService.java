package com.amerrell.eventsquared.services;

import android.util.Log;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.models.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TicketmasterService {
    public static void findTMEvents(String city, String state, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        SimpleDateFormat tmDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        Date now = new Date();
        String strTMDate = tmDateTimeFormat.format(now);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.TM_LOCATION_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.TM_KEY_PARAMETER, Constants.TM_API_KEY)
            .addQueryParameter(Constants.TM_CITY_PARAMETER, city)
            .addQueryParameter(Constants.TM_STATE_PARAMETER, state)
            .addQueryParameter(Constants.TM_START_DATE_PARAMETER, strTMDate);
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
                JSONObject tmJSON = new JSONObject(jsonData);
                JSONArray eventsJSON = tmJSON.getJSONObject("_embedded").getJSONArray("events");
                for (int i = 0; i < eventsJSON.length(); i++) {
                    JSONObject eventJSON = eventsJSON.getJSONObject(i);
                    String id = eventJSON.getString("id");
                    String name = eventJSON.getString("name");
                    String dateTime = "";
                    JSONObject eventDates = eventJSON.getJSONObject("dates").getJSONObject("start");
                    if (eventDates.has("dateTime")) {
                        dateTime = eventDates.getString("dateTime");
                    } else {
                        dateTime = eventDates.getString("localDate");
                    }
                    String venue = eventJSON.getJSONObject("_embedded").getJSONArray("venues").getJSONObject(0).getString("name");
                    JSONArray images = eventJSON.getJSONArray("images");
                    String imageURL = "";
                    for (int j = 0; j < images.length(); j++) {
                        if (images.getJSONObject(j).getString("ratio").equals("16_9")) {
                            imageURL = images.getJSONObject(j).getString("url");
                            break;
                        }
                    }
                    if (imageURL.equals("")) {
                        imageURL = images.getJSONObject(0).getString("url");
                    }
                    Double minPrice = -0.01;
                    Double maxPrice = -0.01;
                    if (!eventJSON.isNull("priceRanges")) {
                        minPrice = eventJSON.getJSONArray("priceRanges").getJSONObject(0).getDouble("min");
                        maxPrice = eventJSON.getJSONArray("priceRanges").getJSONObject(0).getDouble("max");
                    }
                    String ticketURL = eventJSON.getString("url");
                    Event event = new Event(id, name, ticketURL,dateTime, venue, minPrice, maxPrice, imageURL);
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
}
