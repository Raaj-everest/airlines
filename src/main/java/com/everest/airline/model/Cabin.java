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
       this.fare+=(this.fare*percentage)/100;

    }

    public double getFare() {
        int percentage =  ((capacity - occupiedSeats) * 100) / capacity;
        System.out.println(percentage);
        if(percentage>70) {
            return (this.fare + (this.fare * 0 / 100));
        }
        if(percentage>50 && percentage<70){
            return (this.fare + (this.fare * 20 / 100));
        }
        if(percentage>25 && percentage<50){
            return (this.fare + (this.fare * 35 / 100));
        }
        if(percentage>0 && percentage<25){
            return  (this.fare + (this.fare * 50 / 100));
        }
        return fare;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public double getBaseFare() {
        return fare;
    }
    public void updateOccupiedSeats(int number) {
        if (occupiedSeats + number <= capacity) {
            occupiedSeats += number;
        }
    }
}
