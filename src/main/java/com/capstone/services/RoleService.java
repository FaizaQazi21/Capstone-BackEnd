package com.capstone.services;

import com.capstone.data.RoleRepository;
import com.capstone.models.Roles;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Roles findById(int id){
        return roleRepository.findById(id);
    }

    public List<Roles> findAll(){
        return  roleRepository.findAll();
    }

    public boolean deleteById(int id){
        return roleRepository.deleteById(id);
    }

    public Result<Roles> add(Roles roles) {
        Result<Roles> result = validate(roles);
        if (!result.isSuccess()) {
            return result;
        }

        if (roles.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        roles = roleRepository.add(roles);
        result.setPayload(roles);
        return result;
    }

    public Result<Roles> update(Roles roles){
        Result<Roles> result = validate(roles);
        if (!result.isSuccess()) {
            return result;
        }

        if (roles.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (roleRepository.findById(roles.getId()) == null){
            String msg = String.format("Id: %s, not found", roles.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!roleRepository.update(roles)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<Roles> validate(Roles roles) {
        Result<Roles> result = new Result<>();
        if (roles == null) {
            result.addMessage("roles cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(roles.getRole())) {
            result.addMessage("role is required", ResultType.INVALID);
        }

        return result;
    }





}
