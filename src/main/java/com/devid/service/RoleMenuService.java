package com.devid.service;

import com.devid.entity.RoleMenu;

public interface RoleMenuService {

	/**
	 * 根据角色id删除关联的用户角色信息
	 * @param roleId void
	 */
	public void deleteByRoleId(Integer roleId);

	/**
	 * 保存角色菜单关系
	 * @param roleMenu void
	 */
	public void save(RoleMenu roleMenu);
	
}
