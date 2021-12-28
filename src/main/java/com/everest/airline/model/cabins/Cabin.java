package com.everest.airline.model.cabins;

import com.everest.airline.model.cabins.exceptions.FLightAccommodationException;

public abstract class Cabin {

    private final int capacity;
    private int occupiedSeats;
    private double baseFare;

    public Cabin(int capacity, int occupiedSeats, double baseFare) {
        if (occupiedSeats <= capacity) {
            this.occupiedSeats = occupiedSeats;
            this.capacity = capacity;
            this.baseFare = baseFare;
        } else throw new FLightAccommodationException("OccupiedSeats are greater than capacity of" + this.getClass());
    }

    public void updateFare(int percentage) {
        this.baseFare += (this.baseFare * percentage) / 100;

    }

    public double ticketCost() {
        int percentage = ((capacity - occupiedSeats) * 100) / capacity;
        if (percentage >= 70) {
            return (this.baseFare + (this.baseFare * 0 / 100));
        } else if (percentage >= 50) {
            return (this.baseFare + (this.baseFare * 20 / 100));
        } else if (percentage >= 25) {
            return (this.baseFare + (this.baseFare * 35 / 100));
        } else if (percentage >= 0) {
            return (this.baseFare + (this.baseFare * 50 / 100));
        }
        throw new FLightAccommodationException("Error while cabin calculating ticketCost for" + this.getClass());
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void updateOccupiedSeats(int number) {
        if (occupiedSeats + number <= capacity) {
            occupiedSeats += number;
        } else throw new FLightAccommodationException("Flight capacity exceeded for the " + this.getClass());
    }
}
