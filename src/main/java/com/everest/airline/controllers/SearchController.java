package com.everest.airline.controllers;

import com.everest.airline.Search.SearchHelper;
import com.everest.airline.model.CabinTypes;
import com.everest.airline.model.Flight;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.everest.airline.data.DataReader.readFromFiles;
import static com.everest.airline.data.DataWriter.writingToFiles;

@Controller
public class SearchController {

    private List<Flight> searchedFlights;
    private String from;
    private String to;
    private LocalDate departureDate;
    private int numberOfPassengersBoarding;
    private CabinTypes classType;


    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType, Model model) throws IOException {
        if (from != null) {
            setValues(from, to, departureDate, numberOfPassengersBoarding, classType);
        }
        searchedFlights = SearchHelper.sourceToDestination(this.from, this.to, this.departureDate, this.numberOfPassengersBoarding, this.classType);
        if (searchedFlights.size() == 0) {
            return "noFlights";
        }
        model.addAttribute("flights", searchedFlights);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") String number, Model model) throws IOException {
        List<Flight> flights = readFromFiles().stream().filter(f -> f.getNumber() == Integer.parseInt(number)).collect(Collectors.toList());
        flights.get(0).updateOccupiedSeats(classType, this.numberOfPassengersBoarding);
        writingToFiles(flights.get(0));
        return "redirect:search";
    }

    public void setValues(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType) {
        this.from = from;
        this.to = to;
        this.departureDate = LocalDate.parse(departureDate);
        this.numberOfPassengersBoarding = Integer.parseInt(numberOfPassengersBoarding);
        this.classType = CabinTypes.valueOf(classType);
    }
}
