package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.Goods;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface GoodsService {

	
	/**
	 * 根据商品id查找商品
	 * @param id
	 * @return Goods
	 */
	public Goods findById(Integer id);
	
	/**
	 * 根据条件分页查询商品信息
	 * @param goods 商品实体
	 * @param page 起始页
	 * @param pageSize 每页大小
	 * @param direction 封装排序规则
	 * @param properties 查询条件
	 * @return List<Goods>
	 */
	public List<Goods> list(Goods goods,Integer page, Integer pageSize, Direction direction, String...properties);
	
	/**
	 * 
	 * 2018年3月14日
	 * @param goods
	 * @return Long
	 *
	 */
	public Long getCount(Goods goods);
	
	/**
	 * 添加或修改商品
	 * @param goods
	 * @return void
	 */
	public void save(Goods goods);
	
	/**
	 * 删除商品
	 * @param id 
	 * @return void
	 */
	public void delete(Integer id);
	
	/**
	 * 根据商品类别id查找商品
	 * @param typeId
	 * @return List<Goods>
	 */
	public List<Goods> findByTypeId(Integer typeId);
}


