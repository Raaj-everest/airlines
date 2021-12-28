package com.everest.airline.data;

import com.everest.airline.model.Flight;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataWriter {

    public void writingToFiles(Flight flight) throws IOException {
        String Filename = String.valueOf(flight.getNumber());
        String FileFormat = ".txt";
        String FlightFolder = Filename + FileFormat;
        File files = Paths.get("src/main/java/com/everest/airline/data/flightsData/" + FlightFolder).toFile();
        FileWriter myWriter = new FileWriter(files);
        myWriter.write(flight.toString());
        myWriter.close();
//        List<File> files = Files.walk(Paths.get("src/main/java/com/everest/airline/data/flightsData"))
//                .filter(Files::isRegularFile)
//                .map(Path::toFile)
//                .collect(Collectors.toList());

    }

}
