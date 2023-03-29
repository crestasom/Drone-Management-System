package com.crestasom.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Generated;

@SpringBootApplication
@EnableScheduling
public class DmsApplication {
	@Generated
	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

}
