package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import com.everest.airline.services.dataHandlers.FlightReader;
import com.everest.airline.services.dataHandlers.FlightWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class FlightsRestController {

    @Autowired
    private FlightReader dataReader;

    @Autowired
    private FlightWriter dataWriter;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @GetMapping("/flights")
    public List<Flight> getAllFlights() throws IOException {
        return jdbcTemplate.query(" select number,source,destination,departuredate," +
                "a.occupiedSeats,a.Capacity,a.basefare," +
                "b.occupiedSeats,b.Capacity,b.basefare," +
                "c.occupiedSeats,c.Capacity,c.basefare from flight" +
                " left join cabin a on flight.firstclass=a.id " +
                "left join cabin b on flight.businessclass=b.id" +
                " left join cabin c  on flight.economicclass=c.id;", new FlightRowMapper());
    }

    @GetMapping("/flights/{number}")
    public List<Flight> getFlights(@PathVariable long number) throws IOException {
        Map<String, Object> map = new HashMap();
        map.put("number",number);
        System.out.println(number);
        return jdbcTemplate.query(" select number,source,destination,departuredate," +
                "a.occupiedSeats,a.Capacity,a.basefare," +
                "b.occupiedSeats,b.Capacity,b.basefare," +
                "c.occupiedSeats,c.Capacity,c.basefare from flight" +
                " left join cabin a on flight.firstclass=a.id " +
                "left join cabin b on flight.businessclass=b.id" +
                " left join cabin c  on flight.economicclass=c.id where number=:number;", map,new FlightRowMapper());
    }
//
//    @PostMapping("/flights")
//    public long create(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer businessBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
//        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
//        Cabin businessClass = new Cabin(businessClassCapacity, businessBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
//        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
//        long numberId = dataWriter.generateNUmber();
//        Flight flight = new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
//        File file = dataWriter.create(numberId);
//        dataWriter.write(flight, file);
//        return numberId;
//    }
//
//
//    @DeleteMapping("/flights/{number}")
//    public String delete(@PathVariable long number) throws IOException {
//        File file = dataReader.getFile(String.valueOf(number));
//        if (file.delete()) {
//            return ("Flight : " + number + " deleted successfully");
//        }
//        throw new FlightNotFoundException("Unable to locate the flight to delete");
//    }
//
//    @PutMapping("/flights")
//    public long create(String number, String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer businessBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) throws IOException {
//        long numberId;
//        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
//        Cabin businessClass = new Cabin(businessClassCapacity, businessBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
//        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
//        numberId = Long.parseLong(String.valueOf(number));
//        Flight flight = new Flight(numberId, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
//        dataWriter.write(flight, dataReader.getFile(String.valueOf(numberId)));
//        return numberId;
//    }
}