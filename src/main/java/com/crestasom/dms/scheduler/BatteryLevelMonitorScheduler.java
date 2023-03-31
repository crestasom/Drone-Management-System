package com.crestasom.dms.scheduler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.crestasom.dms.model.Drone;
import com.crestasom.dms.repo.DroneAuditLogRepo;
import com.crestasom.dms.service.DroneService;
import com.crestasom.dms.util.DTOUtility;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class BatteryLevelMonitorScheduler {
	private DroneService service;
	private DroneAuditLogRepo repo;
	private static Logger logger = LoggerFactory.getLogger(BatteryLevelMonitorScheduler.class);

	@Scheduled(cron = "0 * * * * ?")
	public void logBatteryInformation() {
		logger.debug("BatteryLevelMonitorScheduler start for logging drone battery level");
		List<Drone> droneList = service.findAllDrone();
		repo.saveAll(droneList.stream().map(DTOUtility::convertDroneToDroneAuditLog).toList());
		logger.debug("BatteryLevelMonitorScheduler completed for logging drone battery level");
	}
}
