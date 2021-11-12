package com.capstone.services;

import com.capstone.data.StatusRepository;
import com.capstone.models.Status;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status findById(int id){
        return statusRepository.findById(id);
    }

    public List<Status> findAll(){
        return statusRepository.findAll();
    }
    public boolean deleteById(int id){
        return statusRepository.deleteById(id);
    }

    public Result<Status> add(Status status) {
        Result<Status> result = validate(status);
        if (!result.isSuccess()) {
            return result;
        }

        if (status.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        status = statusRepository.add(status);
        result.setPayload(status);
        return result;
    }

    public Result<Status> update(Status status){
        Result<Status> result = validate(status);
        if (!result.isSuccess()) {
            return result;
        }

        if (status.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (statusRepository.findById(status.getId()) == null){
            String msg = String.format("Id: %s, not found", status.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!statusRepository.update(status)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<Status> validate(Status status) {
        Result<Status> result = new Result<>();
        if (status == null) {
            result.addMessage("task cannot be null", ResultType.INVALID);
            return result;
        }

        return result;
    }
}
