package com.capstone.services;

import com.capstone.data.TaskRepository;
import com.capstone.models.ProjectTask;
import com.capstone.models.Task;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task findById(int id){
        return taskRepository.findById(id);
    }

    public List<ProjectTask> findByUser(int id){
        return taskRepository.findByUser(id);
    }



    public List<Task> findAll(){
        return taskRepository.findAll();
    }

    public boolean deleteById(int id){
        return taskRepository.deleteById(id);
    }

    public Result<Task> add(Task task) {
        Result<Task> result = validate(task);
        if (!result.isSuccess()) {
            return result;
        }

        if (task.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        task = taskRepository.add(task);
        result.setPayload(task);
        return result;
    }

    public Result<Task> update(Task task){
        Result<Task> result = validate(task);
        if (!result.isSuccess()) {
            return result;
        }

        if (task.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (taskRepository.findById(task.getId()) == null){
            String msg = String.format("Id: %s, not found", task.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!taskRepository.update(task)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }

    public Result<Task> updateTotalHours(Task task){
        Result<Task> result = validate(task);
        if (!result.isSuccess()) {
            return result;
        }

        if (task.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (taskRepository.findById(task.getId()) == null){
            String msg = String.format("Id: %s, not found", task.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!taskRepository.updateTotalHours(task)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<Task> validate(Task task) {
        Result<Task> result = new Result<>();
        if (task == null) {
            result.addMessage("task cannot be null", ResultType.INVALID);
            return result;
        }
        if (Validations.isNullOrBlank(task.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }

        return result;
    }
}
