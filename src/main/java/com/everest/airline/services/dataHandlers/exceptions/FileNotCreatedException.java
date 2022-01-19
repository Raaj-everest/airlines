package com.everest.airline.services.dataHandlers.exceptions;

public class FileNotCreatedException extends RuntimeException {
    public FileNotCreatedException(String message) {
        super(message);
    }
}
