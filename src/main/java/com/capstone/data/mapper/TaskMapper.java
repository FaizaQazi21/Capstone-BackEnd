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
        task.setUser_id(resultSet.getInt("user_id"));
        task.setStart_time(resultSet.getTime("start_time"));
        task.setEnd_time(resultSet.getTime("end_time"));
        task.setStatus(resultSet.getString("status"));
        task.setProject_id(resultSet.getInt("project_id"));
        task.setNotes(resultSet.getString("note"));

        return task;
    }
}
