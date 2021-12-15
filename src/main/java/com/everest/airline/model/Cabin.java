package com.everest.airline.model;

public class Cabin {
    private final int capacity;
    private int occupiedSeats;
    private int fare;

    public Cabin(int capacity, int occupiedSeats, int baseFare) {
        this.occupiedSeats = occupiedSeats;
        this.capacity = capacity;
        this.fare = baseFare;
    }

    public void updateFare(int percentage) {
        if (percentage > 0) {
            this.fare += (this.fare * (percentage / 100));
        } else {
            this.fare -= (this.fare * (percentage / 100));
        }
    }

    public int getFare() {
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
