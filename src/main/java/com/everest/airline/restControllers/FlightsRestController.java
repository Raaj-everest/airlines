package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import com.everest.airline.services.dataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
public class FlightsRestController {

    @Autowired
    private dataManager manager;

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable long number) throws IOException {
        return manager.getFlight(number);
    }

    @GetMapping("/flights")
    public List<Flight> getFlight() throws IOException {
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
        return manager.write(source, destination, departureDate,
                economyClassCapacity, firstClassCapacity, businessClassCapacity,
                occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats,
                economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
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
        return manager.update(number, source, destination, departureDate,
                economyClassCapacity, firstClassCapacity, businessClassCapacity,
                occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats,
                economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
    }

    @DeleteMapping("/flights/{number}")
    public String delete(@PathVariable long number) {
        return manager.remove(number);
    }

}