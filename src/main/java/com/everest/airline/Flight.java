package com.everest.airline;

import java.time.LocalDate;

public class Flight {
    private long number;
    private String source;
    private String destination;
    private LocalDate departureDate;
    private final int capacity = 10;
    private int occupiedSeats=0;
    private int availableSeats=0;

    public Flight(long number, String source, String destination, LocalDate departureDate,int occupiedSeats) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.occupiedSeats=occupiedSeats;
        this.availableSeats=(capacity-occupiedSeats);
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

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }
}
