package com.everest.airline.model;

public class Cabin {
    private final int capacity;
    private int occupiedSeats;
    private double fare;

    public Cabin(int capacity, int occupiedSeats, double baseFare) {
        this.occupiedSeats = occupiedSeats;
        this.capacity = capacity;
        this.fare = baseFare;
    }

    public void updateFare(int percentage) {
            this.fare =(this.fare + (this.fare * percentage / 100));
        System.out.println(fare);
        System.out.println("price incresed");

    }

    public double getFare() {
        return this.fare;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public void updateOccupiedSeats(int number) {
        if (occupiedSeats + number <= capacity) {
            occupiedSeats += number;
        }
    }
}
