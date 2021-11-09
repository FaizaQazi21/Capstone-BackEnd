package com.capstone.data;



import com.capstone.models.Task;

import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById (int id);
    Task add (Task task);
    boolean update(Task task);
    boolean deleteById(int id);
}
