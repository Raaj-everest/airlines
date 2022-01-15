package com.everest.airline.data;

import com.everest.airline.data.exceptions.FolderNotFoundException;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class FlightReader {

    public Flight stringToFlight(String flightData) {
        String[] flight = flightData.split(",");
        int flightNumber = Integer.parseInt(flight[0]);
        String source = flight[1];
        String destination = flight[2];
        String[] departureDate = flight[3].split("-");
        int year = Integer.parseInt(departureDate[0]);
        int month = Integer.parseInt(departureDate[1]);
        int day = Integer.parseInt(departureDate[2]);
        int economicSeatCapacity = Integer.parseInt(flight[4]);
        int firstClassSeatCapacity = Integer.parseInt(flight[5]);
        int businessClassSeatCapacity = Integer.parseInt(flight[6]);
        int occupiedEconomicSeats = Integer.parseInt(flight[7]);
        int occupiedFirstClassSeats = Integer.parseInt(flight[8]);
        int occupiedBusinessClassSeats = Integer.parseInt(flight[9]);
        double economyFare = Double.parseDouble(flight[10]);
        double firstClassFare = Double.parseDouble(flight[11]);
        double businessClassFare = Double.parseDouble(flight[12]);
        Cabin firstClass = new Cabin(firstClassSeatCapacity, occupiedFirstClassSeats, firstClassFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassSeatCapacity, occupiedBusinessClassSeats, businessClassFare,CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economicSeatCapacity, occupiedEconomicSeats, economyFare,CabinType.ECONOMIC);
        return new Flight(flightNumber, source, destination, LocalDate.of(year, month, day), firstClass, businessClass, economyClass);
    }

    public File[] getAll() {
        File directoryPath = new File("src/main/java/com/everest/airline/data/flightsData");
        FilenameFilter textFileFilter = (dir, name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".txt");
        };
        if (directoryPath.listFiles(textFileFilter) == null) {
            throw new FolderNotFoundException("The root Directory{Flight data} is Empty");
        }
        return directoryPath.listFiles(textFileFilter);
    }

    public File getFile(String name) {
        return Paths.get("src/main/java/com/everest/airline/data/flightsData/" + name + ".txt").toFile();
    }

    public String read(File file) throws IOException {
        return Files.lines(Paths.get(file.getPath())).collect(Collectors.toList()).get(0);
    }

}

