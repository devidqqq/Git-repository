package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.Role;

public interface RoleService {

	/**
	 * 通过用户id查找用户角色集合
	 * @param id
	 * @return List<Role>
	 */
	public List<Role> findByUserId(Integer id);
	
	/**
	 * 通过id查找角色
	 * @param id
	 * @return Role
	 */
	public Role findById(Integer id);
	
	/**
	 * 查询所有角色
	 * @return List<Role>
	 */
	public List<Role> findAll();
	
	/**
	 * 根据条件分页查询用户信息
	 * @param role 用户实体
	 * @param page 起始页
	 * @param pageSize 每页大小
	 * @param direction 封装排序规则
	 * @param properties 查询条件
	 * @return List<Role>
	 */
	public List<Role> list(Role role,Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 
	 * 2018年3月14日
	 * @param role
	 * @return Long
	 *
	 */
	public Long getCount(Role role);
	
	/**
	 * 添加或修改用户
	 * @param role
	 * @return void
	 */
	public void save(Role role);
	
	/**
	 * 删除用户
	 * @param id 
	 * @return void
	 */
	public void delete(Integer id);

	/**
	 *根据角色名查找角色实体 
	 * @param name
	 * @return Role
	 */
	public Role findRoleByRoleName(String name);
}


