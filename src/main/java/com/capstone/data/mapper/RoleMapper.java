package com.capstone.data.mapper;

import com.capstone.models.Roles;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RoleMapper implements RowMapper<Roles> {

    @Override
    public Roles mapRow(ResultSet resultSet, int i) throws SQLException {
        Roles roles = new Roles();
        roles.setId(resultSet.getInt("role_id"));
        roles.setRole(resultSet.getString("role"));
        roles.setAccess_level(resultSet.getString("access_level"));
        return roles;
    }
}

