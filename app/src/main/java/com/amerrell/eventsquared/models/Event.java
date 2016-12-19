package com.amerrell.eventsquared.models;

public class Event {
    String name;
    String dateTime;
    String venue;
    //TODO determine how to find lowest price for Eventbite and TM events.
    String price;
    String imageURL;

    public Event() {}

    public Event(String name, String dateTime, String venue, String price, String imageURL) {
        this.name = name;
        this.dateTime = dateTime;
        this.venue = venue;
        this.price = price;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getVenue() {
        return venue;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getPrice() {
        return price;
    }
}
