package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amerrell.eventsquared.R;
import com.amerrell.eventsquared.models.Event;
import com.amerrell.eventsquared.services.EventbriteService;
import com.amerrell.eventsquared.services.TicketmasterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EventListActivity extends AppCompatActivity {
    public ArrayList<Event> mEvents = new ArrayList<>();
    public ArrayList<Event> mTMEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        Intent intent = getIntent();
        String searchCity = intent.getStringExtra("city");
        String searchState = intent.getStringExtra("state");

        getTMEvents(searchCity, searchState);
        getEventbriteEvents(searchCity, searchState);
    }

    private void getTMEvents(String city, String state) {
        final TicketmasterService ticketmasterService = new TicketmasterService();
        ticketmasterService.findTMEvents(city, state, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mTMEvents = ticketmasterService.processResults(response);
                    Integer size = mEvents.size();
                    Log.d("Events size ", size.toString());
                    mEvents.addAll(mTMEvents);
                    Integer newSize = mEvents.size();
                    Log.d("After adding", newSize.toString());
                    Collections.sort(mEvents, new Comparator<Event>() {
                        @Override
                        public int compare(Event e1, Event e2) {
                            return e1.toDateTime().compareTo(e2.toDateTime());
                        }
                    });
                }
            }
        });
    }

    private void getEventbriteEvents(final String city, final String state) {
        String cityState = city + ", " + state;
        final EventbriteService eventbriteService = new EventbriteService();

        eventbriteService.findEventbriteEvents(cityState, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mEvents = eventbriteService.processResults(response);
                    getTMEvents(city, state);
                }
            }
        });
    }
}
