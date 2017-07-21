package com.intellitech.springlabs.model.mapper;

import org.springframework.stereotype.Component;

import com.intellitech.springlabs.model.Device;
import com.intellitech.springlabs.model.dto.DeviceDto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

@Component
public class DeviceMapper extends ConfigurableMapper{

	protected void configure(MapperFactory factory) {
	    factory.classMap(Device.class, DeviceDto.class)
	      .field("id", "id")
	      .field("name", "name")
	      .field("uniqueId", "uniqueId")
	      .field("status", "status")
	      .field("phoneNumber", "phoneNumber")
	      .field("user", "userDto")
	      .field("user.role", "userDto.roleDto")
	      .byDefault()
	      .register();  
	  }
}
