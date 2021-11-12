package com.capstone.data;

import com.capstone.data.mapper.StatusMapper;
import com.capstone.models.Status;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class StatusJdbcRepositoryTemplate implements StatusRepository{

    private final JdbcTemplate jdbcTemplate;

    public StatusJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Status> findAll() {
        final String sql = "select * from status;";
        return jdbcTemplate.query(sql, new StatusMapper());
    }

    @Override
    public Status findById(int id) {
        final String sql = "select * from status where status_id = ?;";
        return jdbcTemplate.query(sql, new StatusMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Status add(Status status) {
        final String sql = "insert into status " +
                "(status) " +
                "values(?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, status.getStatus());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        status.setId(keyHolder.getKey().intValue());
        return status;
    }

    @Override
    public boolean update(Status status) {
        final String sql = "update status set " +
                "status = ? " +
                "where status_id = ?;";

        return jdbcTemplate.update(sql,
                status.getStatus(), status.getId()) > 0;

    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from status where status_id = ?", id) > 0;
    }
}
