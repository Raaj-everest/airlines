package com.everest.airline.restControllers;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class dataManager {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public long write(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        List<Integer> flightNumber = jdbcTemplate.query("select number from flight order by number desc limit 1", new FlightNumberMapper());
        long flightID = (flightNumber.get(0) + 1);
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        Flight flight = new Flight(flightID, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(firstClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(businessClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(economyClass));
        jdbcTemplate.update("insert into flight (number,source,destination,departureDate) values(:number,:source,:destination,:departureDate)", flightMapper(flight));
        List<Long> id = jdbcTemplate.query("select id from cabin order by id desc limit 3", new cabinIDMapper());
        HashMap<String, Long> relation = new HashMap<>();
        relation.put("firstClass_id", id.get(2));
        relation.put("businessClass_id", id.get(1));
        relation.put("economyClass_id", id.get(0));
        relation.put("flight_number", flightID);
        jdbcTemplate.update("insert into flight_cabin (flight_number,cabin_id) values(:flight_number,:firstClass_id)", relation);
        jdbcTemplate.update("insert into flight_cabin (flight_number,cabin_id) values(:flight_number,:businessClass_id)", relation);
        jdbcTemplate.update("insert into flight_cabin (flight_number,cabin_id) values(:flight_number,:economyClass_id)", relation);
        return flightID;
    }

    public String update(String number, String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        long flightID = Long.parseLong(number);
        Map<String, Long> map = new HashMap<>();
        map.put("flightID", flightID);
        List<Long> id = jdbcTemplate.query("select cabin_id from flight_cabin where flight_number =:flightID ", map, new FlightCabinIDMapper());
        Flight flight = new Flight(flightID, source, destination, LocalDate.parse(departureDate), firstClass, businessClass, economyClass);
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(firstClass, id.get(2)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(businessClass, id.get(1)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(economyClass, id.get(0)));
        jdbcTemplate.update("update flight set number=:number,source=:source,destination=:destination,departureDate=:departureDate where number=:number", flightMapper(flight, flightID));
        return "updated flight with id : " + number + " successfully";
    }

    public Flight getFlight(Long number) {
        Map<String, Integer> map = new HashMap<>();
        map.put("flight_number", number.intValue());
        System.out.println(map.get("flight_number"));
        List<Cabin> cabins = jdbcTemplate.query("select cabin.* from flight_cabin inner join cabin on flight_cabin.cabin_id=cabin.id where flight_cabin.flight_number =:flight_number", map, new CabinCreator());
        List<ArrayList> flightData = jdbcTemplate.query("select * from flight where number=:flight_number", map, new FlightDataMapper());
        return new Flight((long) flightData.get(0).get(0), (String) flightData.get(0).get(1), (String) flightData.get(0).get(2), (LocalDate) flightData.get(0).get(3), cabins.get(0), cabins.get(1), cabins.get(2));
    }

    private SqlParameterSource flightMapper(Flight flight) {
        return new MapSqlParameterSource()
                .addValue("number", flight.getNumber())
                .addValue("source", flight.getSource())
                .addValue("destination", flight.getDestination())
                .addValue("departureDate", flight.getDepartureDate());
    }

    private SqlParameterSource flightMapper(Flight flight, long number) {
        return new MapSqlParameterSource()
                .addValue("number", flight.getNumber())
                .addValue("source", flight.getSource())
                .addValue("destination", flight.getDestination())
                .addValue("departureDate", flight.getDepartureDate())
                .addValue("number", number);
    }

    private SqlParameterSource cabinMapper(Cabin cabin) {
        return new MapSqlParameterSource()
                .addValue("capacity", cabin.getCapacity())
                .addValue("occupiedSeats", cabin.getOccupiedSeats())
                .addValue("availableSeats", (cabin.getCapacity() - cabin.getOccupiedSeats()))
                .addValue("baseFare", cabin.getBaseFare())
                .addValue("type", cabin.getCabinType().toString());
    }

    private SqlParameterSource cabinMapper(Cabin cabin, long number) {
        return new MapSqlParameterSource()
                .addValue("capacity", cabin.getCapacity())
                .addValue("occupiedSeats", cabin.getOccupiedSeats())
                .addValue("availableSeats", (cabin.getCapacity() - cabin.getOccupiedSeats()))
                .addValue("baseFare", cabin.getBaseFare())
                .addValue("type", cabin.getCabinType().toString())
                .addValue("id", number);
    }

    public String remove(long number) {
        Map<String, Long> mapper = new HashMap<String, Long>();
        mapper.put("flight_number", number);
        List<Long> id = jdbcTemplate.query("select cabin_id from flight_cabin where flight_number=:flight_number", mapper, new FlightCabinIDMapper());
        mapper.put("first_class", id.get(0));
        mapper.put("business_class", id.get(1));
        mapper.put("economic_class", id.get(2));
        jdbcTemplate.update("delete from flight where number=:flight_number", mapper);
        jdbcTemplate.update("delete from cabin where id=:first_class",mapper);
        jdbcTemplate.update("delete from cabin where id=:business_class",mapper);
        jdbcTemplate.update("delete from cabin where id=:economic_class",mapper);
        jdbcTemplate.update("delete from flight_cabin where flight_number=:flight_number",mapper);
        return "flight with ID : "+number+ " deleted successfully";

    }
}
