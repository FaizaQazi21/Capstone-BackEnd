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
    public Task findByUser(int userId) {
        final String sql = "select * from task where user_id = ?;";
        return jdbcTemplate.query(sql, new TaskMapper(), userId).stream().findAny().orElse(null);
    }

    @Override
    public Task add(Task task) {
        final String sql = "insert into task " +
                "(name, user_id, start_time, total_hours, status_id, project_id, note, task_description) " +
                "values(?,?, ?, ?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, task.getName());
            ps.setObject(2, task.getUser_id());
            ps.setTimestamp(3, task.getStart_time());
            ps.setString(4, task.getTotal_hours());
            ps.setInt(5, task.getStatus_id());
            ps.setInt(6, task.getProject_id());
            ps.setString(7, task.getNotes());
            ps.setString(8, task.getTask_description());
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
                "name = ?, user_id = ?, start_time = ?, total_hours = ?, status_id =? , project_id =?, note =?, task_description=? " +
                "where task_id = ?;";

        return jdbcTemplate.update(sql,
                task.getName(), task.getUser_id(), task.getStart_time(), task.getTotal_hours(), task.getStatus_id(),
                task.getProject_id(), task.getNotes(), task.getTask_description(), task.getId()) > 0;
    }

    @Override
    public boolean updateTotalHours(Task task) {

        final int start = 1;
        final int paused = 2;
        final int inProgress =3;
        final int completed =4;
        String sql;

        if (task.getStatus_id() == start){ //change status from start to in progress
            sql = "update task set start_time = ?, user_id = ?, status_id = ?," +
                    " note = ?, task_description = ? where task_id = ?;";

            return jdbcTemplate.update(sql, task.getStart_time(), task.getUser_id(), inProgress,
                    task.getNotes(),task.getTask_description(), task.getId()) > 0;
        }else if(task.getStatus_id() == paused){

            sql = "update task set total_hours = ?, status_id = ?, note = ?," +
                    "task_description = ? where task_id = ?;";
            return jdbcTemplate.update(sql, task.getTotal_hours(), paused,task.getNotes(),
                    task.getTask_description(), task.getId()) > 0;
        }else { //the last possible status is 4
            //update total_hours, status_id to completed, note, task_description
            sql = "update task set total_hours = ?, status_id = ?, note = ?," +
                    "task_description = ? where task_id = ?;";
            return jdbcTemplate.update(sql, task.getTotal_hours(), completed, task.getNotes(),
                    task.getTask_description(), task.getId()) > 0;
        }
    }

    @Override
    public boolean deleteById(int id) {
        return jdbcTemplate.update(
                "delete from task where task_id = ?", id) > 0;
    }
}
