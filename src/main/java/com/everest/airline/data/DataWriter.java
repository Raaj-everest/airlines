package com.everest.airline.data;

import com.everest.airline.model.Cabin;
import com.everest.airline.model.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class DataWriter {

    public static void writingToFiles(Flight flight) throws IOException {
        String Filename = String.valueOf(flight.getNumber());
        String FileFormat = ".txt";
        String FlightFolder = Filename + FileFormat;
        List<File> files = DataReader.getFilesInFolder().stream().filter(file -> (FlightFolder.equals(file.getName()))).collect(Collectors.toList());
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
        myWriter.write(String.valueOf(flight.getCapacity(Cabin.ECONOMIC)));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getCapacity(Cabin.FIRST)));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getCapacity(Cabin.SECOND)));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getOccupiedSeats(Cabin.ECONOMIC)));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getOccupiedSeats(Cabin.FIRST)));
        myWriter.write(",");
        myWriter.write(String.valueOf(flight.getOccupiedSeats(Cabin.SECOND)));
        myWriter.write("\n");
        myWriter.close();
    }
}
