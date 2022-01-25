package com.everest.airline.model;

import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.services.tickets.TicketCalculator;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private final LocalDate departureDate;
    private final Cabin firstClass;
    private final Cabin businessClass;
    private final Cabin economyClass;


    public Flight(long number, String source, String destination, LocalDate departureDate, Cabin firstClass, Cabin businessClass, Cabin economyClass) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.firstClass = firstClass;
        this.businessClass = businessClass;
        this.economyClass = economyClass;
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

    public Cabin getEconomyClass() {
        return economyClass;
    }

    public Cabin getFirstClass() {
        return firstClass;
    }

    public Cabin getBusinessClass() {
        return businessClass;
    }

    public int getCapacity(CabinType type) {
        return cabinSelector(type).getCapacity();
    }

    public int getOccupiedSeats(CabinType type) {
        return cabinSelector(type).getOccupiedSeats();
    }

    public void updateOccupiedSeats(CabinType type, int numberOfPassengersBoarding) {
        cabinSelector(type).updateOccupiedSeats(numberOfPassengersBoarding);
    }

    public int getAvailableSeats(CabinType type) {
        return cabinSelector(type).getAvailableSeats();
    }

    public boolean checkAvailability(CabinType type, int numberOfPassengers) {
        return numberOfPassengers <= getAvailableSeats(type);
    }


    public double getBaseTicketPrice(CabinType type) {
        return (int) cabinSelector(type).getBaseFare();
    }


    public Cabin cabinSelector(CabinType cabinType) {
        switch (cabinType) {
            case FIRST:
                return firstClass;
            case BUSINESS:
                return businessClass;
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
                + "-" + getDepartureDate().getDayOfMonth() + "," + getCapacity(CabinType.ECONOMIC)
                + "," + getCapacity(CabinType.FIRST) + "," + getCapacity(CabinType.BUSINESS)
                + "," + getOccupiedSeats(CabinType.ECONOMIC) + "," + getOccupiedSeats(CabinType.FIRST)
                + "," + getOccupiedSeats(CabinType.BUSINESS) + "," + getBaseTicketPrice(CabinType.ECONOMIC)
                + "," + getBaseTicketPrice(CabinType.FIRST) + "," + getBaseTicketPrice(CabinType.BUSINESS);
    }

    public double getTicketPrice(CabinType type) {
        TicketCalculator tc = new TicketCalculator();
        return tc.price(this.departureDate, cabinSelector(type));
    }

}