package com.everest.airline;

import com.everest.airline.services.dataHandlers.FlightReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;


@SpringBootApplication
public class AirlineApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(AirlineApplication.class, args);
        FlightReader f = new FlightReader();
        File F =Paths.get("src/main/java/com/everest/airline/data/flightsData/1001.txt").toFile();
        File g = f.getFile("1001");
        System.out.println(f.read(g));
    }

}
