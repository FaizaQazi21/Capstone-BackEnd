package com.capstone.data.mapper;

import com.capstone.models.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskMapper implements RowMapper<Task>{
    @Override
    public Task mapRow(ResultSet resultSet, int i) throws SQLException {
        Task task = new Task();
        task.setId(resultSet.getInt("task_id"));
        task.setName(resultSet.getString("name"));
        task.setUser_id((Integer) resultSet.getObject("user_id"));
        task.setStart_time(resultSet.getTimestamp("start_time"));
        task.setTotal_hours(resultSet.getDouble("total_hours"));
        task.setStatus_id(resultSet.getInt("status_id"));
        task.setProject_id(resultSet.getInt("project_id"));
        task.setNotes(resultSet.getString("note"));
        task.setNotes(resultSet.getString("task_description"));

        return task;
    }
}
