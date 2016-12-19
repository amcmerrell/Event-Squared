package com.amerrell.eventsquared;

/**
 * Created by Guest on 12/19/16.
 */
public class Constants {
    public static final String TM_API_KEY = BuildConfig.TM_API_KEY;
    public static final String TM_LOCATION_BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    public static final String TM_KEY_PARAMETER = "apikey";
    public static final String TM_CITY_PARAMETER = "city";
    public static final String TM_STATE_PARAMETER = "stateCode";

    public static final String EVENTBRITE_API_KEY = BuildConfig.EVENTBRITE_TOKEN_KEY;
    public static final String EVENTBRITE_LOCATION_BASE_URL = "https://www.eventbriteapi.com/v3/events/search/";
    public static final String EVENTBRITE_TOKEN_PARAMETER = "token";
    public static final String EVENTBRITE_LOCATION_PARAMETER = "location.address";
}
