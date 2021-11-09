package com.capstone.data;

import com.capstone.data.mapper.RoleMapper;
import com.capstone.models.Roles;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class RoleJdbcRepositoryTemplate implements RoleRepository{

    private final JdbcTemplate jdbcTemplate;

    public RoleJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Roles> findAll() {
        final String sql = "select * from role;";
        return jdbcTemplate.query(sql, new RoleMapper());

    }

    @Override
    public Roles findById(int id) {
        final String sql = "select * from role where role_id = ?;";
        return jdbcTemplate.query(sql, new RoleMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Roles add(Roles roles) {
        final String sql = "insert into role (role, access_level) values(?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, roles.getRole());
            ps.setString(2, roles.getAccess_level());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        roles.setId(keyHolder.getKey().intValue());
        return roles;

    }

    @Override
    public boolean update(Roles roles) {
        final String sql = "update role set role = ? , access_level = ? where role_id = ?;";

        return jdbcTemplate.update(sql, roles.getRole(), roles.getAccess_level(), roles.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from role where role_id = ?", id) > 0;
    }
}
