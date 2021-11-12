package com.capstone.controllers;

import com.capstone.models.Status;
import com.capstone.services.Result;
import com.capstone.services.StatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService service;

    public StatusController(StatusService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> findById(@PathVariable int id){
        Status status= service.findById(id);
        if (status == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(status);
    }

    @GetMapping
    public List<Status> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", produces = "application/json")
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Status status) {
        Result<Status> result = service.add(status);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @RequestMapping(value = "/{id}", produces = "application/json")
    @PutMapping
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Status status){
        if (id != status.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Status> result = service.update(status);
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
