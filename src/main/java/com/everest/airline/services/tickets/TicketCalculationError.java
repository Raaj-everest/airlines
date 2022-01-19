package com.everest.airline.services.tickets;

public class TicketCalculationError extends RuntimeException {
    public TicketCalculationError(String message) {
        super(message);
    }
}
