package com.devid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.devid.entity.Goods;

/**
 * 
 * @author 10172
 * 2018年3月14日
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>,JpaSpecificationExecutor<Goods>{
	
	
	/**
	 * 根据商品类别id查找商品
	 * @param typeId
	 * @return List<Goods>
	 */
	@Query(value="SELECT * FROM t_goods WHERE type_id=?1",nativeQuery=true)
	public List<Goods> findByTypeId(Integer typeId);
}


