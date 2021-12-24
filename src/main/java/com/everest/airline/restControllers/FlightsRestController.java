package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.everest.airline.data.DataReader.readFromFiles;
import static com.everest.airline.data.DataWriter.writingToFiles;


@RestController
public class FlightsRestController {

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        return readFromFiles();
    }
    @GetMapping("/flights/{number}")
    public List<Flight> getFlights(@PathVariable int number) throws IOException {
        return readFromFiles().stream().filter(flight -> flight.getNumber()==number).collect(Collectors.toList());
    }

    @PostMapping("/flights")
    public long create(  String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer secondClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedSecondClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double secondClassBaseFare) throws IOException {
        List<Flight> data = readFromFiles();
        System.out.println("sdcsefwvervevebv");
        data.sort(new sortById());
        Long numberId=  data.get(data.size()-1).getNumber();
        System.out.println("phase 2 ");
        numberId++;
        File file1 = new File("src/main/java/com/everest/airline/data/flightsData/" + numberId + ".txt");
        file1.createNewFile();
        System.out.println("fole created");
        System.out.println(numberId);
        System.out.println(source);
        System.out.println(destination);
        System.out.println(departureDate);
        System.out.println(economyClassCapacity);
        System.out.println(firstClassCapacity);
        System.out.println(secondClassCapacity);
        System.out.println(occupiedEconomicSeats);
        System.out.println(occupiedFirstClassSeats);
        System.out.println(occupiedSecondClassSeats);
        System.out.println(economyClassBaseFare);
        System.out.println(firstClassBaseFare);
        System.out.println(secondClassBaseFare);
        writingToFiles(new Flight( numberId, source,  destination,  LocalDate.parse(departureDate),  economyClassCapacity,  firstClassCapacity,  secondClassCapacity,  occupiedEconomicSeats,  occupiedFirstClassSeats, occupiedSecondClassSeats,  economyClassBaseFare,  firstClassBaseFare, secondClassBaseFare));
        return numberId;
    }
    public static class sortById implements Comparator<Flight> {
        public int compare(Flight a, Flight b)
        {
            return Math.toIntExact(a.getNumber() - b.getNumber());
        }
    }
//
//    // Update
//    @PutMapping("/flights/{number}")
//    public Flight update(@PathVariable long number, String source, String destination) {
//        Flight existingFlight = Data.flights.stream()
//                .filter(f -> f.getNumber() == number)
//                .findFirst()
//                .orElse(null);
//        // Get the file
//        // Update the file
//        // Response updated data
//        Flight flight = new Flight(number, source, destination);
//        Data.flights.set(Data.flights.indexOf(existingFlight), flight);
//        return flight;
//    }
//
//    // Update
//    @DeleteMapping("/flights/{number}")
//    public void delete(@PathVariable long number) {
//        // Delete the file
//    }
}