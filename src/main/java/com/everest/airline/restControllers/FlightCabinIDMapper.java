package com.everest.airline.restControllers;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class FlightCabinIDMapper implements RowMapper<Long> {
    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getLong("cabin_id");
    }
}
