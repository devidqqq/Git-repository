package com.devid.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.devid.entity.Menu;

public interface MenuService {

	/**
	 *根据父节点id和角色id查找菜单 
	 * @param userName
	 * @return User
	 */
	public List<Menu> findMenuByPidAndRid(Integer parentId, Integer roleId);
	
	/**
	 * 根据父节点查找下一级子节点
	 * @param pId
	 * @return List<Menu>
	 */
	public List<Menu> findByPid(Integer pId);
	
	/**
	 * 根据角色id查询所有菜单权限
	 * @param roleId
	 * @return List<Menu>
	 */
	public List<Menu> findByRoleId(Integer roleId);

	/**
	 * 根据Id查找菜单
	 * @param parseInt
	 * @return Menu
	 */
	public Menu findById(Integer id);
}


