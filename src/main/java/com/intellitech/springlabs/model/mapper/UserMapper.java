package com.intellitech.springlabs.model.mapper;

import org.springframework.stereotype.Component;

import com.intellitech.springlabs.model.User;
import com.intellitech.springlabs.model.dto.UserDto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class UserMapper extends ConfigurableMapper{

protected void configure(MapperFactory factory) {
		
	    factory.classMap(User.class, UserDto.class)
	      .field("id", "id")
	      .field("firstName", "firstName")
	      .field("lastName", "lastName")
	      .field("username", "username")
	      .field("firstName", "firstName")
	      .field("password", "password")
	      .field("email", "email")
	      .field("role", "roleDto")
	      .byDefault()
	      .register();      
	}
}
