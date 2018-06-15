package com.devid.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devid.entity.Goods;
import com.devid.repository.GoodsRepository;
import com.devid.service.GoodsService;
import com.devid.util.StringUtil;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService{

	@Autowired
	private GoodsRepository goodsRepository;
	

	@Override
	public List<Goods> list(Goods goods, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Goods> pageGoods = goodsRepository.findAll(new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				if(goods!=null) {
					if(StringUtil.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%"+goods.getName()+"%"));
					}
					predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		},pageable);
		return pageGoods.getContent();
	}

	@Override
	public Long getCount(Goods goods) {
		Long count = goodsRepository.count(new Specification<Goods>() {

			@Override
			public Predicate toPredicate(Root<Goods> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				if(goods!=null) {
					if(StringUtil.isNotEmpty(goods.getName())) {
						predicate.getExpressions().add(criteriaBuilder.like(root.get("name"), "%"+goods.getName()+"%"));
					}
					predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		});
		return count;
	}

	@Override
	@Transactional
	public void save(Goods goods) {
		goodsRepository.save(goods);
	}

	@Override
	public void delete(Integer id) {
		goodsRepository.delete(id);
	}

	@Override
	public Goods findById(Integer id) {
		return goodsRepository.findOne(id);
	}

	@Override
	public List<Goods> findByTypeId(Integer typeId) {
		return goodsRepository.findByTypeId(typeId);
	}

	
}


