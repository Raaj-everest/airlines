package com.everest.airline.Search;

import com.everest.airline.Flight;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.everest.airline.Data.readFromFiles;

public class SearchHelper {

    public static List<Flight> sourceToDestination(String from, String to, LocalDate departureDate, Integer ticket) throws IOException {
        return readFromFiles().stream()
                .filter(flight -> (flight.getSource().equalsIgnoreCase(from) && flight.getDestination().equalsIgnoreCase(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getTotalAvailableSeats()))))
                .collect(Collectors.toList());
    }
}

