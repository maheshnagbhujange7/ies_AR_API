package com.spider.__AR_API.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.spider.__AR_API.bindings.App;
import com.spider.__AR_API.service.ArService;

public class ArRestController {

	@Autowired
	private ArService arService;
	
	@PostMapping("/app")
	public ResponseEntity<String> createApp(@RequestBody App app){
		String status = arService.createApplication(app);
		return new ResponseEntity<>(status,HttpStatus.OK);
	}
	
	@GetMapping("/apps/{userId}")
	public List<App> getApps(Integer userId){
		return arService.fetchApps(userId);
	}
}
