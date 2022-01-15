package com.everest.airline.model;

import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private final Cabin firstClass;
    private final Cabin businessClass;
    private final Cabin economyClass;
    private final LocalDate departureDate;


    public Flight(long number, String source, String destination, LocalDate departureDate, Cabin firstClass,Cabin businessClass,Cabin economyClass) {
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
        return getCapacity(type) - getOccupiedSeats(type);
    }

    public boolean checkAvailability(CabinType type, int numberOfPassengers) {
        return numberOfPassengers <= getAvailableSeats(type);
    }


    public double getBaseTicketPrice(CabinType type) {
        return (int) cabinSelector(type).getBaseFare();
    }


    private Cabin cabinSelector(CabinType cabinType) {
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
        LocalDate now = LocalDate.now();
        int differenceInDays = Math.abs(now.compareTo(departureDate));
        if (differenceInDays > 15) {
            return (int) cabinSelector(type).getBaseFare();
        } else {
            return calculateFare(type, differenceInDays);
        }
    }

    private int calculateFare(CabinType type, int differenceInDays) {
        double currentFare;
        double fare = cabinSelector(type).getBaseFare();
        if (differenceInDays > 3) {
            for (int i = 1; i <= (15 - differenceInDays); i++) {
                currentFare = fare + (fare * 2 / 100);
                fare = currentFare;
            }
            return (int) fare;
        }
        if (differenceInDays > 0) {
            for (int i = 1; i <= (12); i++) {
                currentFare = fare + (fare * 2 / 100);
                fare = currentFare;
            }
            for (int i = 1; i <= 3; i++) {
                currentFare = fare + (fare * 10 / 100);
                fare = currentFare;
            }
            return (int) fare;
        }
        return -1;
    }
    public void updateTicketPrice(CabinType type, int percentage) {
        cabinSelector(type).updateFare(percentage);
    }

}