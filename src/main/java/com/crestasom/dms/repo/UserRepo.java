package com.crestasom.dms.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crestasom.dms.model.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);
}
