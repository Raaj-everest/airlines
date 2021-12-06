package com.everest.airline.Search;

import com.everest.airline.Data;
import com.everest.airline.Flight;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchHelper {

    public static List<Flight> sourceToDestination(String from, String to, LocalDate departureDate, Integer ticket) {
        List<Flight> searched = new ArrayList<>();
        searched = Data.flights.stream()
                .filter(flight -> (flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getAvailableSeats()))))
                .collect(Collectors.toList());
        return searched;
    }
}

