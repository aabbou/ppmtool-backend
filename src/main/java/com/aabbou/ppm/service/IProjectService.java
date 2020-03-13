package com.aabbou.ppm.service;

import java.util.List;

import com.aabbou.ppm.entity.Project;

public interface IProjectService {

    List<Project> findAllProjects(String username);

    Project saveOrUpdateProject(Project project, String username);

    void deleteProjectByIdentifier(String projectId, String username);

    Project findProjectByIdentifier(String projectId, String username);

}
