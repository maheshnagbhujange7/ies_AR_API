package com.spider.__AR_API.service;

import java.util.List;

import com.spider.__AR_API.bindings.App;

public interface ArService {

	public String createApplication(App app);
	
	public List<App> fetchApps (Integer userId);
	
	
}
