package com.capstone.models;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Task {
    private int id;
    private String name;
    private Integer user_id;
    private Timestamp start_time;
    private String total_hours;
    private int status_id;
    private int project_id;
    private String notes;
    private String task_description;

}
