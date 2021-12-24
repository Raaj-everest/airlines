package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.everest.airline.data.DataReader.readFromFiles;


@RestController
public class FlightsRestController {

    @GetMapping("/flights")
    public List<Flight> getAllFlights(@PathVariable Optional<String> number) throws IOException {
        return readFromFiles();
    }
    @GetMapping("/flights/{number}")
    public List<Flight> getFlights(@PathVariable int number) throws IOException {
        return readFromFiles().stream().filter(flight -> flight.getNumber()==number).collect(Collectors.toList());
    }

    // CUD
//    @PostMapping("/flights")
//    public long create(String source, String destination) {
//        // 1009 = Get the last flight id
//        Flight flight = new Flight(1110, source, destination);
//
//        // create the file
//        // write to file
//        Data.flights.add(flight);
//        return flight.getNumber();
//    }
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