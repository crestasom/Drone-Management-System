package com.crestasom.dms.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.crestasom.dms.model.User;
import com.crestasom.dms.repo.UserRepo;
import com.crestasom.dms.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private UserRepo userRepo;

	@Override
	public Optional<User>  findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepo.findByUserName(userName);
	}

	@Override
	public void addUser(User user) {
		userRepo.save(user);
	}

}
