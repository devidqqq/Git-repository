package com.devid.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.devid.entity.Menu;
import com.devid.entity.Role;
import com.devid.entity.User;
import com.devid.repository.MenuRepository;
import com.devid.repository.RoleRepository;
import com.devid.repository.UserRepository;

public class MyRealm extends AuthorizingRealm{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	/**
	 * 自定义权限验证
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Set<String> roles = new HashSet<>();
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUserName(userName);
		List<Role> roleList = roleRepository.findByUserId(user.getId());//一个用户对应多个角色
		for (Role role : roleList) {
			roles.add(role.getName());
			List<Menu> menuList = menuRepository.findByRoleId(role.getId());//一个角色对应多个菜单权限
			for (Menu menu : menuList) {
				info.addStringPermission(menu.getName());//添加权限
			}
		}
		info.setRoles(roles);//添加角色
		return info;
	}

	/**
	 * 自定义身份验证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		User user = userRepository.findUserByUserName(userName);
		if(user!=null) {
			AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUserName(),user.getPassword(),"");
			return authenticationInfo;
		}else {
			return null;
		}
	}

}


