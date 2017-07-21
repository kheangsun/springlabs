package com.intellitech.springlabs.service;

import java.util.List;

import com.intellitech.springlabs.model.dto.RoleDto;

import javassist.tools.rmi.ObjectNotFoundException;

public interface RoleService {
	
	 List<RoleDto> findAll() throws ObjectNotFoundException;
}
