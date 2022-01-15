package com.everest.airline.restControllers;

import com.everest.airline.data.FlightReader;
import com.everest.airline.data.FlightWriter;
import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@RestController
public class FlightsRestController {

    @Autowired
    private FlightReader dataReader;

    @Autowired
    private FlightWriter dataWriter;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        List<Flight> data = new ArrayList<>();
        File[] files = dataReader.getAll();
        for (File file : files) {
            String flightData = dataReader.read(file);
            Flight flight = dataReader.stringToFlight(flightData);
            data.add(flight);
        }
        return data;
    }

    @GetMapping("/flights/{number}")
    public Flight getFlights(@PathVariable long number) throws IOException {
        File file = dataReader.getFile(String.valueOf(number));
        String flightData = dataReader.read(file);
        Flight flight = dataReader.stringToFlight(flightData);
        if (flight == null) {
            throw new FlightNotFoundException("No flight found with the resource you are entered");
        }
        return flight;
    }

    @PostMapping("/flights")
    public long create(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer businessBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, businessBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        long numberId = dataWriter.generateNUmber();
        Flight flight = new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
        File file = dataWriter.create(numberId);
        dataWriter.write(flight, file);
        return numberId;
    }


    @DeleteMapping("/flights/{number}")
    public String delete(@PathVariable long number) throws IOException {
        File file = dataReader.getFile(String.valueOf(number));
        if (file.delete()) {
            return ("Flight : " + number + " deleted successfully");
        }
        throw new FlightNotFoundException("Unable to locate the flight to delete");
    }

    @PutMapping("/flights")
    public long create(String number, String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer businessBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
        long numberId;
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, businessBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        numberId = Long.parseLong(String.valueOf(number));
        Flight flight = new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
        dataWriter.write(flight, dataReader.getFile(String.valueOf(numberId)));
        return numberId;
    }
}