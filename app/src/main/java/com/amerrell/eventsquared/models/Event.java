package com.amerrell.eventsquared.models;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DecimalFormat;

public class Event {
    String id;
    String name;
    String ticketURL;
    String dateTime;
    String venue;
    Double minPrice;
    Double maxPrice;
    String imageURL;
    String source;

    public Event() {}

    public Event(String id, String name, String ticketURL, String dateTime, String venue, Double minPrice, Double maxPrice, String imageURL) {
        this.id = id;
        this.name = name;
        this.ticketURL = ticketURL;
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
        if (name.length() <= 50) {
            return name;
        } else {
            return name.substring(0,50) + "...";
        }
    }

    public String getTicketURL() {
        return ticketURL;
    }

    public String getDateTime() {
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("MM/dd/yyyy h:mm a");
        return dateFormat.print(toDateTime());
    }

    public String getVenue() {
        if (venue.equals("null")) {
            return "";
        } else {
            return venue;
        }
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public DateTime toDateTime() {
        DateTime dt = new DateTime(dateTime);
        return dt;
    }

    public String toStringMinPrice() {
        if (minPrice == 0.0) {
            return "Free - ";
        } else if (minPrice == -0.01) {
            return "No prices found";
        } else {
            return "$" + formatDouble(minPrice) + " - ";
        }
    }

    public String toStringMaxPrice() {
        if (maxPrice == 0.0) {
            return "Free";
        } else if (maxPrice == -0.01) {
            return "No prices found";
        } else {
            return "$" + formatDouble(maxPrice);
        }
    }

    public String formatDouble(Double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(price);
    }

    public boolean priceIsEqual(Double minPrice, Double maxPrice) {
        if (minPrice == maxPrice) {
            return true;
        } else if (minPrice == -0.01 || maxPrice == -0.01) {
            return true;
        } else {
            return false;
        }
    }
}
