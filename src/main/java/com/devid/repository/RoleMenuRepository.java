package com.devid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.devid.entity.RoleMenu;
import com.devid.entity.User;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer>,JpaSpecificationExecutor<User>{
	
	/**
	 * 根据角色id删除关联的用户角色信息
	 * @param roleId void
	 */
	@Query(value="delete from t_role_menu where role_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByRoleId(Integer roleId);
}


