package com.capstone.models;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ProjectTask {

    private int task_id;
    private String task_name;
    private int project_id;
    private String project_name;
    private String status;
    private int status_id;
    private int user_id;
    private String total_hours;
}
