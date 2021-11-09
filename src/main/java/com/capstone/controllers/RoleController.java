package com.capstone.controllers;

import com.capstone.models.Roles;
import com.capstone.services.Result;
import com.capstone.services.ResultType;
import com.capstone.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> findById(@PathVariable int id){
       Roles roles= service.findById(id);
        if (roles == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(roles);

    }

    @GetMapping
    public List<Roles> findAll() {
        return service.findAll();
    }

    @RequestMapping(value = "/", produces = "application/json")
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Roles roles) {
        Result<Roles> result = service.add(roles);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @RequestMapping(value = "/{id}", produces = "application/json")
    @PutMapping
    public ResponseEntity<Object> update(@PathVariable int id, @RequestBody Roles roles){
        if (id != roles.getId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Result<Roles> result = service.update(roles);
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
