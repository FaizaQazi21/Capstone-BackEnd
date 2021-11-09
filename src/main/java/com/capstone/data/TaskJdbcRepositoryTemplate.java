package com.capstone.data;


import com.capstone.data.mapper.TaskMapper;
import com.capstone.models.Task;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class TaskJdbcRepositoryTemplate implements  TaskRepository{
    private final JdbcTemplate jdbcTemplate;

    public TaskJdbcRepositoryTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Task> findAll() {
        final String sql = "select * from task;";
        return jdbcTemplate.query(sql, new TaskMapper());
    }

    @Override
    public Task findById(int id) {
        final String sql = "select * from task where task_id = ?;";
        return jdbcTemplate.query(sql, new TaskMapper(), id).stream().findAny().orElse(null);
    }

    @Override
    public Task add(Task task) {
        final String sql = "insert into task " +
                "(name, user_id, start_time, end_time, status, project_id, note) " +
                "values(?,?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setInt(2, task.getUser_id());
            ps.setTime(3, task.getStart_time());
            ps.setTime(4, task.getEnd_time());
            ps.setString(5, task.getStatus());
            ps.setInt(6, task.getProject_id());
            ps.setString(7, task.getNotes());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        task.setId(keyHolder.getKey().intValue());
        return task;
    }

    @Override
    public boolean update(Task task) {
        final String sql = "update task set " +
                "name = ?, user_id = ?, start_time = ?, end_time = ?, status =? , project_id =?, note =? " +
                "where task_id = ?;";

        return jdbcTemplate.update(sql,
                task.getName(), task.getUser_id(), task.getStart_time(), task.getEnd_time(), task.getStatus(), task.getProject_id(), task.getNotes(), task.getId()) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from task where task_id = ?", id) > 0;
    }
}
