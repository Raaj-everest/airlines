package com.everest.airline.restControllers.mappers;

import com.everest.airline.model.Flight;
import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FlightRowMappper implements RowMapper<Flight> {
    @Override
    public Flight mapRow(ResultSet rs, int rowNum) throws SQLException {
        Cabin first = new Cabin(rs.getInt("cf.capacity"),rs.getInt("cf.occupied"),rs.getInt("cf.fare"), CabinType.valueOf(rs.getString("cf.type")));
        Cabin business = new Cabin(rs.getInt("cb.capacity"),rs.getInt("cb.occupied"),rs.getInt("cb.fare"), CabinType.valueOf(rs.getString("cb.type")));
        Cabin economic = new Cabin(rs.getInt("ce.capacity"),rs.getInt("ce.occupied"),rs.getInt("ce.fare"), CabinType.valueOf(rs.getString("ce.type")));
        return new Flight(rs.getLong("number"),rs.getString("source"),rs.getString("destination"),rs.getDate("departureDate").toLocalDate(),first,business,economic);
    }
}
