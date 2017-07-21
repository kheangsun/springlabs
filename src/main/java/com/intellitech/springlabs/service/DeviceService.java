package com.intellitech.springlabs.service;

import java.util.List;

import com.intellitech.springlabs.model.dto.DeviceDto;

import javassist.tools.rmi.ObjectNotFoundException;

public interface DeviceService {

	 DeviceDto saveDevice(DeviceDto device);
	 void deleteDevice(DeviceDto device);
	 void deleteDevice(Long deviceId) throws IllegalArgumentException;
	 DeviceDto updateDevice(DeviceDto device);
	 DeviceDto findById(Long deviceId) throws ObjectNotFoundException;
	 DeviceDto findByUniqueId(String uniqueId) throws ObjectNotFoundException;
	 List<DeviceDto> findByUserId(Long userId) throws ObjectNotFoundException;
}
