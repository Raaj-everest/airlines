package com.everest.airline.data;

import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;


public class FlightObjectManager {
    @Autowired
    private DataReader dataReader;
    public List<Flight> data = dataReader.readFromFiles();
    @Autowired
    private DataWriter dataWriter;

    public List<Flight> getAllFlights() {
        return data;
    }

    public Flight getFlight(long number) {
        return data.stream().filter(flight -> flight.getNumber() == number).collect(Collectors.toList()).get(0);
    }

    public void addFlight(Flight){

    }

}
