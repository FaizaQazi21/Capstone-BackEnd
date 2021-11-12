package com.capstone.data.mapper;


import com.capstone.models.Status;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusMapper implements RowMapper<Status>{
    @Override
    public Status mapRow(ResultSet resultSet, int i) throws SQLException {
        Status status = new Status();

        status.setId(resultSet.getInt("status_id"));
        status.setStatus(resultSet.getString("status"));


        return status;
    }
}
