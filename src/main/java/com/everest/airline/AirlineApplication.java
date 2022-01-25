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
    }

}
