package com.aabbou.ppm.service;

import com.aabbou.ppm.entity.ProjectTask;

public interface IProjectTaskService {

    ProjectTask addProjectTask(String backlog_id, ProjectTask projectTask, String username);

    Iterable<ProjectTask> findBacklogById(String backlog_id, String username);

    ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username);

    ProjectTask updateByProjectSequence(ProjectTask projectTask, String backlog_id, String pt_id, String username);

    void deletePTByProjectSequence(String backlog_id, String pt_id, String username);

}
