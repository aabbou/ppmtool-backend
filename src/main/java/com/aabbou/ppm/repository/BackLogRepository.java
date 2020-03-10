package com.aabbou.ppm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aabbou.ppm.entity.BackLog;

@Repository
public interface BackLogRepository extends JpaRepository<BackLog, Long> {

	BackLog findByProjectIdentifier(String projectIdentifier);
	

}
