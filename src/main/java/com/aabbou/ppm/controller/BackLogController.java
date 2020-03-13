package com.aabbou.ppm.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aabbou.ppm.entity.ProjectTask;
import com.aabbou.ppm.service.IProjectTaskService;
import com.aabbou.ppm.service.impl.MapValidationErrorService;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BackLogController {

    @Autowired
    private IProjectTaskService projectTaskService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addProjectTasktoBackLog(@PathVariable String backlog_id,
	    @Valid @RequestBody ProjectTask projectTask, BindingResult result, Principal principal) {

	ResponseEntity<?> erroMap = mapValidationErrorService.mapValidationService(result);
	if (erroMap != null)
	    return erroMap;

	ProjectTask projectTask1 = projectTaskService.addProjectTask(backlog_id, projectTask, principal.getName());

	return new ResponseEntity<ProjectTask>(projectTask1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBackLog(@PathVariable String backlog_id, Principal principal) {

	return projectTaskService.findBacklogById(backlog_id, principal.getName());

    }

    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
	    Principal principal) {
	ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id, principal.getName());
	return new ResponseEntity<ProjectTask>(projectTask, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> updateProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
	    @Valid @RequestBody ProjectTask projectTask, BindingResult result, Principal principal) {

	ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
	if (errorMap != null)
	    return errorMap;

	ProjectTask updatedTask = projectTaskService.updateByProjectSequence(projectTask, backlog_id, pt_id,
		principal.getName());

	return new ResponseEntity<ProjectTask>(updatedTask, HttpStatus.OK);

    }

    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,
	    Principal principal) {
	projectTaskService.deletePTByProjectSequence(backlog_id, pt_id, principal.getName());

	return new ResponseEntity<String>("Project Task " + pt_id + " was deleted successfully", HttpStatus.OK);
    }

}
