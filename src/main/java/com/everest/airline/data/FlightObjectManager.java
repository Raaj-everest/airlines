package com.everest.airline.data;

import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class FlightObjectManager {
    @Autowired
    private DataReader dataReader;

    @Autowired
    private DataWriter dataWriter;


    public List<Flight> getAllFlights() {
        return dataReader.getAllFlightsFromFiles();
    }

    public Flight getFlight(long number) {
        return getAllFlights().stream().filter(flight -> flight.getNumber() == number).collect(Collectors.toList()).get(0);
    }

    public void addFlight(Flight flight) {
        File file1 = new File("src/main/java/com/everest/airline/data/flightsData/" + flight.getNumber() + ".txt");
        try {
            file1.createNewFile();
            dataWriter.writingToFiles(flight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFlight(long number) {

    }


}
