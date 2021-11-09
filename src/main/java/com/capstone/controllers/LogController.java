package com.capstone.controllers;

import com.capstone.models.Log;
import com.capstone.services.LogService;
import com.capstone.services.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/log")
public class LogController {

    private final LogService service;

    public LogController(LogService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Log> findById(@PathVariable int id){
        Log log= service.findById(id);
        if (log == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(log);
    }

    @GetMapping
    public List<Log> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", produces = "application/json")
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Log log) {
        Result<Log> result = service.add(log);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @RequestMapping(value = "/{id}", produces = "application/json")
    @PutMapping
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Log log){
        if (id != log.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Log> result = service.update(log);
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
