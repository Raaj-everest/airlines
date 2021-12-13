package com.everest.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

import static com.everest.airline.Data.readFromFiles;
import static com.everest.airline.Data.writingToFiles;

@SpringBootApplication
public class AirlineApplication {



	public static void main(String[] args) throws IOException {
		SpringApplication.run(AirlineApplication.class, args);
	}



}
