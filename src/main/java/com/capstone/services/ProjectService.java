package com.capstone.services;

import com.capstone.data.ProjectRepository;
import com.capstone.models.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    public Project findById(int id){
        return projectRepository.findById(id);
    }

    public List<Project> findAll(){
        return  projectRepository.findAll();
    }

    public boolean deleteById(int id){
        return projectRepository.deleteById(id);
    }

    public Result<Project> add(Project project) {
        Result<Project> result = validate(project);
        if (!result.isSuccess()) {
            return result;
        }

        if (project.getId() != 0) {
            result.addMessage("Id cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        project = projectRepository.add(project);
        result.setPayload(project);
        return result;
    }

    public Result<Project> update(Project project){
        Result<Project> result = validate(project);
        if (!result.isSuccess()) {
            return result;
        }

        if (project.getId() <= 0) {
            result.addMessage("Id must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (projectRepository.findById(project.getId()) == null){
            String msg = String.format("Id: %s, not found", project.getId());
            result.addMessage(msg, ResultType.NOT_FOUND);
            return result;
        }

        if (!projectRepository.update(project)) {
            result.addMessage("Invalid", ResultType.INVALID);
            return result;
        }

        return result;
    }


    private Result<Project> validate(Project project) {
        Result<Project> result = new Result<>();
        if (project == null) {
            result.addMessage("project cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(project.getName())) {
            result.addMessage("name is required", ResultType.INVALID);
        }
        if (Validations.isNullOrBlank(project.getProject_description())) {
            result.addMessage("description is required", ResultType.INVALID);
        }
        if (Validations.isNullOrBlank(project.getPriority())) {
            result.addMessage("priority is required", ResultType.INVALID);
        }

        return result;
    }
    public int getTotalTasksCompleted(int id){
        return projectRepository.findCompletedTasks(id);
    }
}
