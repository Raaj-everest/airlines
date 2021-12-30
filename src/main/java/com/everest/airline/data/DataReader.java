package com.everest.airline.data;

import com.everest.airline.data.exceptions.FolderNotFoundException;
import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class DataReader {

    public List<Flight> getAllFlightsFromFiles() {
        List<Flight> flights = new ArrayList<>();
        List<String> data = getFilesInFolder();
        for (String f : data) {
            flights.add(stringToFlight(f));
        }
        return flights;
    }

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

    private List<String> getFilesInFolder() {
        try {
            return Files.walk(Paths.get("src/main/java/com/everest/airline/data/flightsData"))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile).map(file -> {
                        try (Scanner in = new Scanner(file)) {
                            return in.next();
                        } catch (FileNotFoundException e) {
                            return null;
                        }
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public File[] getListOfFiles() {
        File directoryPath = new File("src/main/java/com/everest/airline/data/flightsData");
        FilenameFilter textFileFilter = (dir, name) -> {
            String lowercaseName = name.toLowerCase();
            return lowercaseName.endsWith(".txt");
        };
        if(directoryPath.listFiles(textFileFilter)==null){
            throw new FolderNotFoundException("The root Directory{Flight data} is Empty");
        }
        return directoryPath.listFiles(textFileFilter);
    }

    public File getFile(String name){
        return Paths.get("src/main/java/com/everest/airline/data/flightsData/" + name+".txt").toFile();
    }

    public String readFile(File file) throws IOException {
        return Files.lines(Paths.get(file.getPath())).collect(Collectors.toList()).get(0);
    }

}

