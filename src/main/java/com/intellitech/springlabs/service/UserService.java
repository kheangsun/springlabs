package com.intellitech.springlabs.service;

import com.intellitech.springlabs.model.dto.UserDto;

import javassist.tools.rmi.ObjectNotFoundException;

public interface UserService {
	
	 UserDto findByUsernameOrEmail(String usernameOrEmail);
	 UserDto findById(Long id) throws ObjectNotFoundException;
	 UserDto findByEmail(String email) throws ObjectNotFoundException;
	 UserDto save(UserDto userDto);
}
