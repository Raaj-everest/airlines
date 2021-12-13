package com.everest.airline.data;

import com.everest.airline.model.Flight;

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

public class DataReader {

    public static List<Flight> readFromFiles() throws IOException {
        List<File> files = getFilesInFolder();
        List<Flight> flightsFromFile = new ArrayList<>();
        for (File file : files) {
            Scanner in = new Scanner(file);
            String flightData = in.next();
            in.close();
            String[] flight = flightData.split(",");
            int flightNumber = Integer.parseInt(flight[0]);
            String source = flight[1];
            String destination = flight[2];
            String[] departureDate = flight[3].split("-");
            int year = Integer.parseInt(departureDate[0]);
            int month = Integer.parseInt(departureDate[1]);
            int day = Integer.parseInt(departureDate[2]);
            int capacity = Integer.parseInt(flight[4]);
            int occupiedSeats = Integer.parseInt(flight[5]);
            flightsFromFile.add(new Flight(flightNumber, source, destination, LocalDate.of(year, month, day), capacity, occupiedSeats));
        }
        return flightsFromFile;
    }

    static List<File> getFilesInFolder() throws IOException {
        return Files.walk(Paths.get("/Users/raaj/projects/airlines/src/main/java/com/everest/airline/data/flightsData"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }
}
