package com.capstone.services;

import com.capstone.data.UserRepository;

import com.capstone.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User findById(int id){
        return userRepository.findById(id);
    }

    public List<User> findAll(){
        return  userRepository.findAll();
    }

    public boolean deleteById(int id){
        return userRepository.deleteById(id);
    }

    public Result<User> add(User user) {
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        user = userRepository.add(user);
        result.setPayload(user);
        return result;
    }

    public Result<User> update(User user){
        Result<User> result = validate(user);
        if (!result.isSuccess()) {
            return result;
        }

        if (user.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (userRepository.findById(user.getId()) == null){
            String msg = String.format("Id: %s, not found", user.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!userRepository.update(user)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<User> validate(User user) {
        Result<User> result = new Result<>();
        if (user == null) {
            result.addMessage("user cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(user.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }
        if (Validations.isNullOrBlank(user.getEmail())) {
            result.addMessage("email is required", ResultType.INVALID);
        }
        if (Validations.isNullOrBlank(user.getPassword())) {
            result.addMessage("password is required", ResultType.INVALID);
        }

        return result;
    }
}
