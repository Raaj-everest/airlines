package com.everest.airline.controllers;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import com.everest.airline.restControllers.mappers.FlightRowMappper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class SearchController {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    private String from;
    private String to;
    private LocalDate departureDate;
    private int numberOfPassengersBoarding;
    private CabinType classType;


    @RequestMapping(value = "/search")
    public String search(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType, Model model) {
        if (from != null) {
            setValues(from, to, departureDate, numberOfPassengersBoarding, classType);
        }
        SqlParameterSource map = new MapSqlParameterSource()
                .addValue("from", this.from)
                .addValue("to", this.to)
                .addValue("departureDate", this.departureDate);

        List<Flight> data = jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight inner join cabin cf on flight.first_class=cf.id inner join cabin cb on flight.business_class=cb.id  inner join cabin ce on flight.economic_class=ce.id where source=:from and destination=:to and departureDate=:departureDate", map, new FlightRowMappper());
        model.addAttribute("flights", data);
        model.addAttribute("CabinType", classType);
        return "search";
    }

    @RequestMapping(value = "/{number}")
    public String book(@PathVariable("number") String number, Model model) {
        Map<String, Long> map = new HashMap<>();
        map.put("number", Long.parseLong(number));
        Flight flight = jdbcTemplate.query("select number,source,destination,departureDate,cf.*,cb.*,ce.* from flight inner join cabin cf on flight.first_class=cf.id inner join cabin cb on flight.business_class=cb.id  inner join cabin ce on flight.economic_class=ce.id where number=:number", map, new FlightRowMappper()).get(0);
        flight.updateOccupiedSeats(classType, numberOfPassengersBoarding);
        List<Long> id = jdbcTemplate.query("select first_class,business_class,economic_class from flight where number=:number ", map, new RowMapper<List<Long>>() {
            @Override
            public List<Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Long> arr = new ArrayList<>();
                arr.add(rs.getLong("first_class"));
                arr.add(rs.getLong("business_class"));
                arr.add(rs.getLong("economic_class"));
                return arr;
            }
        }).get(0);
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(flight.getFirstClass(), id.get(0)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(flight.getBusinessClass(), id.get(1)));
        jdbcTemplate.update("update cabin set capacity=:capacity,occupied=:occupiedSeats,available=:availableSeats,fare=:baseFare,type=:type where id=:id", cabinMapper(flight.getEconomyClass(), id.get(2)));
        SqlParameterSource relation = new MapSqlParameterSource()
                .addValue("number", flight.getNumber())
                .addValue("source", flight.getSource())
                .addValue("destination", flight.getDestination())
                .addValue("departureDate", flight.getDepartureDate())
                .addValue("firstClass_id", id.get(0))
                .addValue("businessClass_id", id.get(1))
                .addValue("economyClass_id", id.get(2));
        jdbcTemplate.update("update flight set source=:source,destination=:destination,departureDate=:departureDate,first_class=:firstClass_id,business_class=:businessClass_id,economic_class=:economyClass_id where number=:number", relation);
        return "confirmed";
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

    public void setValues(String from, String to, String departureDate, String numberOfPassengersBoarding, String classType) {
        this.from = from;
        this.to = to;
        this.departureDate = LocalDate.parse(departureDate);
        this.numberOfPassengersBoarding = Integer.parseInt(numberOfPassengersBoarding);
        this.classType = CabinType.valueOf(classType);
    }
}
