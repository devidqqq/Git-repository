package com.devid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.devid.entity.User;
import com.devid.entity.UserRole;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>,JpaSpecificationExecutor<UserRole>{
	
	/**
	 * 根据用户id删除关联的用户角色信息
	 * @param userId void
	 */
	@Query(value="delete from t_user_role where user_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByUserId(Integer userId);
	
	/**
	 * 根据角色id删除关联的用户角色信息
	 * @param userId void
	 */
	@Query(value="delete from t_user_role where role_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByRoleId(Integer id);
}


