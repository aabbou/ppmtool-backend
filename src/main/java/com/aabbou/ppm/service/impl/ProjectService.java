package com.aabbou.ppm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aabbou.ppm.entity.Project;
import com.aabbou.ppm.exceptions.ProjectIdException;
import com.aabbou.ppm.repository.ProjectRepository;
import com.aabbou.ppm.service.IProjectService;

@Service
public class ProjectService  implements IProjectService{
	
	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public List<Project> findAllProjects() {
		return projectRepository.findAll();
	}

	@Override
	public Project saveOrUpdateProject(Project project) {
        try{
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        }catch (Exception e){
            throw new ProjectIdException("Project ID '"+project.getProjectIdentifier().toUpperCase()+"' already exists");
        }
	}


	@Override
	public void deleteProjectById(Long id) {
		Project project = findProjectById(id);
	    projectRepository.delete(project);
		
	}
	
	@Override
	public void deleteProjectByIdentifier(String projectId) {
		Project project = findProjectByIdentifier(projectId);
	    projectRepository.delete(project);
		
	}

	@Override
	public Project findProjectById(Long id) {
		Project project = projectRepository.getOne(id);
		if(project == null ) {
			throw new ProjectIdException("Project ID '"+ id +"' does not exist");
		}else {
			return project;
		}
	}

	@Override
	public Project findProjectByIdentifier(String projectId) {
		Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
		if(project == null ) {
			throw new ProjectIdException("Project ID '"+ projectId.toUpperCase() +"' does not exist");
		}else {
			return project;
		}
	}



}
