package com.aabbou.ppm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aabbou.ppm.entity.User;
import com.aabbou.ppm.exceptions.UsernameAlreadyExistsException;
import com.aabbou.ppm.repository.UserRepository;
import com.aabbou.ppm.service.IUserService;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public User registerUser(User newUser) {

		try {

			newUser.setPassword(
					bCryptPasswordEncoder.encode(newUser.getPassword()));
			// Username has to be unique (exception)
			newUser.setUsername(newUser.getUsername());
			// Make sure that password and confirmPassword match
			// We don't persist or show the confirmPassword
			newUser.setConfirmPassword("");

			return userRepository.save(newUser);

		} catch (Exception e) {
			throw new UsernameAlreadyExistsException(
					"This username already exists");
		}

		// Make sure that password and confirmPassword match
		// We don't persist or show the confirmPassword

	}

}
