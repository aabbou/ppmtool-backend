package com.aabbou.ppm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aabbou.ppm.entity.BackLog;
import com.aabbou.ppm.entity.Project;
import com.aabbou.ppm.entity.User;
import com.aabbou.ppm.exceptions.ProjectIdException;
import com.aabbou.ppm.exceptions.ProjectNotFoundException;
import com.aabbou.ppm.repository.BackLogRepository;
import com.aabbou.ppm.repository.ProjectRepository;
import com.aabbou.ppm.repository.UserRepository;
import com.aabbou.ppm.service.IProjectService;

@Service
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BackLogRepository backLogRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Project> findAllProjects(String username) {
	return projectRepository.findAllByProjectLeader(username);
    }

    @Override
    public Project saveOrUpdateProject(Project project, String username) {

	if (project.getId() != null) {
	    Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());
	    if (existingProject != null && (!existingProject.getProjectLeader().equals(username))) {
		throw new ProjectNotFoundException("Project not found in your account");
	    } else if (existingProject == null) {
		throw new ProjectNotFoundException("Project with ID: '" + project.getProjectIdentifier()
			+ "' cannot be updated because it doesn't exist");
	    }
	}

	final String projectId = project.getProjectIdentifier().toUpperCase();
	try {
	    User user = userRepository.findByUsername(username);
	    project.setUser(user);
	    project.setProjectLeader(user.getUsername());

	    project.setProjectIdentifier(projectId);

	    if (project.getId() == null || project.getId() == 0) { // create new
		BackLog backlog = new BackLog();
		project.setBacklog(backlog);
		backlog.setProject(project);
		backlog.setProjectIdentifier(projectId);
	    }

	    if (project.getId() != null && project.getId() != 0) {// update
		project.setBacklog(backLogRepository.findByProjectIdentifier(projectId));
	    }
	    return projectRepository.save(project);
	} catch (Exception e) {
	    throw new ProjectIdException("Project ID '" + projectId + "' already exists");
	}
    }

    @Override
    public void deleteProjectByIdentifier(String projectId, String username) {
	Project project = findProjectByIdentifier(projectId, username);
	projectRepository.delete(project);

    }

    @Override
    public Project findProjectByIdentifier(String projectId, String username) {
	Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
	if (project == null) {
	    throw new ProjectIdException("Project ID '" + projectId.toUpperCase() + "' does not exist");
	}
	if (!project.getProjectLeader().equals(username)) {
	    throw new ProjectNotFoundException("Project not found in your account");
	}

	return project;

    }

}
