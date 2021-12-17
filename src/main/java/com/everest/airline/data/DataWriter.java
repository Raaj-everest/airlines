package com.everest.airline.data;

import com.everest.airline.model.CabinTypes;
import com.everest.airline.model.Flight;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class DataWriter {

    public static void writingToFiles(Flight flight) throws IOException {
        String Filename = String.valueOf(flight.getNumber());
        String FileFormat = ".txt";
        String FlightFolder = Filename + FileFormat;
        List<File> files = DataReader.getFilesInFolder().stream().filter(file -> (FlightFolder.equals(file.getName()))).collect(Collectors.toList());
        FileWriter myWriter = new FileWriter(files.get(0));
        myWriter.write(flight.toString());
        myWriter.close();
    }

}
