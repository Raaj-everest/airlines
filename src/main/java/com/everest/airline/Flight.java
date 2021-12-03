package com.everest.airline;

import java.time.LocalDate;

public class Flight {
    private long number;
    private String source;
    private String destination;
    private LocalDate departureDate;

    public Flight(long number, String source, String destination, LocalDate departureDate) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public long getNumber() {
        return number;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }
}
