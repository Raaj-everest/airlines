package com.everest.airline.Search;

import com.everest.airline.Data;
import com.everest.airline.Flight;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.everest.airline.Data.readDataFromFile;

public class SearchHelper {

//    public static List<Flight> sourceToDestination(String from, String to, LocalDate departureDate, Integer ticket) {
//        return Data.flights.stream()
//                .filter(flight -> (flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getAvailableSeats()))))
//                .collect(Collectors.toList());
//    }
    public static List<Flight> sourceToDestination(String from, String to, LocalDate departureDate, Integer ticket) throws FileNotFoundException {
        return readDataFromFile().stream()
                .filter(flight -> (flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getAvailableSeats()))))
                .collect(Collectors.toList());
    }

}

