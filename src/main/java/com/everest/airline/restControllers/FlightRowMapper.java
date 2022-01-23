package com.everest.airline.restControllers;


import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class FlightRowMapper implements RowMapper<Flight> {

    @Override
    public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cabin firstclass = new Cabin(rs.getInt("a.capacity"), rs.getInt("a.occupiedseats"), rs.getInt("a.basefare"), CabinType.FIRST);
        Cabin secondclass = new Cabin(rs.getInt("b.capacity"), rs.getInt("b.occupiedseats"), rs.getInt("b.basefare"), CabinType.BUSINESS);
        Cabin economicclass = new Cabin(rs.getInt("c.capacity"), rs.getInt("c.occupiedseats"), rs.getInt("c.basefare"), CabinType.ECONOMIC);
        LocalDate departure = rs.getDate("departureDate").toLocalDate();
        return new Flight(rs.getInt("number"), rs.getString("source"), rs.getString("destination"), departure, firstclass, secondclass, economicclass);
    }
}
