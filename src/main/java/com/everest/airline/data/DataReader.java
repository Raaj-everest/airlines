package com.everest.airline.data;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
@Component
public class DataReader {

    public List<Flight> readFromFiles() {
        List<Flight> flights = new ArrayList<>();
        List<String > data = getFilesInFolder();
        for (String f: data){
           flights.add(stringToFlight(f));
        }
        return flights;
    }

    private Flight stringToFlight(String flightData) {
        String[] flight = flightData.split(",");
        int flightNumber = Integer.parseInt(flight[0]);
        String source = flight[1];
        String destination = flight[2];
        String[] departureDate = flight[3].split("-");
        int year = Integer.parseInt(departureDate[0]);
        int month = Integer.parseInt(departureDate[1]);
        int day = Integer.parseInt(departureDate[2]);
        int economicSeatCapacity = Integer.parseInt(flight[4]);
        int FirstClassSeatCapacity = Integer.parseInt(flight[5]);
        int secondClassSeatCapacity = Integer.parseInt(flight[6]);
        int occupiedEconomicSeats = Integer.parseInt(flight[7]);
        int occupiedFirstClassSeats = Integer.parseInt(flight[8]);
        int occupiedSecondClassSeats = Integer.parseInt(flight[9]);
        double economyFare = Double.parseDouble(flight[10]);
        double firstClassFare = Double.parseDouble(flight[11]);
        double secondClassFare = Double.parseDouble(flight[12]);
        return new Flight(flightNumber, source, destination, LocalDate.of(year, month, day), economicSeatCapacity, FirstClassSeatCapacity, secondClassSeatCapacity, occupiedEconomicSeats, occupiedFirstClassSeats, occupiedSecondClassSeats, economyFare, firstClassFare, secondClassFare);
    }

    private List<String> getFilesInFolder()  {
        List<String> data = new ArrayList<>();
        try {
           List<File> files = Files.walk(Paths.get("src/main/java/com/everest/airline/data/flightsData"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
            for (File file : files) {
                Scanner in = new Scanner(file);
                String flightData = in.next();
                data.add(flightData);
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}
