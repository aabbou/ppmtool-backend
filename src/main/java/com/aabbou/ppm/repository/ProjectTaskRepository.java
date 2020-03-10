package com.aabbou.ppm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aabbou.ppm.entity.ProjectTask;

@Repository
public interface ProjectTaskRepository
		extends
			JpaRepository<ProjectTask, Long> {

	Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(
			String backlog_id);

	ProjectTask findByProjectSequence(String pt_id);

}
