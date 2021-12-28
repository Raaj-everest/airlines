package com.everest.airline.restControllers;

import com.everest.airline.data.DataReader;
import com.everest.airline.data.DataWriter;
import com.everest.airline.data.FlightObjectManager;
import com.everest.airline.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@RestController
public class FlightsRestController {

    @Autowired
    private DataReader dataReader;

    @Autowired
    private DataWriter dataWriter;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        List<Flight> data = dataReader.readFromFiles();
        data.sort(new sortById());
        return data;
    }

    @GetMapping("/flights/{number}")
    public Flight getFlights(@PathVariable long number) throws IOException {
        return dataReader.readFromFiles().stream().filter(flight -> flight.getNumber()==number).collect(Collectors.toList()).get(0);
    }

    @PostMapping("/flights")
    public long create(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer secondClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedSecondClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double secondClassBaseFare) throws IOException {
        List<Flight> data = dataReader.readFromFiles();
        data.sort(new sortById());
        Long numberId=  data.get(data.size()-1).getNumber();
        numberId++;
        File file1 = new File("src/main/java/com/everest/airline/data/flightsData/" + numberId + ".txt");
        file1.createNewFile();
        dataWriter.writingToFiles(new Flight( numberId, source,  destination,  LocalDate.parse(departureDate),  economyClassCapacity,  firstClassCapacity,  secondClassCapacity,  occupiedEconomicSeats,  occupiedFirstClassSeats, occupiedSecondClassSeats,  economyClassBaseFare,  firstClassBaseFare, secondClassBaseFare));
        return numberId;
    }


    @DeleteMapping("/flights/{number}")
    public String delete(@PathVariable long number) throws IOException {
        List<File> files =  Files.walk(Paths.get("src/main/java/com/everest/airline/data/flightsData"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());
        for(File f: files){
            if(f.getName().equals(number+".txt")){
                f.delete();
            }
        }
        return ("Flight : "+number +" deleted successfully");
    }

    public static class sortById implements Comparator<Flight> {
        public int compare(Flight a, Flight b) {
            return Math.toIntExact(a.getNumber() - b.getNumber());
        }
    }
}