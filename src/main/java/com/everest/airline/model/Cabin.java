package com.everest.airline.model;

public class Cabin {
    private final int capacity;
    private int occupiedSeats;
    private double baseFare;

    public Cabin(int capacity, int occupiedSeats, double baseFare) {
        this.occupiedSeats = occupiedSeats;
        this.capacity = capacity;
        this.baseFare = baseFare;
    }

    public void updateFare(int percentage) {
       this.baseFare +=(this.baseFare *percentage)/100;

    }

    public double getBaseFare() {
        int percentage =  ((capacity - occupiedSeats) * 100) / capacity;
        if(percentage>70) {
            return (this.baseFare + (this.baseFare * 0 / 100));
        }
        if(percentage>50 && percentage<70){
            return (this.baseFare + (this.baseFare * 20 / 100));
        }
        if(percentage>25 && percentage<50){
            return (this.baseFare + (this.baseFare * 35 / 100));
        }
        if(percentage>0 && percentage<25){
            return  (this.baseFare + (this.baseFare * 50 / 100));
        }
        return baseFare;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public double getNominalFare() {
        return baseFare;
    }
    public void updateOccupiedSeats(int number) {
        if (occupiedSeats + number <= capacity) {
            occupiedSeats += number;
        }
    }
}
