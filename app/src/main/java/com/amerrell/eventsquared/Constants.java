package com.amerrell.eventsquared;

public class Constants {
    public static final String TM_API_KEY = BuildConfig.TM_API_KEY;
    public static final String TM_LOCATION_BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json";
    public static final String TM_KEY_PARAMETER = "apikey";
    public static final String TM_CITY_PARAMETER = "city";
    public static final String TM_STATE_PARAMETER = "stateCode";
    public static final String TM_SORT_PARAMETER = "sort";
    public static final String TM_START_DATE_PARAMETER = "startDateTime";
    public static final String TM_DATE_ASC_VALUE = "date,asc";

    public static final String EVENTBRITE_API_TOKEN = BuildConfig.EVENTBRITE_TOKEN_KEY;
    public static final String EVENTBRITE_BASE_URL = "https://www.eventbriteapi.com/v3/events/";
    public static final String EVENTBRITE_SEARCH_PATH = "search";
    public static final String EVENTBRITE_TICKET_PATH = "ticket_classes";
    public static final String EVENTBRITE_EXPAND_PARAMETER = "expand";
    public static final String EVENTBRITE_TOKEN_PARAMETER = "token";
    public static final String EVENTBRITE_LOCATION_PARAMETER = "location.address";
    public static final String EVENTBRITE_SORTBY_PARAMETER = "sort_by";
    public static final String EVENTBRITE_START_DATE_PARAMETER = "start_date.range_start";
    public static final String EVENTBRITE_DATE_VALUE = "date";
    public static final String EVENTBRITE_TODAY_VALUE = "today";
    public static final String EVENTBRITE_VENUE_TICKETS_VALUES = "venue,ticket_classes";
}
