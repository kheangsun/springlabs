package com.intellitech.springlabs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.intellitech.springlabs.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {

	Device findByUniqueId(String uniqueId);
	@Query("SELECT d FROM Device d WHERE d.user.id=:userId")
	List<Device> findByUserId(@Param("userId")Long userId);
}
