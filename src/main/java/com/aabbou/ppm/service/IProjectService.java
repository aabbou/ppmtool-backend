package com.aabbou.ppm.service;

import java.util.List;

import com.aabbou.ppm.entity.Project;

public interface IProjectService {
	
	List<Project> findAllProjects();
	
	Project saveOrUpdateProject(Project project); 
	
	Project findProjectById(Long id); 
	
	void deleteProjectById(Long id);
	
	void deleteProjectByIdentifier(String projectId);

	Project findProjectByIdentifier(String projectId); 

}
