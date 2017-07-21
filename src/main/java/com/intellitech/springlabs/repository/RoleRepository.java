package com.intellitech.springlabs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intellitech.springlabs.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
