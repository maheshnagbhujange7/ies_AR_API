package com.spider.__AR_API.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.function.client.WebClient;

import com.spider.ADMIN_API.entities.UserEntity;
import com.spider.ADMIN_API.repositires.UserRepo;
import com.spider.__AR_API.bindings.App;
import com.spider.__AR_API.constants.AppConstatnts;
import com.spider.__AR_API.entites.AppEntity;
import com.spider.__AR_API.exception.SsaWebException;
import com.spider.__AR_API.repositires.AppRepo;

public class ArServiceImpl implements ArService {

	@Autowired
	private AppRepo appRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	private static final String SSA_WEB_API_URL="https://ssa.web.app/{ssn}";
	
	@Override
	public String createApplication(App app) {
		
		try {
			WebClient webClient = WebClient.create(); 
			
			String stateName=webClient.get()
			                          .uri(SSA_WEB_API_URL,app.getSsn())
			                          .retrieve()
			                          .bodyToMono(String.class)
			                          .block();
			if (AppConstatnts.RI.equals(stateName)) {
				
				UserEntity userEntity = userRepo.findById(app.getUserId()).get();
				
				AppEntity appEntity = new AppEntity();
				BeanUtils.copyProperties(app, appEntity);
				
				appEntity.setUser(userEntity);
				
				appEntity = appRepo.save(appEntity);
				return "App created with Case Num :"+appEntity.getCaseNum();
			}
			
		} catch (Exception e) {
			throw new SsaWebException(e.getMessage());
		}
		
		return AppConstatnts.INVALID_SSN;
	}

	@Override
	public List<App> fetchApps(Integer userId) {
		
		UserEntity userEntity = userRepo.findById(userId).get();
        Integer roleId = userEntity.getRoleId();
        
        List<AppEntity> appEntities = null ;
        
        if (1 == roleId) {
        	appEntities = appRepo.fetchUserApps(); 
		}else {
			appEntities = appRepo.fetchCwApps(userId);
		}
        
        List<App> apps = new ArrayList<>();
        
        for(AppEntity entity : appEntities) {
            App app = new App();
            BeanUtils.copyProperties(entity, apps);
            apps.add(app);
        
       }
		return apps;
	}

}
