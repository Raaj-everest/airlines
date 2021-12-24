package com.everest.airline;

import com.everest.airline.model.Flight;
import com.everest.airline.restControllers.FlightsRestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.everest.airline.data.DataReader.readFromFiles;

@SpringBootApplication
public class AirlineApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AirlineApplication.class, args);

//        List<Flight> data = readFromFiles();
//        for (Flight f:data
//             ) {
//            System.out.println(f.getNumber());
//        }
//        data.sort(new FlightsRestController.sortById());
//        for (Flight f:data
//        ) {
//            System.out.println(f.getNumber());
//        }
//        List<Flight> data = readFromFiles();
//        data.sort(new FlightsRestController.sortById());
//        Long numberId=  data.get(data.size()-1).getNumber();
//        numberId++;
//        File file1
//                = new File("src/main/java/com/everest/airline/data/flightsData/" + numberId + ".txt");
//        file1.createNewFile();
////        Flight flight = new Flight(1110, source, destination);
//        System.out.println("file created");
//        System.out.println(file1.getPath());
    }
}
