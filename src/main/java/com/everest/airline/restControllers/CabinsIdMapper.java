//package com.everest.airline.restControllers;
//
//import org.springframework.jdbc.core.RowMapper;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class CabinsIdMapper implements RowMapper<Long> {
//    @Override
//    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return rs.getLong("first_class"),rs.getLong("business_class"),rs.getLong("economic_class");
//    }
//}
