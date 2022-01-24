package com.everest.airline.restControllers;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FlightDataMapper implements RowMapper<ArrayList> {
    @Override
    public ArrayList mapRow(ResultSet rs, int rowNum) throws SQLException {
        ArrayList<Object> arr = new ArrayList<Object>();
        arr.add(rs.getLong("number"));
        arr.add(rs.getString("source"));
        arr.add(rs.getString("destination"));
        arr.add(rs.getDate("departureDate").toLocalDate());
        return arr;
    }
}
