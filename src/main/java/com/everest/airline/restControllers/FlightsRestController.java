package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class FlightsRestController {

    @Autowired
    private dataManager u;

    @GetMapping("/flights/{number}")
    public Flight getFlight(@PathVariable long number) throws IOException {
        return u.getFlight(number);
    }

    @PutMapping("/flights")
    public long create( String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        return u.write( source, destination, departureDate, economyClassCapacity, firstClassCapacity, businessClassCapacity, occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats, economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
    }

    @PostMapping("/flights")
    public String update( String number,String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        return u.update( number,source, destination, departureDate, economyClassCapacity, firstClassCapacity, businessClassCapacity, occupiedEconomicSeats, occupiedFirstClassSeats, occupiedBusinessClassSeats, economyClassBaseFare, firstClassBaseFare, businessClassBaseFare);
    }

    @DeleteMapping("/flights/{number}")
    public String delete(@PathVariable long number){
        return u.remove(number);
    }

}