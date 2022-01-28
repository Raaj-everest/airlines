package com.everest.airline.repositories;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.repositories.mappers.CabinIDMapper;
import com.everest.airline.repositories.mappers.FlightNumberMapper;
import com.everest.airline.repositories.mappers.FlightRowMappper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FlightRepository {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public List<Flight> getAll() {
        return jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight inner join cabin cf on flight.first_class=cf.id inner join cabin cb on flight.business_class=cb.id inner join cabin ce on flight.economic_class=ce.id", new FlightRowMappper());
    }

    public Flight getFlight(Long number) {
        Map<String, Integer> map = new HashMap<>();
        map.put("flight_number", number.intValue());
        return jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight" +
                " inner join cabin cf on flight.first_class=cf.id" +
                " inner join cabin cb on flight.business_class=cb.id " +
                " inner join cabin ce on flight.economic_class=ce.id" +
                " where number=:flight_number", map, new FlightRowMappper()).get(0);
    }

    public List<Flight> search(String from, String to, LocalDate departureDate) {
        SqlParameterSource map = new MapSqlParameterSource()
                .addValue("from", from)
                .addValue("to", to)
                .addValue("departureDate", departureDate);
        return jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight" +
                " inner join cabin cf on flight.first_class=cf.id" +
                " inner join cabin cb on flight.business_class=cb.id" +
                " inner join cabin ce on flight.economic_class=ce.id" +
                " where source=:from and destination=:to and departureDate=:departureDate", map, new FlightRowMappper());
    }

    @Transactional
    public String write(Flight flight) {
        Cabin firstClass = flight.getFirstClass();
        Cabin businessClass = flight.getBusinessClass();
        Cabin economyClass = flight.getEconomyClass();
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(firstClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(businessClass));
        jdbcTemplate.update("insert into cabin (capacity,occupied,available,fare,type) values(:capacity,:occupiedSeats,:availableSeats,:baseFare,:type)", cabinMapper(economyClass));
        List<Long> CabinID = jdbcTemplate.query("select id from cabin order by id desc limit 3", new CabinIDMapper());
        jdbcTemplate.update("insert into flight (source,destination,departureDate,first_class,business_class,economic_class) values(:source,:destination,:departureDate,:firstClass_id,:businessClass_id,:economyClass_id)", flightMapper(flight, CabinID));
        List<Long> flightID = jdbcTemplate.query("select number from flight order by number desc limit 1", new FlightNumberMapper());
        return "flight with ID : " + flightID.get(0) + " created successfully";
    }

    @Transactional
    public String update(Flight flight) {
        Cabin firstClass = flight.getFirstClass();
        Cabin businessClass = flight.getBusinessClass();
        Cabin economyClass = flight.getEconomyClass();
        Map<String, Long> map = new HashMap<>();
        map.put("flightID", flight.getNumber());
        List<Long> CabinID = jdbcTemplate.query("select first_class,business_class,economic_class from flight where number=:flightID ", map, (rs, rowNum) -> {
            List<Long> arr = new ArrayList<>();
            arr.add(rs.getLong("first_class"));
            arr.add(rs.getLong("business_class"));
            arr.add(rs.getLong("economic_class"));
            return arr;
        }).get(0);
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(firstClass, CabinID.get(0)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(businessClass, CabinID.get(1)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(economyClass, CabinID.get(2)));
        jdbcTemplate.update("update flight set source=:source,destination=:destination,departureDate=:departureDate,first_class=:firstClass_id,business_class=:businessClass_id,economic_class=:economyClass_id where number=:number", flightMapper(flight, CabinID));
        return "flight with id : " + flight.getNumber() + " updated successfully";
    }

    @Transactional
    public String remove(long number) {
        Map<String, Long> mapper = new HashMap<String, Long>();
        mapper.put("flight_number", number);
        List<Long> id = jdbcTemplate.query("select first_class,business_class,economic_class from flight where number=:flight_number ", mapper, (rs, rowNum) -> {
            List<Long> arr = new ArrayList<>();
            arr.add(rs.getLong("first_class"));
            arr.add(rs.getLong("business_class"));
            arr.add(rs.getLong("economic_class"));
            return arr;
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


    private SqlParameterSource flightMapper(Flight flight, List<Long> CabinId) {
        return new MapSqlParameterSource()
                .addValue("number", flight.getNumber())
                .addValue("source", flight.getSource())
                .addValue("destination", flight.getDestination())
                .addValue("departureDate", flight.getDepartureDate())
                .addValue("firstClass_id", CabinId.get(2))
                .addValue("businessClass_id", CabinId.get(1))
                .addValue("economyClass_id", CabinId.get(0));

    }
}
