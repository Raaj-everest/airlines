package com.everest.airline.model;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private LocalDate departureDate;
    private final int economyClassCapacity;
    private final int firstClassCapacity;
    private final int secondClassCapacity;
    private int occupiedEconomicSeats;
    private int occupiedFirstClassSeats;
    private int occupiedSecondClassSeats;
    private Cabin selectedCabinType;

    public Flight(long number, String source, String destination, LocalDate departureDate, int economyClassCapacity, int firstClassCapacity, int secondClassCapacity, int occupiedEconomicSeats, int occupiedFirstClassSeats, int occupiedSecondClassSeats) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.economyClassCapacity = economyClassCapacity;
        this.firstClassCapacity = firstClassCapacity;
        this.secondClassCapacity = secondClassCapacity;
        this.occupiedEconomicSeats = occupiedEconomicSeats;
        this.occupiedFirstClassSeats = occupiedFirstClassSeats;
        this.occupiedSecondClassSeats = occupiedSecondClassSeats;
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


    public int getCapacity(Cabin type) {
        switch (type) {
            case ALL:
                return (economyClassCapacity + firstClassCapacity + secondClassCapacity);
            case FIRST:
                return firstClassCapacity;
            case SECOND:
                return secondClassCapacity;
            case ECONOMIC:
                return economyClassCapacity;
        }
        return -1;
    }

    public int getOccupiedSeats(Cabin type) {
        switch (type) {
            case ALL:
                return (occupiedEconomicSeats + occupiedFirstClassSeats + occupiedSecondClassSeats);
            case FIRST:
                return occupiedFirstClassSeats;
            case SECOND:
                return occupiedSecondClassSeats;
            case ECONOMIC:
                return occupiedEconomicSeats;
        }
        return -1;
    }

    public void updateOccupiedSeats(Cabin type, int numberOfPassengersBoarding) {
        switch (type) {
            case ALL:
                return;
            case FIRST:
                if ((occupiedFirstClassSeats + numberOfPassengersBoarding) <= firstClassCapacity) {
                    this.occupiedFirstClassSeats += numberOfPassengersBoarding;
                }
                break;
            case SECOND:

                if ((occupiedSecondClassSeats + numberOfPassengersBoarding) <= secondClassCapacity) {
                    this.occupiedSecondClassSeats += numberOfPassengersBoarding;
                }
                break;
            case ECONOMIC:
                if ((occupiedEconomicSeats + numberOfPassengersBoarding) <= economyClassCapacity) {
                    this.occupiedEconomicSeats += numberOfPassengersBoarding;
                }
                break;
        }
    }

    public int getAvailableSeats(Cabin type) {
        this.selectedCabinType=type;
        return getCapacity(type) - getOccupiedSeats(type);
    }
    public int getAvailableSeats() {

        return getCapacity(this.selectedCabinType) - getOccupiedSeats(this.selectedCabinType);
    }

}
