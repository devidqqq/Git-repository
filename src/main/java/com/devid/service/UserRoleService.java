package com.devid.service;

import com.devid.entity.UserRole;

public interface UserRoleService {

	/**
	 * 根据用户id删除关联的用户角色信息
	 * @param userId void
	 */
	public void deleteByUserId(Integer userId);
	
	/**
	 * 保存用户角色关系实体
	 * @param userRole void
	 */
	public void saveUserRole(UserRole userRole);

	/**
	 * 根据角色id删除关联的用户角色信息
	 * @param userId void
	 */
	public void deleteByRoleId(Integer id);
}
