package com.everest.airline.restControllers.mappers;

import com.everest.airline.model.cabins.Cabin;
import com.everest.airline.model.cabins.CabinType;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CabinCreator implements RowMapper<Cabin> {
    @Override
    public Cabin mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Cabin(rs.getInt("capacity"),rs.getInt("occupied"),rs.getInt("fare"), CabinType.valueOf(rs.getString("type")));
    }
}
