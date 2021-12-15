package com.everest.airline.model;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private final Cabin firstClass;
    private final Cabin secondClass;
    private final Cabin economyClass;
    private LocalDate departureDate;
    private CabinTypes selectedCabinType;

    public Flight(long number, String source, String destination, LocalDate departureDate, int economyClassCapacity, int firstClassCapacity, int secondClassCapacity, int occupiedEconomicSeats, int occupiedFirstClassSeats, int occupiedSecondClassSeats, int economyClassBaseFare, int firstClassBaseFare, int secondClassBaseFare) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare);
        this.secondClass = new Cabin(secondClassCapacity, occupiedSecondClassSeats, secondClassBaseFare);
        this.economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare);
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

    public int getCapacity(CabinTypes type) {
        switch (type) {
            case ALL:
                return (economyClass.getCapacity() + firstClass.getCapacity() + secondClass.getCapacity());
            case FIRST:
                return firstClass.getCapacity();
            case SECOND:
                return secondClass.getCapacity();
            case ECONOMIC:
                return economyClass.getCapacity();
        }
        return -1;
    }

    public int getOccupiedSeats(CabinTypes type) {
        this.selectedCabinType = type;
        switch (type) {
            case ALL:
                return (firstClass.getOccupiedSeats() + secondClass.getOccupiedSeats() + economyClass.getOccupiedSeats());
            case FIRST:
                return firstClass.getOccupiedSeats();
            case SECOND:
                return secondClass.getOccupiedSeats();
            case ECONOMIC:
                return economyClass.getOccupiedSeats();
        }
        return -1;
    }

    public void updateOccupiedSeats(CabinTypes type, int numberOfPassengersBoarding) {
        switch (type) {
            case FIRST:
                firstClass.updateOccupiedSeats(numberOfPassengersBoarding);
                return;
            case SECOND:
                secondClass.updateOccupiedSeats(numberOfPassengersBoarding);
                return;
            case ECONOMIC:
                economyClass.updateOccupiedSeats(numberOfPassengersBoarding);
                return;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public int getAvailableSeats(CabinTypes type) {
        return getCapacity(type) - getOccupiedSeats(type);
    }

    public int getAvailableSeats() { //used to fetch value in thymeleaf
        return getCapacity(this.selectedCabinType) - getOccupiedSeats(this.selectedCabinType);
    }

    public int getTicketPrice(CabinTypes type) {
        switch (type) {
            case FIRST:
                return firstClass.getFare();
            case SECOND:
                return secondClass.getFare();
            case ECONOMIC:
                return economyClass.getFare();
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public int getTicketPrice() { //used to fetch value in thymeleaf
        switch (this.selectedCabinType) {
            case FIRST:
                return firstClass.getFare();
            case SECOND:
                return secondClass.getFare();
            case ECONOMIC:
                return economyClass.getFare();
            default:
                throw new IllegalStateException("Unexpected value: " + selectedCabinType);
        }
    }

    public void updateTicketPrice(CabinTypes type, int percentage) {
        switch (type) {
            case ALL:
                break;
            case FIRST:
                firstClass.updateFare(percentage);
                break;
            case SECOND:
                secondClass.updateFare(percentage);
                break;
            case ECONOMIC:
                economyClass.updateFare(percentage);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

}
