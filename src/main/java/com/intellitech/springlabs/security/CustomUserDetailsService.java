package com.intellitech.springlabs.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intellitech.springlabs.model.dto.RoleDto;
import com.intellitech.springlabs.model.dto.UserDto;
import com.intellitech.springlabs.service.UserService;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

		if (usernameOrEmail.trim().isEmpty()) {
			throw new UsernameNotFoundException("username is empty");
		}

		UserDto userDto = userService.findByUsernameOrEmail(usernameOrEmail);

		if (userDto == null) {
			throw new UsernameNotFoundException("User " + usernameOrEmail + " not found");
		}

		SpringLabsUser springLabsUser = new SpringLabsUser(userDto.getUsername(), userDto.getPassword(),
				getGrantedAuthorities(userDto));
		springLabsUser.setUserDto(userDto);
		return springLabsUser;
	}

	private List<GrantedAuthority> getGrantedAuthorities(UserDto userDto) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		RoleDto role = userDto.getRoleDto();
		authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		return authorities;
	}

}
