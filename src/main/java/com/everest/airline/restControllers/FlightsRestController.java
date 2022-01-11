package com.everest.airline.restControllers;

import com.everest.airline.data.DataReader;
import com.everest.airline.data.DataWriter;
import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.types.BusinessClass;
import com.everest.airline.model.cabins.types.EconomyClass;
import com.everest.airline.model.cabins.types.FirstClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class FlightsRestController {

    @Autowired
    private DataReader dataReader;

    @Autowired
    private DataWriter dataWriter;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        List<Flight> data = new ArrayList<>();
            File[] files = dataReader.getListOfFiles();
            for (File file : files) {
                String flightData = dataReader.readFile(file);
                Flight flight = dataReader.stringToFlight(flightData);
                data.add(flight);
            }
            return data;
    }

    @GetMapping("/flights/{number}")
    public Flight getFlights(@PathVariable long number) throws IOException {
        File file = dataReader.getFile(String.valueOf(number));
        String flightData = dataReader.readFile(file);
        Flight flight = dataReader.stringToFlight(flightData);
        if(flight==null) {
            throw new FlightNotFoundException("No flight found with the resource you are entered");
        }
        return flight;
    }

    @PostMapping("/flights")
    public long create(@PathVariable Optional<String> number, String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer businessBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
        long numberId;
        System.out.println(firstClassCapacity);
        System.out.println(occupiedFirstClassSeats);
        System.out.println(firstClassBaseFare);
        Cabin firstClass = new FirstClass(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare);
        Cabin businessClass = new BusinessClass(businessClassCapacity, businessBusinessClassSeats, businessClassBaseFare);
        Cabin economyClass = new EconomyClass(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare);
        if (number.isEmpty()) {
            File file = dataWriter.createFile();
            String name = file.getName();
            numberId = Long.parseLong(name.split("\\.")[0]);
            dataWriter.writingToFiles(new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass), file);
            return numberId;
        }
        numberId = Long.parseLong(String.valueOf(number));
        dataWriter.writingToFiles(new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass), dataReader.getFile(String.valueOf(numberId)));
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
}