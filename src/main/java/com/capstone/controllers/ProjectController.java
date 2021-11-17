package com.capstone.controllers;


import com.capstone.models.Project;
import com.capstone.services.ProjectService;
import com.capstone.services.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/project")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Project> findById(@PathVariable int id){
        Project project= service.findById(id);
        if (project == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(project);

    }
    @GetMapping
    public List<Project> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", produces = "application/json")
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Project project) {
        Result<Project> result = service.add(project);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    //@RequestMapping(value = "/update/{id}", produces = "application/json")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Project project){
        if (id != project.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Project> result = service.update(project);
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
