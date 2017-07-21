package com.intellitech.springlabs.model.mapper;

import org.springframework.stereotype.Component;

import com.intellitech.springlabs.model.Role;
import com.intellitech.springlabs.model.dto.RoleDto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class RoleMapper extends ConfigurableMapper{
	
	protected void configure(MapperFactory factory) {
		
	    factory.classMap(Role.class, RoleDto.class)
	      .field("id", "id")
	      .field("roleName", "roleName")
	      .byDefault()
	      .register();      
	}
}
