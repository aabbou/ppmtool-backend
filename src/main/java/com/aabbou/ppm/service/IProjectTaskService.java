package com.aabbou.ppm.service;

import com.aabbou.ppm.entity.ProjectTask;

public interface IProjectTaskService {

	ProjectTask addProjectTask(String backlog_id, ProjectTask projectTask);

	Iterable<ProjectTask> findBacklogById(String backlog_id);

	ProjectTask findPTByProjectSequence(String backlog_id, String pt_id);

	ProjectTask updateByProjectSequence(ProjectTask projectTask,
			String backlog_id, String pt_id);

	void deletePTByProjectSequence(String backlog_id, String pt_id);

}
