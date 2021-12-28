package com.everest.airline.model;

import com.everest.airline.model.cabins.types.BusinessClass;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.types.EconomyClass;
import com.everest.airline.model.cabins.types.FirstClass;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private final Cabin firstClass;
    private final Cabin secondClass;
    private final Cabin economyClass;
    private LocalDate departureDate;


    public Flight(long number, String source, String destination, LocalDate departureDate, int economyClassCapacity, int firstClassCapacity, int secondClassCapacity, int occupiedEconomicSeats, int occupiedFirstClassSeats, int occupiedSecondClassSeats, double economyClassBaseFare, double firstClassBaseFare, double secondClassBaseFare) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.firstClass = new FirstClass(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare);
        this.secondClass = new BusinessClass(secondClassCapacity, occupiedSecondClassSeats, secondClassBaseFare);
        this.economyClass = new EconomyClass(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare);
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

    public Cabin getSecondClass() {
        return secondClass;
    }

    public int getCapacity(CabinTypes type) {
        return selecting(type).getCapacity();
    }

    public int getOccupiedSeats(CabinTypes type) {
        return selecting(type).getOccupiedSeats();
    }

    public void updateOccupiedSeats(CabinTypes type, int numberOfPassengersBoarding) {
        selecting(type).updateOccupiedSeats(numberOfPassengersBoarding);
    }

    public int getAvailableSeats(CabinTypes type) {
        return getCapacity(type) - getOccupiedSeats(type);
    }

    public boolean checkAvailability(CabinTypes type, int numberOfPassengers) {
        return numberOfPassengers <= getAvailableSeats(type);
    }

    public double getTicketPrice(CabinTypes type) {
        LocalDate now = LocalDate.now();
        int differenceInDays = Math.abs(now.compareTo(departureDate));
        if (differenceInDays > 15) {
            return (int) selecting(type).ticketCost();
        } else {
            return calculateFare(type, differenceInDays);
        }
    }

    public double getBaseTicketPrice(CabinTypes type) {
        return (int) selecting(type).getBaseFare();
    }


    public void updateTicketPrice(CabinTypes type, int percentage) {
        selecting(type).updateFare(percentage);
    }

    private Cabin selecting(CabinTypes cabinType) {
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


    private int calculateFare(CabinTypes type, int differenceInDays) {
        double currentFare;
        double fare = selecting(type).ticketCost();
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
}
