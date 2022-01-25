package com.everest.airline.services;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.restControllers.FlightNotFoundException;
import com.everest.airline.restControllers.mappers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class dataManager {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional
    public String write(String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(firstClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(businessClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(economyClass));
        List<Long> id = jdbcTemplate.query("select id from cabin order by id desc limit 3", new CabinIDMapper());
        SqlParameterSource relation = new MapSqlParameterSource()
                .addValue("source", source)
                .addValue("destination", destination)
                .addValue("departureDate", departureDate)
                .addValue("firstClass_id", id.get(2))
                .addValue("businessClass_id", id.get(1))
                .addValue("economyClass_id", id.get(0));
        jdbcTemplate.update("insert into flight (source,destination,departureDate,first_class,business_class,economic_class) values(:source,:destination,:departureDate,:firstClass_id,:businessClass_id,:economyClass_id)", relation);
        List<Long> flightID = jdbcTemplate.query("select number from flight order by number desc limit 1", new FlightNumberMapper());
        return "flight with ID : " + flightID.get(0) + " created successfully";
    }

    @Transactional
    public String update(long number, String source, String destination, String departureDate, Integer economyClassCapacity, Integer firstClassCapacity, Integer businessClassCapacity, Integer occupiedEconomicSeats, Integer occupiedFirstClassSeats, Integer occupiedBusinessClassSeats, Integer economyClassBaseFare, Double firstClassBaseFare, Double businessClassBaseFare) {
        Cabin firstClass = new Cabin(firstClassCapacity, occupiedFirstClassSeats, firstClassBaseFare, CabinType.FIRST);
        Cabin businessClass = new Cabin(businessClassCapacity, occupiedBusinessClassSeats, businessClassBaseFare, CabinType.BUSINESS);
        Cabin economyClass = new Cabin(economyClassCapacity, occupiedEconomicSeats, economyClassBaseFare, CabinType.ECONOMIC);
        long flightID = number;
        Map<String, Long> map = new HashMap<>();
        map.put("flightID", flightID);
       List<Long> flightIDConfirmation = jdbcTemplate.query("select number from flight where number=:flightID",map,new FlightNumberMapper());
        if(flightIDConfirmation.isEmpty()){
            throw new FlightNotFoundException("There is no flight assosiated with the Id :"+flightID);
        }
        List<Long> id = jdbcTemplate.query("select first_class,business_class,economic_class from flight where number=:flightID ", map, new RowMapper<List<Long>>() {
            @Override
            public List<Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Long> arr = new ArrayList<>();
                arr.add(rs.getLong("first_class"));
                arr.add(rs.getLong("business_class"));
                arr.add(rs.getLong("economic_class"));
                return arr;
            }
        }).get(0);
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(firstClass, id.get(0)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(businessClass, id.get(1)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(economyClass, id.get(2)));
        SqlParameterSource relation = new MapSqlParameterSource()
                .addValue("number", flightID)
                .addValue("source", source)
                .addValue("destination", destination)
                .addValue("departureDate", departureDate)
                .addValue("firstClass_id", id.get(0))
                .addValue("businessClass_id", id.get(1))
                .addValue("economyClass_id", id.get(2));
        jdbcTemplate.update("update flight set source=:source,destination=:destination,departureDate=:departureDate,first_class=:firstClass_id,business_class=:businessClass_id,economic_class=:economyClass_id where number=:number", relation);
        return "flight with id : " + number + " updated successfully";
    }

    public Flight getFlight(Long number) {
        Map<String, Integer> map = new HashMap<>();
        map.put("flight_number", number.intValue());
        return jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight inner join cabin cf on flight.first_class=cf.id inner join cabin cb on flight.business_class=cb.id  inner join cabin ce on flight.economic_class=ce.id where number=:flight_number",map,new FlightRowMappper()).get(0);
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
                .addValue("departureDate", flight.getDepartureDate());
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

    @Transactional
    public String remove(long number) {
        Map<String, Long> mapper = new HashMap<String, Long>();
        mapper.put("flight_number", number);
        List<Long> id = jdbcTemplate.query("select first_class,business_class,economic_class from flight where number=:flight_number ", mapper, new RowMapper<List<Long>>() {
            @Override
            public List<Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Long> arr = new ArrayList<>();
                arr.add(rs.getLong("first_class"));
                arr.add(rs.getLong("business_class"));
                arr.add(rs.getLong("economic_class"));
                return arr;
            }
        }).get(0);
        mapper.put("first_class", id.get(0));
        mapper.put("business_class", id.get(1));
        mapper.put("economic_class", id.get(2));
        jdbcTemplate.update("delete from flight where number=:flight_number", mapper);
        jdbcTemplate.update("delete from cabin where id=:first_class", mapper);
        jdbcTemplate.update("delete from cabin where id=:business_class", mapper);
        jdbcTemplate.update("delete from cabin where id=:economic_class", mapper);
        return "flight with ID : " + number + " deleted successfully";

    }

    public List<Flight> getAll() {
        return jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight inner join cabin cf on flight.first_class=cf.id inner join cabin cb on flight.business_class=cb.id  inner join cabin ce on flight.economic_class=ce.id",new FlightRowMappper());
    }
}
