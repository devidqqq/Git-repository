package com.devid.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devid.entity.Menu;
import com.devid.repository.MenuRepository;
import com.devid.service.MenuService;

@Service("menuService")
public class MenuServiceImpl implements MenuService{

	@Autowired
	private MenuRepository menuRepository;

	@Override
	public List<Menu> findMenuByPidAndRid(Integer parentId, Integer roleId) {
		return menuRepository.findMenuByPidAndRid(parentId, roleId);
	}

	@Override
	public List<Menu> findByPid(Integer pId) {
		return menuRepository.findByPid(pId);
	}

	@Override
	public List<Menu> findByRoleId(Integer roleId) {
		return menuRepository.findByRoleId(roleId);
	}

	@Override
	public Menu findById(Integer id) {
		return menuRepository.findOne(id);
	}


}


