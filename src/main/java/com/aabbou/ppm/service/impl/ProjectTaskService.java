package com.aabbou.ppm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aabbou.ppm.entity.BackLog;
import com.aabbou.ppm.entity.Project;
import com.aabbou.ppm.entity.ProjectTask;
import com.aabbou.ppm.exceptions.ProjectNotFoundException;
import com.aabbou.ppm.repository.BackLogRepository;
import com.aabbou.ppm.repository.ProjectRepository;
import com.aabbou.ppm.repository.ProjectTaskRepository;
import com.aabbou.ppm.service.IProjectTaskService;

@Service
public class ProjectTaskService implements IProjectTaskService {

	@Autowired
	private BackLogRepository backlogRepository;

	@Autowired
	private ProjectTaskRepository projectTaskRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public ProjectTask addProjectTask(String projectIdentifier,
			ProjectTask projectTask) {

		// Exceptions: Project not found

		// PTs to be added to a specific project, project != null, BL exists
		BackLog backlog = backlogRepository
				.findByProjectIdentifier(projectIdentifier);
		// set the bl to pt
		projectTask.setBacklog(backlog);
		// we want our project sequence to be like this: IDPRO-1 IDPRO-2 ...100
		// 101
		Integer BacklogSequence = backlog.getPTSequence();
		// Update the BL SEQUENCE
		BacklogSequence++;
		backlog.setPTSequence(BacklogSequence);

		// Add Sequence to Project Task
		projectTask.setProjectSequence(
				projectIdentifier.toUpperCase() + "-" + BacklogSequence);
		projectTask.setProjectIdentifier(projectIdentifier.toUpperCase());

		// INITIAL priority when priority null
		if (projectTask.getPriority() == null) {
			projectTask.setPriority(0);
		}
		// INITIAL status when status is null
		if (projectTask.getStatus() == "" || projectTask.getStatus() == null) {
			projectTask.setStatus("TO_DO");
		}

		return projectTaskRepository.save(projectTask);
	}

	@Override
	public Iterable<ProjectTask> findBacklogById(String backlog_id) {
		Project project = projectRepository.findByProjectIdentifier(backlog_id);

		if (project == null) {
			throw new ProjectNotFoundException(
					"Project with ID: '" + backlog_id + "' does not exist");
		}
		return projectTaskRepository
				.findByProjectIdentifierOrderByPriority(backlog_id);
	}

	@Override
	public ProjectTask findPTByProjectSequence(String backlog_id,
			String pt_id) {
		// make sure we are searching on an existing backlog
		BackLog backlog = backlogRepository.findByProjectIdentifier(backlog_id);
		if (backlog == null) {
			throw new ProjectNotFoundException(
					"Project with ID: '" + backlog_id + "' does not exist");
		}

		// make sure that our task exists
		ProjectTask projectTask = projectTaskRepository
				.findByProjectSequence(pt_id);

		if (projectTask == null) {
			throw new ProjectNotFoundException(
					"Project Task '" + pt_id + "' not found");
		}

		// make sure that the backlog/project id in the path corresponds to the
		// right project
		if (!projectTask.getProjectIdentifier().equals(backlog_id)) {
			throw new ProjectNotFoundException("Project Task '" + pt_id
					+ "' does not exist in project: '" + backlog_id);
		}

		return projectTask;
	}

	@Override
	public ProjectTask updateByProjectSequence(ProjectTask updatedTask,
			String backlog_id, String pt_id) {
		ProjectTask projectTask = projectTaskRepository
				.findByProjectSequence(pt_id);

		projectTask = updatedTask;

		return projectTaskRepository.save(projectTask);
	}

	@Override
	public void deletePTByProjectSequence(String backlog_id, String pt_id) {
		ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id);

		// BackLog backlog = projectTask.getBacklog();
		// List<ProjectTask> pts = backlog.getProjectTasks();
		// pts.remove(projectTask);
		// backlogRepository.save(backlog);

		projectTaskRepository.delete(projectTask);
	}

}
