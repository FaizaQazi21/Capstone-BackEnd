package com.capstone.data.mapper;

import com.capstone.models.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.capstone.models.ProjectTask;

public class ProjectTaskMapper implements RowMapper<ProjectTask> {
    @Override
    public ProjectTask mapRow(ResultSet resultSet, int i) throws SQLException {
        ProjectTask projectTask = new ProjectTask();
        projectTask.setTask_id(resultSet.getInt("task_id"));
        projectTask.setTask_name(resultSet.getString("task_name"));
        projectTask.setProject_id(resultSet.getInt("project_id"));
        projectTask.setProject_name(resultSet.getString("project_name"));
        projectTask.setStatus(resultSet.getString("status"));
        projectTask.setStatus_id(resultSet.getInt("status_id"));
        projectTask.setUser_id((Integer) resultSet.getObject("user_id"));
        projectTask.setTotal_hours(resultSet.getString("total_hours"));

        return projectTask;
    }
}
