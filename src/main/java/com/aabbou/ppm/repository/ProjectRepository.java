package com.aabbou.ppm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aabbou.ppm.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByProjectIdentifier(String projectIdentifier);

    List<Project> findAllByProjectLeader(String username);
}
