package com.intellitech.springlabs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.intellitech.springlabs.model.request.AuthenticationRequest;
import com.intellitech.springlabs.model.response.UserTransfer;
import com.intellitech.springlabs.util.Constants;
import com.intellitech.springlabs.util.TokenUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("customUserDetailsService")
	private UserDetailsService customUserDetailsService;

	@RequestMapping(value = "/authenticate", method = { RequestMethod.POST })
	@ApiOperation(value = "authenticate")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = Constants.SUCCESS, response = UserTransfer.class),
			@ApiResponse(code = 403, message = Constants.FORBIDDEN),
			@ApiResponse(code = 204, message = Constants.NOT_FOUND),
			@ApiResponse(code = 417, message = Constants.EXCEPTION_FAILED) })
	public ResponseEntity<UserTransfer> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			String username = authenticationRequest.getUsername();
			String password = authenticationRequest.getPassword();

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
			Authentication authentication = this.authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

			List<String> roles = new ArrayList<>();

			for (GrantedAuthority authority : userDetails.getAuthorities()) {
				roles.add(authority.toString());
			}
			return new ResponseEntity<UserTransfer>(new UserTransfer(userDetails.getUsername(), roles,
					TokenUtil.createToken(userDetails), HttpStatus.OK), HttpStatus.OK);
		} catch (BadCredentialsException bce) {
			return new ResponseEntity<UserTransfer>(new UserTransfer(), HttpStatus.NO_CONTENT);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
