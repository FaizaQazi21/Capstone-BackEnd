package com.capstone.data;



import com.capstone.models.Task;
import com.capstone.models.ProjectTask;
import java.util.List;

public interface TaskRepository {
    List<Task> findAll();
    Task findById (int id);
    List<ProjectTask> findByUser(int id);
    Task add (Task task);
    boolean update(Task task);
    boolean updateTotalHours(Task task);
    boolean deleteById(int id);
}
