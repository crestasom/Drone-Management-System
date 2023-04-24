package com.crestasom.dms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.crestasom.dms.model.User;
import com.crestasom.dms.service.UserService;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableScheduling
@AllArgsConstructor
public class DmsApplication implements CommandLineRunner {

	private UserService service;
	private static Logger logger = LoggerFactory.getLogger(DmsApplication.class);
	private PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		service.addUser(User.builder().userName("test").password(encoder.encode("test")).build());
		logger.info("test user added");
	}

}
