package com.capstone.data.mapper;

import com.capstone.models.Project;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectMapper implements RowMapper<Project> {
    @Override
    public Project mapRow(ResultSet resultSet, int i) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("project_id"));
        project.setName(resultSet.getString("name"));
        project.setPriority(resultSet.getString("priority"));
        return project;
    }
}
