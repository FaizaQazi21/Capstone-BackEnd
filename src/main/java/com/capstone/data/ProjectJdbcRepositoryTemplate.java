package com.capstone.data;

import com.capstone.data.mapper.ProjectMapper;
import com.capstone.models.Project;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class ProjectJdbcRepositoryTemplate implements ProjectRepository{

    private final JdbcTemplate jdbcTemplate;

    public ProjectJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Project> findAll() {
        final String sql = "select * from project;";
        return jdbcTemplate.query(sql, new ProjectMapper());
    }

    @Override
    public Project findById(int id) {
        final String sql = "select * from project where project_id = ?;";
        return jdbcTemplate.query(sql, new ProjectMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Project add(Project project) {
        final String sql = "insert into project (name, project_description,priority) values(?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, project.getName());
            ps.setString(1, project.getProject_description());
            ps.setString(3, project.getPriority());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        project.setId(keyHolder.getKey().intValue());
        return project;
    }

    @Override
    public boolean update(Project project) {
        final String sql = "update project set name = ? , project_description =? ,priority = ? where project_id = ?;";

        return jdbcTemplate.update(sql, project.getName(), project.getProject_description(), project.getPriority(), project.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from project where project_id = ?", id) > 0;
    }
}
