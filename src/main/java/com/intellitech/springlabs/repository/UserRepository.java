package com.intellitech.springlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intellitech.springlabs.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.username=:usernameOrEmail OR u.email=:usernameOrEmail")
	User findByUsernameOrEmail(@Param("usernameOrEmail") String usernameOrEmail);
	User findByEmail(String email);
}
