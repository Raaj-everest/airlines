package com.everest.airline;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private LocalDate departureDate;
    private int capacity = 0;
    private int occupiedEconomicSeats;
    private int occupiedFirstClassSeats;
    private int occupiedSecondClassSeats;
    private int availableEconomicSeats;
    private int availableFirstClassSeats;
    private int availableSecondClassSeats;
    private int totalOccupiedSeats =0;
    private int totalAvailableSeats =0;

    public Flight(long number, String source, String destination, LocalDate departureDate,int capacity,int occupiedSeats) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.capacity=capacity;
        this.departureDate = departureDate;
        this.totalOccupiedSeats =occupiedSeats;
        this.totalAvailableSeats =(capacity-occupiedSeats);
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

    public int getTotalOccupiedSeats() {
        return totalOccupiedSeats;
    }

    public int getTotalAvailableSeats() {
        return totalAvailableSeats;
    }

    public void setOccupiedSeats() {
        if(totalOccupiedSeats <=capacity) {
            this.totalOccupiedSeats += 1;
        }
    }
}
