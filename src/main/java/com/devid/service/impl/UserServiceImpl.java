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

import com.devid.entity.User;
import com.devid.repository.UserRepository;
import com.devid.service.UserService;
import com.devid.util.StringUtil;

@Service("userService")

public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserByUserName(String userName) {
		return userRepository.findUserByUserName(userName);
	}

	@Override
	public List<User> list(User user, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<User> pageUser = userRepository.findAll(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				if(user!=null) {
					if(StringUtil.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"), "%"+user.getUserName()+"%"));
					}
					if(StringUtil.isNotEmpty(user.getTrueName())) {
						predicate.getExpressions().add(criteriaBuilder.like(root.get("trueName"), "%"+user.getTrueName()+"%"));
					}
					predicate.getExpressions().add(criteriaBuilder.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
		},pageable);
		return pageUser.getContent();
	}

	@Override
	public Long getCount(User user) {
		Long count = userRepository.count(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Predicate predicate = criteriaBuilder.conjunction();
				if(user!=null) {
					if(StringUtil.isNotEmpty(user.getUserName())) {
						predicate.getExpressions().add(criteriaBuilder.like(root.get("userName"), "%"+user.getUserName()+"%"));
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
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void delete(Integer id) {
		userRepository.delete(id);
	}

	@Override
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}

	
}


