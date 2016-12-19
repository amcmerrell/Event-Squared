package com.amerrell.eventsquared.models;

public class Event {
    String id;
    String name;
    String dateTime;
    String venue;
    //TODO determine how to find lowest price for Eventbite and TM events.
    String price;
    String imageURL;

    public Event() {}

    public Event(String id, String name, String dateTime, String venue, String price, String imageURL) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.venue = venue;
        this.price = "";
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
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

    public void setPrice(String price) {
        this.price = price;
    }
}
