package com.everest.airline;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Data {
//    public static List<Flight> flights = List.of(
//            new Flight(1001, "Hyderabad", "Bangalore", LocalDate.of(2021, Month.DECEMBER, 6), 9),
//            new Flight(1002, "Bangalore", "Hyderabad", LocalDate.of(2021, Month.DECEMBER, 7), 8),
//            new Flight(1003, "Goa", "Bangalore", LocalDate.of(2021, Month.DECEMBER, 8), 7),
//            new Flight(1004, "Bangalore", "Goa", LocalDate.of(2021, Month.DECEMBER, 9), 6),
//            new Flight(1005, "Bangalore", "Hyderabad", LocalDate.of(2021, Month.DECEMBER, 10), 8),
//            new Flight(1006, "Hyderabad", "Bangalore", LocalDate.of(2021, Month.DECEMBER, 6), 5));

    public static List<Flight> readDataFromFile() throws FileNotFoundException {

        Scanner in = new Scanner(new FileReader("/Users/raaj/projects/airlines/src/main/java/com/everest/airline/data.txt"));
        List<Flight> flightsFromFile = new ArrayList<>();
        String flightData;
        while (in.hasNextLine()) {
            flightData = in.next();
            String[] flight = flightData.split(",");
            int flightNumber = Integer.parseInt(flight[0]);
            String source = flight[1];
            String destination = flight[2];
            String[] departureDate = flight[3].split("-");
            int year = Integer.parseInt(departureDate[0]);
            int month = Integer.parseInt(departureDate[1]);
            int day = Integer.parseInt(departureDate[2]);
            int occupiedSeats = Integer.parseInt(flight[4]);
            flightsFromFile.add(new Flight(flightNumber, source, destination, LocalDate.of(year, month, day), occupiedSeats));
        }
        return flightsFromFile;
    }
}
