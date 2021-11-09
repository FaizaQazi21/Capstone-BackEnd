package com.capstone.services;

import com.capstone.data.LogRepository;
import com.capstone.models.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public Log findById(int id){
        return logRepository.findById(id);
    }

    public List<Log> findAll(){
        return logRepository.findAll();
    }

    public boolean deleteById(int id){
        return logRepository.deleteById(id);
    }

    public Result<Log> add(Log log) {
        Result<Log> result = validate(log);
        if (!result.isSuccess()) {
            return result;
        }

        if (log.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        log = logRepository.add(log);
        result.setPayload(log);
        return result;
    }

    public Result<Log> update(Log log){
        Result<Log> result = validate(log);
        if (!result.isSuccess()) {
            return result;
        }

        if (log.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (logRepository.findById(log.getId()) == null){
            String msg = String.format("Id: %s, not found", log.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!logRepository.update(log)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<Log> validate(Log log) {
        Result<Log> result = new Result<>();
        if (log == null) {
            result.addMessage("log cannot be null", ResultType.INVALID);
            return result;
        }

        return result;
    }
}
