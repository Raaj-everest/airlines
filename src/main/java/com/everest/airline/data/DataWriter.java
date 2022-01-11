package com.everest.airline.data;

import com.everest.airline.data.exceptions.FileNotCreatedException;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

@Component
public class DataWriter {

    @Autowired
    DataReader dataReader;

    public void writingToFiles(Flight flight, File file) throws IOException {
        FileWriter myWriter = new FileWriter(file);
        myWriter.write(flight.toString());
        myWriter.close();
    }

    public File createFile(long number) throws IOException {
        File file1 = new File("src/main/java/com/everest/airline/data/flightsData/" + number + ".txt");
        if (file1.createNewFile()) {
            return file1;
        }
        throw new FileNotCreatedException("Failed to create the file");
    }

    public long generateNUmber(){
        File[] data = dataReader.getListOfFiles();
        Arrays.sort(data);
        String name = data[data.length - 1].getName();
        long numberId = Long.parseLong(name.split("\\.")[0]);
        numberId++;
        return numberId;
    }
}
