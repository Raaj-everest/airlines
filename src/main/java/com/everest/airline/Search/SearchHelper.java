package com.everest.airline.Search;

import com.everest.airline.model.Cabin;
import com.everest.airline.model.CabinTypes;
import com.everest.airline.model.Flight;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.everest.airline.data.DataReader.readFromFiles;

public class SearchHelper {

    public static List<Flight> sourceToDestination(String from, String to, LocalDate departureDate, int ticket, CabinTypes cabinType) throws IOException {
        return readFromFiles().stream()
                .filter(flight -> (flight.getSource().equalsIgnoreCase(from) && flight.getDestination().equalsIgnoreCase(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getAvailableSeats(cabinType)))))
                .collect(Collectors.toList());
    }
}

