package com.aabbou.ppm.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aabbou.ppm.entity.Project;
import com.aabbou.ppm.service.IProjectService;
import com.aabbou.ppm.service.impl.MapValidationErrorService;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @GetMapping("/all")
    public List<Project> getAllProjects(Principal principal) {
	return projectService.findAllProjects(principal.getName());
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProjectByIdentifier(@PathVariable String projectId, Principal principal) {
	Project project = projectService.findProjectByIdentifier(projectId, principal.getName());

	return new ResponseEntity<Project>(project, HttpStatus.OK);

    }

    @PostMapping("")
    public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result,
	    Principal principal) {
	// Validate body request
	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	if (errorMap != null)
	    return errorMap;

	// persist in db
	Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
	return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @PutMapping("")
    public ResponseEntity<?> updateProject(@Valid @RequestBody Project project, BindingResult result,
	    Principal principal) {

	// Validate body request
	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	if (errorMap != null)
	    return errorMap;

	Project project1 = projectService.saveOrUpdateProject(project, principal.getName());
	return new ResponseEntity<Project>(project1, HttpStatus.CREATED);
    }

    @DeleteMapping("/{projectId}")
    public ResponseEntity<?> deleteProjectById(@PathVariable String projectId, Principal principal) {
	projectService.deleteProjectByIdentifier(projectId, principal.getName());

	return new ResponseEntity<String>("Project with ID: '" + projectId + "' was deleted", HttpStatus.OK);
    }

}
