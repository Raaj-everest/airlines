package com.everest.airline;

import java.io.*;
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
            flightData = in.nextLine();
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
    public static void writeToFile(String from, String to, LocalDate departureDate, Integer ticket) {
        try {
            List<Flight> ff = Data.readDataFromFile();
            for (Flight flight:ff
                 ) {
                if (flight.getSource().equals(from) && flight.getDestination().equals(to) && flight.getDepartureDate().equals(departureDate) && (ticket <= (flight.getAvailableSeats()))){
                    flight.setOccupiedSeats(ticket);
                }
            }
            FileWriter myWriter = new FileWriter("/Users/raaj/projects/airlines/src/main/java/com/everest/airline/data.txt");
            for (Flight f:
                    ff ) {
                myWriter.write(String.valueOf(f.getNumber()));
                myWriter.write(",");
                myWriter.write(f.getSource());
                myWriter.write(",");
                myWriter.write(f.getDestination());
                myWriter.write(",");
                myWriter.write(String.valueOf(f.getDepartureDate().getYear()));
                myWriter.write("-");
                myWriter.write(String.valueOf(f.getDepartureDate().getMonthValue()));
                myWriter.write("-");
                myWriter.write(String.valueOf(f.getDepartureDate().getDayOfMonth()));
                myWriter.write(",");
                myWriter.write(String.valueOf(f.getOccupiedSeats()));
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
