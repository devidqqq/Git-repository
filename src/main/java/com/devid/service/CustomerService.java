package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.Customer;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface CustomerService {

	
	/**
	 * 根据客户id查找客户
	 * @param id
	 * @return Customer
	 */
	public Customer findById(Integer id);
	
	/**
	 * 根据条件分页查询客户信息
	 * @param customer 客户实体
	 * @param page 起始页
	 * @param pageSize 每页大小
	 * @param direction 封装排序规则
	 * @param properties 排序条件
	 * @return List<Customer>
	 */
	public List<Customer> list(Customer customer,Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 
	 * 2018年3月14日
	 * @param customer
	 * @return Long
	 *
	 */
	public Long getCount(Customer customer);
	
	/**
	 * 添加或修改客户
	 * @param customer
	 * @return void
	 */
	public void save(Customer customer);
	
	/**
	 * 删除客户
	 * @param id 
	 * @return void
	 */
	public void delete(Integer id);
}


