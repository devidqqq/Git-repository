package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.User;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface UserService {

	/**
	 *根据商品名查找商品实体 
	 * @param userName
	 * @return User
	 */
	public User findUserByUserName(String userName);
	
	/**
	 * 根据商品id查找商品
	 * @param id
	 * @return User
	 */
	public User findById(Integer id);
	
	/**
	 * 根据条件分页查询商品信息
	 * @param user 商品实体
	 * @param page 起始页
	 * @param pageSize 每页大小
	 * @param direction 封装排序规则
	 * @param properties 查询条件
	 * @return List<User>
	 */
	public List<User> list(User user,Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 
	 * 2018年3月14日
	 * @param user
	 * @return Long
	 *
	 */
	public Long getCount(User user);
	
	/**
	 * 添加或修改商品
	 * @param user
	 * @return void
	 */
	public void save(User user);
	
	/**
	 * 删除商品
	 * @param id 
	 * @return void
	 */
	public void delete(Integer id);
}


