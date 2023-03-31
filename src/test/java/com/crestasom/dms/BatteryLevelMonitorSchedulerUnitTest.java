package com.crestasom.dms;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.crestasom.dms.model.Drone;
import com.crestasom.dms.model.DroneAuditLog;
import com.crestasom.dms.model.Medication;
import com.crestasom.dms.repo.DroneAuditLogRepo;
import com.crestasom.dms.scheduler.BatteryLevelMonitorScheduler;
import com.crestasom.dms.service.DroneService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BatteryLevelMonitorSchedulerUnitTest {
	@InjectMocks
	BatteryLevelMonitorScheduler scheduler;
	@Mock
	private DroneService service;
	@Mock
	private DroneAuditLogRepo repo;

	@BeforeEach
	void setUp() {
		List<Drone> droneList = new ArrayList<>();
		List<Medication> med = new ArrayList<>();
		med.add(Medication.builder().weight(200.0).build());
		List<Medication> med1 = new ArrayList<>();
		List<DroneAuditLog> auditList = new ArrayList<>();
		auditList.add(DroneAuditLog.builder().build());
		auditList.add(DroneAuditLog.builder().build());
		auditList.add(DroneAuditLog.builder().build());
		droneList.add(Drone.builder().serialNumber("1234").medicationList(med1).build());
		droneList.add(Drone.builder().serialNumber("456").medicationList(med).build());
		droneList.add(Drone.builder().serialNumber("789").build());
		Mockito.when(service.findAllDrone()).thenReturn(droneList);
		Mockito.when(repo.saveAll(any())).thenReturn(null);
		Mockito.when(repo.findAll()).thenReturn(auditList);
	}

	@Test
	void testLogBatteryInformation() {
		scheduler.logBatteryInformation();
		assertEquals(3, repo.findAll().size());
	}
}
