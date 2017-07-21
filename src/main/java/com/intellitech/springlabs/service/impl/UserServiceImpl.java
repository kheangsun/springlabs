package com.intellitech.springlabs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intellitech.springlabs.model.User;
import com.intellitech.springlabs.model.dto.UserDto;
import com.intellitech.springlabs.model.mapper.UserMapper;
import com.intellitech.springlabs.repository.UserRepository;
import com.intellitech.springlabs.service.UserService;

import javassist.tools.rmi.ObjectNotFoundException;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserMapper userMapper;

	@Override
	@Transactional(readOnly = true)
	public UserDto findByUsernameOrEmail(String username) {
		try {
			User user = userRepository.findByUsernameOrEmail(username);
			return userMapper.map(user, UserDto.class);
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	@Transactional(readOnly = true)
	public UserDto findById(Long id) throws ObjectNotFoundException {

		User user = userRepository.findOne(id);
		if (user == null) {
			throw new ObjectNotFoundException("User not found");
		}
		return userMapper.map(user, UserDto.class);

	}

	@Override
	@Transactional(readOnly = true)
	public UserDto findByEmail(String email) throws ObjectNotFoundException {

		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("User not found");
		}
		return userMapper.map(user, UserDto.class);

	}

	@Override
	public UserDto save(UserDto userDto) {

		if (userDto != null) {
			User user = userMapper.map(userDto, User.class);
			User userSaved = userRepository.save(user);
			if (userSaved != null) {
				return userMapper.map(userSaved, UserDto.class);
			}
		}
		return null;
	}

}
