package com.capstone.data.mapper;

import com.capstone.models.Log;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LogMapper implements RowMapper<Log>{
    @Override
    public Log mapRow(ResultSet resultSet, int i) throws SQLException {
        Log log = new Log();
        log.setId(resultSet.getInt("log_id"));
        log.setUser_id(resultSet.getInt("user_id"));
        log.setComment(resultSet.getString("comment"));

        return log;
    }
}
