package com.intellitech.springlabs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intellitech.springlabs.model.Device;
import com.intellitech.springlabs.model.dto.DeviceDto;
import com.intellitech.springlabs.model.mapper.DeviceMapper;
import com.intellitech.springlabs.repository.DeviceRepository;
import com.intellitech.springlabs.service.DeviceService;
import com.intellitech.springlabs.util.MapperUtil;

import javassist.tools.rmi.ObjectNotFoundException;

@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private DeviceMapper deviceMapper;

	@Override
	public DeviceDto saveDevice(DeviceDto deviceDto) {

		if (deviceDto != null) {
			Device device = deviceMapper.map(deviceDto, Device.class);
			Device deviceSaved = deviceRepository.save(device);
			return deviceMapper.map(deviceSaved, DeviceDto.class);
		}
		return null;
	}

	@Override
	public void deleteDevice(DeviceDto deviceDto) {
		if (deviceDto != null) {
			Device device = deviceMapper.map(deviceDto, Device.class);
			deviceRepository.delete(device);
		}
	}

	@Override
	public void deleteDevice(Long deviceId) throws IllegalArgumentException{
		deviceRepository.delete(deviceId);
	}

	@Override
	public DeviceDto updateDevice(DeviceDto deviceDto) {

		if (deviceDto != null) {
			Device device = deviceMapper.map(deviceDto, Device.class);
			Device deviceUpdated = deviceRepository.save(device);
			return deviceMapper.map(deviceUpdated, DeviceDto.class);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public DeviceDto findById(Long deviceId) throws ObjectNotFoundException {

		Device device = deviceRepository.findOne(deviceId);
		if (device == null) {
			throw new ObjectNotFoundException("User not found");
		}
		return deviceMapper.map(device, DeviceDto.class);

	}

	@Override
	@Transactional(readOnly = true)
	public DeviceDto findByUniqueId(String uniqueId) throws ObjectNotFoundException {

		Device device = deviceRepository.findByUniqueId(uniqueId);
		if (device == null) {
			throw new ObjectNotFoundException("User not found");
		}
		return deviceMapper.map(device, DeviceDto.class);

	}

	@Override
	@Transactional(readOnly = true)
	public List<DeviceDto> findByUserId(Long userId) throws ObjectNotFoundException {
		List<Device> devices=deviceRepository.findByUserId(userId);
		if(devices.isEmpty()){
			throw new ObjectNotFoundException("Devices not found");
		}
		return MapperUtil.map(deviceMapper, devices, DeviceDto.class);
	}

}
