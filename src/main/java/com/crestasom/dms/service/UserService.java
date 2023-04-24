package com.crestasom.dms.service;

import java.util.Optional;

import com.crestasom.dms.model.User;

public interface UserService {
	
	Optional<User> findByUserName(String userName);
	void addUser(User user);

}
