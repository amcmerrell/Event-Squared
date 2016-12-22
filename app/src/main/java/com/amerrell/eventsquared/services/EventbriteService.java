package com.amerrell.eventsquared.services;

import android.util.Log;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.models.Event;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EventbriteService {
    public static void findEventbriteEvents(String cityState, Integer pageNumber, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().build();

        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTime dt = new DateTime(DateTimeZone.getDefault());

        Integer eventbritePage = pageNumber + 1;

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.EVENTBRITE_BASE_URL).newBuilder();
        urlBuilder.addPathSegment(Constants.EVENTBRITE_SEARCH_PATH)
                .addQueryParameter(Constants.EVENTBRITE_LOCATION_PARAMETER, cityState)
                .addQueryParameter(Constants.EVENTBRITE_START_DATE_PARAMETER, dateFormat.print(dt))
                .addQueryParameter(Constants.EVENTBRITE_SORTBY_PARAMETER, Constants.EVENTBRITE_DATE_VALUE)
                .addQueryParameter(Constants.EVENTBRITE_EXPAND_PARAMETER, Constants.EVENTBRITE_VENUE_TICKETS_VALUES)
                .addQueryParameter(Constants.EVENTBRITE_PAGE_PARAMETER, eventbritePage.toString())
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
                    if (eventJSON.getBoolean("online_event")) {
                        break;
                    }
                    String id = eventJSON.getString("id");
                    String name = eventJSON.getJSONObject("name").getString("text");
                    String dateTime = eventJSON.getJSONObject("start").getString("local");
                    String venue = eventJSON.getJSONObject("venue").getString("name");
                    String imageURL = "";
                    if (eventJSON.has("logo") && !eventJSON.isNull("logo")) {
                        imageURL = eventJSON.getJSONObject("logo").getString("url");
                    }
                    JSONArray ticketArray = eventJSON.getJSONArray("ticket_classes");
                    Double minPrice = -0.01;
                    Double maxPrice = -0.01;
                    if (ticketArray.length() > 0) {
                        ArrayList<Double> ticketPrices = sortTicketPrices(ticketArray);
                        minPrice = ticketPrices.get(0);
                        maxPrice = ticketPrices.get(ticketPrices.size() - 1);
                    }
                    String ticketURL = eventJSON.getString("url");
                    Event event = new Event(id, name, ticketURL,dateTime, venue, minPrice, maxPrice, imageURL);
                    event.setSource("eventbrite");
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

    public ArrayList<Double> sortTicketPrices(JSONArray tickets) {
        ArrayList<Double> prices = new ArrayList<>();
        try {
            for (int i = 0; i < tickets.length(); i++) {
                JSONObject ticket = tickets.getJSONObject(i);
                if (ticket.getBoolean("free")) {
                    prices.add(0.00);
                } else if(ticket.isNull("cost")) {
                    prices.add(-0.01);
                } else {
                    Integer intPrice = ticket.getJSONObject("cost").getInt("value");
                    Double price = intPrice / 100.00;
                    prices.add(price);
                }
            }
            Collections.sort(prices);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prices;
    }
}
