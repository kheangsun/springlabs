package com.intellitech.springlabs.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.intellitech.springlabs.model.dto.UserDto;

public class SpringLabsUser extends User {

	private static final long serialVersionUID = 1L;
	private UserDto userDto;

	public SpringLabsUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public UserDto getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDto userDto) {
		this.userDto = userDto;
	}

}
