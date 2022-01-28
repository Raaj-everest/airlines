package com.everest.airline.exeptions;

public class TicketCalculationException extends RuntimeException {
    public TicketCalculationException(String message) {
        super(message);
    }
}
