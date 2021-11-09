package com.capstone.models;

import lombok.Data;

import java.sql.Time;
import java.time.LocalTime;

@Data
public class Task {
    private int id;
    private String name;
    private int user_id;
    private Time start_time;
    private Time end_time;
    private String status;
    private int project_id;
    private String notes;

}
