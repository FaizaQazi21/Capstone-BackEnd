package com.capstone.data;


import com.capstone.models.Status;

import java.util.List;

public interface StatusRepository {
    List<Status> findAll();
    Status findById (int id);
    Status add (Status status);
    boolean update(Status status);
    boolean deleteById(int id);
}
