package com.everest.airline.controllers;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;


@Controller
public class SearchController {

    @Autowired
    FlightRepository flightRepository;
    private String from;
    private String to;
    private LocalDate departureDate;
    private int numberOfPassengersBoarding;
    private CabinType classType;

    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType, Model model) {

        if (from != null) {
            setValues(from, to, departureDate, numberOfPassengersBoarding, classType);
        }

        List<Flight> data = flightRepository.search(this.from, this.to, this.departureDate);
        model.addAttribute("flights", data);
        model.addAttribute("CabinType", this.classType);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") String number, Model model) {
        Flight flight = flightRepository.getFlight(Long.parseLong(number));
        flight.updateOccupiedSeats(this.classType, this.numberOfPassengersBoarding);
        flightRepository.update(flight);
        return "confirmed";
    }

    private void setValues(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType) {
        this.from = from;
        this.to = to;
        this.departureDate = LocalDate.parse(departureDate);
        this.numberOfPassengersBoarding = Integer.parseInt(numberOfPassengersBoarding);
        this.classType = CabinType.valueOf(classType);
    }
}
