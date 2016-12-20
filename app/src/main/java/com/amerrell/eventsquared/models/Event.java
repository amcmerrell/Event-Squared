package com.amerrell.eventsquared.models;

import org.joda.time.DateTime;

public class Event {
    String id;
    String name;
    String dateTime;
    String venue;
    Double minPrice;
    Double maxPrice;
    String imageURL;

    public Event() {}

    public Event(String id, String name, String dateTime, String venue, Double minPrice, Double maxPrice, String imageURL) {
        this.id = id;
        this.name = name;
        this.dateTime = dateTime;
        this.venue = venue;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
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

    public DateTime toDateTime() {
        DateTime dt = new DateTime(dateTime);
        return dt;
    }

    public String getVenue() {
        return venue;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public String toStringMinPrice() {
        if (minPrice == 0.0) {
            return "Free";
        } else if (minPrice == -0.01) {
            return "N/A";
        } else {
            return "$" + minPrice.toString();
        }
    }

    public String toStringMaxPrice() {
        if (maxPrice == 0.0) {
            return "Free";
        } else if (maxPrice == -0.01) {
            return "N/A";
        } else {
            return "$" + maxPrice.toString();
        }
    }
}
