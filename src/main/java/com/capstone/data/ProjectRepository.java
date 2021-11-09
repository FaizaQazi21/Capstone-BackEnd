package com.capstone.data;



import com.capstone.models.Project;

import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();
    Project findById (int id);
    Project add (Project project);
    boolean update(Project project);
    boolean deleteById(int id);
}
