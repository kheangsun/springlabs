package com.intellitech.springlabs.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intellitech.springlabs.model.dto.DeviceDto;
import com.intellitech.springlabs.model.dto.UserDto;
import com.intellitech.springlabs.model.request.DeviceRequest;
import com.intellitech.springlabs.model.request.UpdateDeviceRequest;
import com.intellitech.springlabs.service.DeviceService;
import com.intellitech.springlabs.service.UserService;
import com.intellitech.springlabs.util.Constants;
import com.intellitech.springlabs.util.CurrentSession;
import com.intellitech.springlabs.util.CustomError;
import com.intellitech.springlabs.util.GenericResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/devices")
public class DeviceController {
	
	

	@Autowired
	private DeviceService deviceService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CurrentSession currentSession;

	@ApiOperation(value = "Add new device fot user")
	@RequestMapping(value = "addNewDevice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = DeviceDto.class),
			@ApiResponse(code = 409, message = Constants.UNIQUE_ID_ALREADY_EXIST),
			@ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
			@ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> addNewDevice(@RequestBody DeviceRequest request) {

		try {
			deviceService.findByUniqueId(request.getUniqueId());
			CustomError error = new CustomError("Device with uniqueId = " + request.getUniqueId() + " already exist");
			return new ResponseEntity<CustomError>(error, HttpStatus.CONFLICT);

		} catch (ObjectNotFoundException onfe) {
             
			try {
				UserDto userDto = userService.findById(currentSession.getSpringLabsUser().getUserDto().getId());
				DeviceDto deviceToSave = new DeviceDto();
				deviceToSave.setUniqueId(request.getUniqueId());
				deviceToSave.setName(request.getName());
				deviceToSave.setStatus(request.getStatus());
				deviceToSave.setPhoneNumber(request.getPhoneNumber());
				deviceToSave.setUserDto(userDto);
				DeviceDto deviceSaved = deviceService.saveDevice(deviceToSave);
				if (deviceSaved != null) {
					return new ResponseEntity<DeviceDto>(deviceSaved, HttpStatus.OK);
				} else {
					CustomError error = new CustomError("Device is not added");
					return new ResponseEntity<CustomError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (ObjectNotFoundException _onfe) {
				CustomError error = new CustomError("User with id = " +currentSession.getSpringLabsUser().getUserDto().getId() + " is not found");
				return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);
			}

		} catch (Exception e) {
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@ApiOperation(value = "Update existing device")
	@RequestMapping(value = "/updateDevice/{deviceId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = DeviceDto.class),
			@ApiResponse(code = 422, message = Constants.DEVICE_NOT_FOUND),
			@ApiResponse(code = 500, message = Constants.INTERNAL_SERVER_ERROR),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> updateDevice(@PathVariable("deviceId") Long deviceId,
			@RequestBody UpdateDeviceRequest request) {

		try {
			DeviceDto deviceToEdit = deviceService.findById(deviceId);
			deviceToEdit.setName(request.getName());
			deviceToEdit.setStatus(request.getStatus());
			deviceToEdit.setPhoneNumber(request.getPhoneNumber());
			DeviceDto deviceSaved = deviceService.saveDevice(deviceToEdit);
			if (deviceSaved != null) {
				return new ResponseEntity<DeviceDto>(deviceSaved, HttpStatus.OK);
			} else {
				CustomError error = new CustomError("Device is not added");
				return new ResponseEntity<CustomError>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			}

		} catch (ObjectNotFoundException onfe) {
			CustomError error = new CustomError("Device with id = " + deviceId + " is not found");
			return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (Exception e) {
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@ApiOperation(value = "Delete existing device")
	@RequestMapping(value = "deleteDevice/{deviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<GenericResponse> deleteDevice(@PathVariable("deviceId") Long deviceId) {
		GenericResponse response = new GenericResponse();
		try {
			deviceService.deleteDevice(deviceId);
			response.setStatus("Success");
			response.setMessage("device is succsefully deleted");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);

		} catch (Exception e) {
			response.setStatus("Failed");
			response.setMessage("device is not deleted");
			return new ResponseEntity<GenericResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@ApiOperation(value = "Get device by id")
	@RequestMapping(value = "/getById/{deviceId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = DeviceDto.class),
			@ApiResponse(code = 422, message = Constants.DEVICE_NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> getDeviceById(@PathVariable("deviceId") Long deviceId) {

		try {
			DeviceDto deviceDto = deviceService.findById(deviceId);
			return new ResponseEntity<DeviceDto>(deviceDto, HttpStatus.OK);

		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			CustomError error = new CustomError("Device with id = " + deviceId + " is not found");
			return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		catch (Exception e) {
			e.printStackTrace();
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}

	}
	
	@ApiOperation(value = "Get all devices of user")
	@RequestMapping(value = "/getByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = DeviceDto.class),
			@ApiResponse(code = 422, message = Constants.DEVICE_NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> getDeviceByUserId(){
		 
		try {
			List<DeviceDto> deviceDtos = deviceService.findByUserId(currentSession.getSpringLabsUser().getUserDto().getId());
			return new ResponseEntity<List<DeviceDto>>(deviceDtos, HttpStatus.OK);
		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			return new ResponseEntity<List<DeviceDto>>(Collections.emptyList(), HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}
		
	}
}
