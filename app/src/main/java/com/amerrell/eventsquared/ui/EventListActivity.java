package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amerrell.eventsquared.R;
import com.amerrell.eventsquared.services.EventbriteService;
import com.amerrell.eventsquared.services.TicketmasterService;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EventListActivity extends AppCompatActivity {

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
                String jsonData = response.body().string();
                Log.v("TM done ", jsonData);
            }
        });
    }

    private void getEventbriteEvents(String city, String state) {
        String cityState = city + ", " + state;
        final EventbriteService eventbriteService = new EventbriteService();

        eventbriteService.findEventbriteEvents(cityState, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String jsonData = response.body().string();
                Log.v("Eventbrite done", jsonData);
            }
        });
    }
}
