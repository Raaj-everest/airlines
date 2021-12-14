package com.everest.airline.model;

import java.time.LocalDate;

public class Flight {
    private final long number;
    private final String source;
    private final String destination;
    private LocalDate departureDate;
    private int economyClassCapacity;
    private int firstClassCapacity;
    private int secondClassCapacity;
    private int occupiedEconomicSeats;
    private int occupiedFirstClassSeats;
    private int occupiedSecondClassSeats;

    public Flight(long number, String source, String destination, LocalDate departureDate, int economyClassCapacity,int firstClassCapacity,int secondClassCapacity, int occupiedEconomicSeats,int occupiedFirstClassSeats,int occupiedSecondClassSeats) {
        this.number = number;
        this.source = source;
        this.destination = destination;
        this.departureDate = departureDate;
        this.economyClassCapacity=economyClassCapacity;
        this.firstClassCapacity=firstClassCapacity;
        this.secondClassCapacity=secondClassCapacity;
        this.occupiedEconomicSeats=occupiedEconomicSeats;
        this.occupiedFirstClassSeats=occupiedFirstClassSeats;
        this.occupiedSecondClassSeats=occupiedSecondClassSeats;
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

    public int getTotalCapacity() {
        return (economyClassCapacity+firstClassCapacity+secondClassCapacity);
    }

    public int getTotalOccupiedSeats() {
        return (occupiedEconomicSeats+occupiedFirstClassSeats+occupiedSecondClassSeats);
    }

    public int getTotalAvailableSeats() {
        return (getTotalCapacity()-getTotalOccupiedSeats());
    }

    public void updateEconomicOccupiedSeats(int number) {
        if ((occupiedEconomicSeats+number) <= economyClassCapacity) {
            this.occupiedEconomicSeats += number;
        }
    }
    public void updateFirstClassOccupiedSeats(int number){
        if ((occupiedFirstClassSeats+number) <= firstClassCapacity) {
            this.occupiedFirstClassSeats += number;
        }
    }
    public void updateSecondClassOccupiedSeats(int number){
        if ((occupiedSecondClassSeats+number) <= secondClassCapacity) {
            this.occupiedSecondClassSeats += number;
        }
    }

    public int getEconomyClassCapacity() {
        return economyClassCapacity;
    }

    public int getFirstClassCapacity() {
        return firstClassCapacity;
    }

    public int getSecondClassCapacity() {
        return secondClassCapacity;
    }

    public int getOccupiedEconomicSeats() {
        return occupiedEconomicSeats;
    }

    public int getOccupiedFirstClassSeats() {
        return occupiedFirstClassSeats;
    }


    public int getOccupiedSecondClassSeats() {
        return occupiedSecondClassSeats;
    }
}
