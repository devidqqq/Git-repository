package com.devid.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devid.entity.UserRole;
import com.devid.repository.UserRoleRepository;
import com.devid.service.UserRoleService;

/**
 * 2018年3月14日
 * Author 10172
 */
@Service("userRoleService")
@Transactional
public class UserRoleServiceImpl implements UserRoleService{

	@Resource
	private UserRoleRepository userRoleRepository;
	
	@Override
	public void deleteByUserId(Integer userId) {
		userRoleRepository.deleteByUserId(userId);
	}

	@Override
	public void saveUserRole(UserRole userRole) {
		userRoleRepository.save(userRole);
	}

	@Override
	public void deleteByRoleId(Integer id) {
		userRoleRepository.deleteByRoleId(id);
	}
}
