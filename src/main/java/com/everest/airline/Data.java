package com.everest.airline;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Data {

    public static List<File> getFilesInFolder() throws IOException {
        return Files.walk(Paths.get("/Users/raaj/projects/airlines/src/main/java/com/everest/airline/flights"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

    public static List<Flight> readFromFiles() throws IOException {
        List<File> files = getFilesInFolder();
        List<Flight> flightsFromFile = new ArrayList<>();
        for (File file : files) {
            String flightData;
            Scanner in = new Scanner(file);
            flightData = in.nextLine();
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

    public static void writingToFiles(Flight flight) throws IOException {
        String Filename = String.valueOf(flight.getNumber());
        String FileFormat = ".txt";
        String FlightFolder = Filename + FileFormat;
        List<File> files = getFilesInFolder().stream().filter(file -> (FlightFolder.equals(file.getName()))).collect(Collectors.toList());
        FileWriter myWriter = new FileWriter(files.get(0));
        myWriter.write(String.valueOf(flight.getNumber()));
        myWriter.write(",");
        myWriter.write(flight.getSource());
        myWriter.write(",");
        myWriter.write(flight.getDestination());
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getDepartureDate().getYear()));
        myWriter.write("-");
        myWriter.write(String.valueOf(flight.getDepartureDate().getMonthValue()));
        myWriter.write("-");
        myWriter.write(String.valueOf(flight.getDepartureDate().getDayOfMonth()));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getCapacity()));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getTotalOccupiedSeats()));
        myWriter.write("\n");
        myWriter.close();
    }

}
