package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.Supplier;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface SupplierService {

	
	/**
	 * 根据供应商id查找供应商
	 * @param id
	 * @return Supplier
	 */
	public Supplier findById(Integer id);
	
	/**
	 * 根据条件分页查询供应商信息
	 * @param supplier 供应商实体
	 * @param page 起始页
	 * @param pageSize 每页大小
	 * @param direction 封装排序规则
	 * @param properties 查询条件
	 * @return List<Supplier>
	 */
	public List<Supplier> list(Supplier supplier,Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 
	 * 2018年3月14日
	 * @param supplier
	 * @return Long
	 *
	 */
	public Long getCount(Supplier supplier);
	
	/**
	 * 添加或修改供应商
	 * @param supplier
	 * @return void
	 */
	public void save(Supplier supplier);
	
	/**
	 * 删除供应商
	 * @param id 
	 * @return void
	 */
	public void delete(Integer id);
}


