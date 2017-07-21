package com.intellitech.springlabs.util;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.intellitech.springlabs.security.SpringLabsUser;

@Component("currentSession")
@Scope(value = "session",proxyMode=ScopedProxyMode.TARGET_CLASS )
public class CurrentSession implements Serializable {

	private static final long serialVersionUID = 1L;
	private SpringLabsUser springLabsUser;

	public SpringLabsUser getSpringLabsUser() {
		springLabsUser= (SpringLabsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return springLabsUser;
	}

	public void setSpringLabsUser(SpringLabsUser springLabsUser) {
		this.springLabsUser = springLabsUser;
	}

}
