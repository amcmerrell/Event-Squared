package com.amerrell.eventsquared.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amerrell.eventsquared.Constants;
import com.amerrell.eventsquared.R;
import com.amerrell.eventsquared.adapters.EventListAdapter;
import com.amerrell.eventsquared.models.Event;
import com.amerrell.eventsquared.services.EventbriteService;
import com.amerrell.eventsquared.services.TicketmasterService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class EventListActivity extends AppCompatActivity {
    @Bind(R.id.eventListRecyclerView) RecyclerView mEventRecyclerView;
    private EventListAdapter mEventAdapter;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Parcelable recyclerViewState;

    public ArrayList<Event> mEvents = new ArrayList<>();
    public ArrayList<Event> mTMEvents = new ArrayList<>();
    public Integer mPageNumber;
    public String mSearchCity;
    public String mSearchState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSearchCity = mSharedPreferences.getString(Constants.SHARED_PREFERENCES_CITY, null);
        mSearchState = mSharedPreferences.getString(Constants.SHARED_PREFERENCES_STATE, null);

        Intent intent = getIntent();
        mPageNumber = intent.getIntExtra("pageNumber", 0);

        getTMEvents(mSearchCity, mSearchState);
        getEventbriteEvents(mSearchCity, mSearchState);
    }

    private void getTMEvents(String city, String state) {
        final TicketmasterService ticketmasterService = new TicketmasterService();
        ticketmasterService.findTMEvents(city, state, mPageNumber, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //add Ticketmaster data to array and sort based on event data.
                mEvents.addAll(ticketmasterService.processResults(response));
                Collections.sort(mEvents, new Comparator<Event>() {
                    @Override
                    public int compare(Event e1, Event e2) {
                        return e1.toDateTime().compareTo(e2.toDateTime());
                    }
                });
                EventListActivity.this.runOnUiThread(new Runnable() {
                    int pastVisiblesItems, visibleItemCount, totalItemCount;
                    boolean loading = true;

                    @Override
                    public void run() {
                        mEventAdapter = new EventListAdapter(getApplicationContext(), mEvents);
                        mEventRecyclerView.setAdapter(mEventAdapter);

                        final LinearLayoutManager layoutManager = new LinearLayoutManager(EventListActivity.this);

                        mEventRecyclerView.setLayoutManager(layoutManager);
                        mEventRecyclerView.setHasFixedSize(true);

                        //Get saved scroll position and set onScrollListener to load data on page end.
                        mEventRecyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                        mEventRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                if (dy > 0) {
                                    visibleItemCount = layoutManager.getChildCount();
                                    totalItemCount = layoutManager.getItemCount();
                                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                                    if (loading) {
                                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                            Log.d("...", "Reload!");
                                            recyclerViewState = mEventRecyclerView.getLayoutManager().onSaveInstanceState();
                                            loading = false;
                                            mPageNumber++;
                                            Log.d("Search city", mSearchCity);
                                            Log.d("Page number", mPageNumber.toString());
                                            getTMEvents(mSearchCity, mSearchState);
                                            getEventbriteEvents(mSearchCity, mSearchState);
                                        }
                                    }
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    private void getEventbriteEvents(final String city, final String state) {
        String cityState = city + ", " + state;
        final EventbriteService eventbriteService = new EventbriteService();

        eventbriteService.findEventbriteEvents(cityState, mPageNumber,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mEvents.addAll(eventbriteService.processResults(response));
                    getTMEvents(city, state);
                }
            }
        });
    }
}
