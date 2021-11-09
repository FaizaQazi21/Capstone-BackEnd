package com.capstone.data;


import com.capstone.data.mapper.LogMapper;
import com.capstone.models.Log;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LogJdbcRepositoryTemplate implements LogRepository{

    private final JdbcTemplate jdbcTemplate;

    public LogJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Log> findAll() {
        final String sql = "select * from log;";
        return jdbcTemplate.query(sql, new LogMapper());
    }

    @Override
    public Log findById(int id) {
        final String sql = "select * from log where log_id = ?;";
        return jdbcTemplate.query(sql, new LogMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Log add(Log log) {
        final String sql = "insert into log (user_id, comment) values(?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, log.getUser_id());
            ps.setString(2, log.getComment());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        log.setId(keyHolder.getKey().intValue());
        return log;
    }

    @Override
    public boolean update(Log log) {
        final String sql = "update log set user_id = ? , comment = ? where log_id = ?;";

        return jdbcTemplate.update(sql, log.getUser_id(), log.getComment(), log.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from log where log_id = ?", id) > 0;
    }
}
