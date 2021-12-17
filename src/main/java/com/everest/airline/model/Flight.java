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
    private CabinTypes selectedCabinType;  //only used to fetch data from thymeleaf
    private int numberOfPassengersBoarding;  //only used to fetch data from thymeleaf

    public Flight(long number, String source, String destination, LocalDate departureDate, int economyClassCapacity, int firstClassCapacity, int secondClassCapacity, int occupiedEconomicSeats, int occupiedFirstClassSeats, int occupiedSecondClassSeats, double economyClassBaseFare, double firstClassBaseFare, double secondClassBaseFare) {
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
        return selector(type).getCapacity();
    }

    public int getOccupiedSeats(CabinTypes type) {
        return selector(type).getOccupiedSeats();
    }

    public void updateOccupiedSeats(CabinTypes type, int numberOfPassengersBoarding) {
        selector(type).updateOccupiedSeats(numberOfPassengersBoarding);
    }

    public int getAvailableSeats(CabinTypes type) {
        return getCapacity(type) - getOccupiedSeats(type);
    }

    public boolean checkAvailability(CabinTypes type, int numberOfPassengers) {
        this.selectedCabinType = type;
        this.numberOfPassengersBoarding = numberOfPassengers;
        return numberOfPassengers <= getAvailableSeats(type);
    }

    public double getTicketPrice(CabinTypes type) {
        return (int) selector(type).getFare();
    }

    public double getBaseTicketPrice(CabinTypes type) {
        return (int) selector(type).getBaseFare();
    }


    public void updateTicketPrice(CabinTypes type, int percentage) {
        selector(type).updateFare(percentage);
    }

    public Cabin selector(CabinTypes cabinType) {
        switch (cabinType) {
            case FIRST:
                return firstClass;
            case SECOND:
                return secondClass;
            case ECONOMIC:
                return economyClass;
            default:
                throw new IllegalStateException("Unexpected value: " + cabinType);
        }
    }

    @Override
    public String toString() {
        return getNumber() + "," + getSource() + "," + getDestination()
                + "," + getDepartureDate().getYear() + "-" + getDepartureDate().getMonthValue()
                + "-" + getDepartureDate().getDayOfMonth() + "," + getCapacity(CabinTypes.ECONOMIC)
                + "," + getCapacity(CabinTypes.FIRST) + "," + getCapacity(CabinTypes.SECOND)
                + "," + getOccupiedSeats(CabinTypes.ECONOMIC) + "," + getOccupiedSeats(CabinTypes.FIRST)
                + "," + getOccupiedSeats(CabinTypes.SECOND) + "," + getBaseTicketPrice(CabinTypes.ECONOMIC)
                + "," + getBaseTicketPrice(CabinTypes.FIRST) + "," + getBaseTicketPrice(CabinTypes.SECOND);
    }

    public int getAvailableSeats() { //used to fetch value in thymeleaf
        return getCapacity(this.selectedCabinType) - getOccupiedSeats(this.selectedCabinType);
    }

    public double getTicketPrice() { //used to fetch value in thymeleaf
        return selector(this.selectedCabinType).getFare() * (numberOfPassengersBoarding);
    }

}
