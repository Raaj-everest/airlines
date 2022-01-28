package com.everest.airline.model.cabins;

import com.everest.airline.exeptions.FLightAccommodationException;

public class Cabin {

    private final int capacity;
    private final CabinType cabinType;
    private int occupiedSeats;
    private double baseFare;

    public Cabin(int capacity, int occupiedSeats, double baseFare, CabinType cabinType) {
        if (occupiedSeats <= capacity) {
            this.occupiedSeats = occupiedSeats;
            this.capacity = capacity;
            this.baseFare = baseFare;
            this.cabinType = cabinType;
        } else throw new FLightAccommodationException("OccupiedSeats are greater than capacity of" + this.getClass());
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

    public int getAvailableSeats() {
        return capacity - occupiedSeats;
    }

    public void updateOccupiedSeats(int number) {
        if (occupiedSeats + number <= capacity) {
            occupiedSeats += number;
        } else throw new FLightAccommodationException("Flight capacity exceeded for the " + this.getClass());
    }

    public CabinType getCabinType() {
        return cabinType;
    }
}
