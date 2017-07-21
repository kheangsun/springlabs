package com.intellitech.springlabs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intellitech.springlabs.model.dto.UserDto;
import com.intellitech.springlabs.model.request.UpdateUserProfileRequest;
import com.intellitech.springlabs.service.UserService;
import com.intellitech.springlabs.util.Constants;
import com.intellitech.springlabs.util.CurrentSession;
import com.intellitech.springlabs.util.CustomError;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CurrentSession currentSession;

	@ApiOperation(value = "Get current user")
	@RequestMapping(value = "getCurrentUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = UserDto.class),
			@ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> getCurrentUser() {

		try {
			UserDto userDto = userService.findById(currentSession.getSpringLabsUser().getUserDto().getId());
			return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);

		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			CustomError error = new CustomError("User not found");
			return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);

		} catch (Exception e) {
			e.printStackTrace();
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}

	}

	@ApiOperation(value = "Update user profile")
	@RequestMapping(value = "updateUserProfile", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = UserDto.class),
			@ApiResponse(code = 422, message = Constants.USER_NOT_FOUND),
			@ApiResponse(code = 204, message = Constants.USER_PROFILE_NOT_UPDATED),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserProfileRequest request) {
		try {
			UserDto userDto = userService.findById(currentSession.getSpringLabsUser().getUserDto().getId());
			userDto.setFirstName(request.getFirstName());
			userDto.setLastName(request.getLastName());
			userDto.setUsername(request.getUsername());
			userDto.setEmail(request.getEmail());
			UserDto userSaved = userService.save(userDto);
			if (userSaved != null) {
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
			}
			CustomError error = new CustomError("Cannot update user");
			return new ResponseEntity<CustomError>(error, HttpStatus.NO_CONTENT);

		} catch (ObjectNotFoundException onfe) {
			onfe.printStackTrace();
			CustomError error = new CustomError("User not found");
			return new ResponseEntity<CustomError>(error, HttpStatus.UNPROCESSABLE_ENTITY);
		}

		catch (Exception e) {
			e.printStackTrace();
			CustomError error = new CustomError("An error has occured");
			return new ResponseEntity<CustomError>(error, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
