package com.devid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.devid.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>,JpaSpecificationExecutor<User>{
	
	/**
	 *根据用户名查找用户实体 
	 * @param userName
	 * @return User
	 */
	@Query(value="select * from t_user where user_name = ?1",nativeQuery=true)
	public User findUserByUserName(String userName);
}


