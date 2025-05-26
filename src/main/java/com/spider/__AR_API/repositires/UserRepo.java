package com.spider.__AR_API.repositires;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.spider.__AR_API.entites.UserEntity;


public interface UserRepo extends JpaRepository<UserEntity, Integer> {

	@Query("update UserEntity set accStatus=:status where userId=:userId")
    public Integer updateAccStatus(Integer userId, String status);

    public UserEntity findByEmail(String email);

    public UserEntity findByEmailAndPwd(String email, String pwd);

	
}
