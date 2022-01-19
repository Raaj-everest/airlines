package com.everest.airline.controllers;

import com.everest.airline.services.dataHandlers.FlightReader;
import com.everest.airline.services.dataHandlers.FlightWriter;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
public class SearchController {

    @Autowired
    FlightReader flightReader;

    @Autowired
    FlightWriter flightWriter;

    private String from;
    private String to;
    private LocalDate departureDate;
    private int numberOfPassengersBoarding;
    private CabinType classType;


    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType, Model model) throws IOException {
        System.out.println(from+to+departureDate+numberOfPassengersBoarding+classType);
        if (from != null) {
            setValues(from, to, departureDate, numberOfPassengersBoarding, classType);
        }

        List<Flight> data = new ArrayList<>();
            File[] files = flightReader.getAll();
            for (File file : files) {
                String flightData = flightReader.read(file);
                Flight flight = flightReader.stringToFlight(flightData);
                data.add(flight);
            }
            List<Flight> searchedFlights = data.stream()
                .filter(flight -> (flight.getSource().equalsIgnoreCase(this.from) && flight.getDestination().equalsIgnoreCase(this.to) && flight.getDepartureDate().equals(this.departureDate) && (flight.checkAvailability(this.classType, this.numberOfPassengersBoarding))))
                .collect(Collectors.toList());
        if (searchedFlights.size() == 0) {
            return "noFlights";
        }
        model.addAttribute("flights", searchedFlights);
        model.addAttribute("CabinType", classType);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") String number, Model model) throws IOException {
        File file = flightReader.getFile(number);
        String flightData = flightReader.read(file);
        Flight flight = flightReader.stringToFlight(flightData);
        flight.updateOccupiedSeats(classType, numberOfPassengersBoarding);
        flightWriter.write(flight,file);
        return "confirmed";
    }

    public void setValues(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType) {
        this.from = from;
        this.to = to;
        this.departureDate = LocalDate.parse(departureDate);
        this.numberOfPassengersBoarding = Integer.parseInt(numberOfPassengersBoarding);
        this.classType = CabinType.valueOf(classType);
    }
}
