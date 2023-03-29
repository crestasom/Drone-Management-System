package com.crestasom.dms.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Drone {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer droneId;
	@Size(max = 100, message = "Max length for Serial Number is 100")
	@Column(unique = true)
	private String serialNumber;
	private Model model;
	@Max(value = 500, message = "Weight limit for drone is 500 Gram")
	private Double maxWeight;
	@Max(value = 100, message = "Battery capacity should be less than 100")
	@Min(value = 0, message = "Battery capacity should be greater than 0")
	private Integer batteryCapacity;
	private State state;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Medication> medicationList;

}
