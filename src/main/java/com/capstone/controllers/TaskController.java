package com.capstone.controllers;


import com.capstone.models.ProjectTask;
import com.capstone.models.Task;
import com.capstone.services.Result;
import com.capstone.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable int id){
        Task task= service.findById(id);
        if (task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(task);
    }
    @GetMapping("/user/{id}")
    public List<ProjectTask> findByUser(@PathVariable int id){
        return service.findByUser(id);

    }
    @GetMapping("/project/{id}")
    public List<ProjectTask> findByProject(@PathVariable int id){
        return service.findByProject(id);

    }

    @GetMapping
    public List<Task> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", produces = "application/json")
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Task task) {
        Result<Task> result = service.add(task);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @RequestMapping(value = "/{id}", produces = "application/json")
    @PutMapping
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Task task){
        if (id != task.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Task> result = service.update(task);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }
    @RequestMapping(value = "/updatehours/{taskid}", produces = "application/json")
    @PutMapping
    public ResponseEntity<Object> updateTotalHours(@PathVariable int taskid, @RequestBody Task task){
        if (taskid != task.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Task> result = service.updateTotalHours(task);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        if (service.deleteById(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
