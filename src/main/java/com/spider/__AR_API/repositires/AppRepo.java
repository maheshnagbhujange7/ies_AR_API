package com.spider.__AR_API.repositires;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.spider.__AR_API.entites.AppEntity;

public interface AppRepo extends JpaRepository<AppEntity,Long> {

	public List<AppEntity> fetchUserApps();
	
	@Query(value="from AppEntity where userId=:userId")
	public List<AppEntity> fetchCwApps(Integer userId);
}
