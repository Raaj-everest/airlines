package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;


@RestController
public class FlightsRestController {

    @Autowired
    private FlightRepository manager;


    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable long number) throws IOException {
        return manager.getFlight(number);
    }

    @GetMapping("/flights")
    public List<Flight> getFlight() {
        return manager.getAll();
    }

    @PutMapping("/flights")
    public String create(String source,
                         String destination,
                         String departureDate,
                         Integer economyClassCapacity,
                         Integer firstClassCapacity,
                         Integer businessClassCapacity,
                         Integer occupiedEconomicSeats,
                         Integer occupiedFirstClassSeats,
                         Integer occupiedBusinessClassSeats,
                         Integer economyClassBaseFare,
                         Double firstClassBaseFare,
                         Double businessClassBaseFare) {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economicClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        long temporaryID = 0;
        return manager.write(new Flight(temporaryID, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economicClass));
    }

    @PostMapping("/flights/{number}")
    public String update(@PathVariable long number,
                         String source,
                         String destination,
                         String departureDate,
                         Integer economyClassCapacity,
                         Integer firstClassCapacity,
                         Integer businessClassCapacity,
                         Integer occupiedEconomicSeats,
                         Integer occupiedFirstClassSeats,
                         Integer occupiedBusinessClassSeats,
                         Integer economyClassBaseFare,
                         Double firstClassBaseFare,
                         Double businessClassBaseFare) {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economicClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        return manager.update(new Flight(number, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economicClass));
    }

    @DeleteMapping("/flights/{number}")
    public String delete(@PathVariable long number) {
        return manager.remove(number);
    }

}