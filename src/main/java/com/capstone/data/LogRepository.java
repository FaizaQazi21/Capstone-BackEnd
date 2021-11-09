package com.capstone.data;


import com.capstone.models.Log;

import java.util.List;

public interface LogRepository {

    List<Log> findAll();
    Log findById (int id);
    Log add (Log log);
    boolean update(Log log);
    boolean deleteById(int id);
}
