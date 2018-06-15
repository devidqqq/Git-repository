package com.devid.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.devid.entity.Role;
import com.devid.repository.RoleRepository;
import com.devid.service.RoleService;
import com.devid.util.StringUtil;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Resource
	private RoleRepository roleRepository;
	
	@Override
	public List<Role> findByUserId(Integer id) {
		return roleRepository.findByUserId(id);
	}

	@Override
	public Role findById(Integer id) {
		return roleRepository.findOne(id);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}


	@Override
	public List<Role> list(Role role, Integer page, Integer pageSize, Direction direction, String... properties) {
		Pageable pageable = new PageRequest(page-1, pageSize);
		Page<Role> pageRole = roleRepository.findAll(new Specification<Role>() {
			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(role!=null) {
					if(StringUtil.isNotEmpty(role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+role.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
			
		}, pageable);
		return pageRole.getContent();
	}

	@Override
	public Long getCount(Role role) {
		Long count = roleRepository.count(new Specification<Role>() {

			@Override
			public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(role!=null) {
					if(StringUtil.isNotEmpty(role.getName())) {
						predicate.getExpressions().add(cb.like(root.get("name"), "%"+role.getName()+"%"));
					}
					predicate.getExpressions().add(cb.notEqual(root.get("id"), 1));
				}
				return predicate;
			}
			
		});
		return count;
	}

	@Override
	public void save(Role role) {
		roleRepository.save(role);
	}

	@Override
	public void delete(Integer id) {
		roleRepository.delete(id);
	}

	@Override
	public Role findRoleByRoleName(String name) {
		// TODO Auto-generated method stub
		return roleRepository.findRoleByRoleName(name);
	}

}


