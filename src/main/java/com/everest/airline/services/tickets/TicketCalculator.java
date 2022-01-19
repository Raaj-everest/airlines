package com.everest.airline.services.tickets;

import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.exceptions.FLightAccommodationException;

import java.time.LocalDate;

public class TicketCalculator {
    public double price(LocalDate date, Cabin c) {
        double cabinCost = cabinPrice(c);
        LocalDate now = LocalDate.now();
        int differenceInDays = Math.abs(now.compareTo(date));
        if (differenceInDays > 15) {
            return (int) cabinCost;
        } else {
            return calculateFare(cabinCost, differenceInDays);
        }
    }

    private double cabinPrice(Cabin c) {
        int percentage = ((c.getCapacity() - c.getOccupiedSeats()) * 100) / c.getCapacity();
        if (percentage >= 70) {
            return (c.getBaseFare() + (c.getBaseFare() * 0 / 100));
        } else if (percentage >= 50) {
            return (c.getBaseFare() + (c.getBaseFare() * 20 / 100));
        } else if (percentage >= 25) {
            return (c.getBaseFare() + (c.getBaseFare() * 35 / 100));
        } else if (percentage >= 0) {
            return (c.getBaseFare() + (c.getBaseFare() * 50 / 100));
        }
        throw new FLightAccommodationException("Error while cabin calculating ticketCost for" + this.getClass());
    }

    private double calculateFare(double fare, int differenceInDays) {
        double currentFare;
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
        throw new TicketCalculationError("Error while calculating ticket cost");
    }
}
