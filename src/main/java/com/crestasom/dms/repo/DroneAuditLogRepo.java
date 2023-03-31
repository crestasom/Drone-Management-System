package com.crestasom.dms.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crestasom.dms.model.DroneAuditLog;

public interface DroneAuditLogRepo extends JpaRepository<DroneAuditLog, Integer>{

}
