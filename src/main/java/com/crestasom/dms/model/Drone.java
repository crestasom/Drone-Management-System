package com.crestasom.dms.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.crestasom.dms.model.enums.State;

import java.util.List;

import com.crestasom.dms.model.enums.Model;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "drone")
public class Drone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer droneId;
	private String serialNumber;
	private Model model;
	private Double maxWeight;
	private Integer batteryCapacity;
	private State state;
	private List<Medication> medicationList;

}
