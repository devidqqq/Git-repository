package com.devid.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devid.entity.Log;
import com.devid.entity.Role;
import com.devid.entity.User;
import com.devid.entity.UserRole;
import com.devid.service.LogService;
import com.devid.service.RoleService;
import com.devid.service.UserRoleService;
import com.devid.service.UserService;
import com.devid.util.StringUtil;

@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 查找除超级管理员外的所有用户信息及其拥有的角色
	 * @param user
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions(value="用户管理")
	public Map<String, Object> list(User user,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<User> userList = userService.list(user, page, rows, Direction.ASC, "id");
		for (User u : userList) {
			List<Role> roleList = roleService.findByUserId(u.getId());
			StringBuffer sb = new StringBuffer();
			for (Role role : roleList) {
				sb.append(",");
				sb.append(role.getName());
			}
			u.setRoles(sb.toString().replaceFirst(",", ""));
		}
		Long total = userService.getCount(user);
		map.put("rows", userList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询用户信息"));
		return map;
	}
	
	/**
	 * 保存用户
	 * @param user
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions(value="用户管理")
	public Map<String,Object> save(User user) {
		Map<String,Object> map = new HashMap<>();
		if(user.getId()==null) {
			if(userService.findUserByUserName(user.getUserName())!=null) {
				map.put("success", false);
				map.put("errorMsg", "用户名已存在！");
				return map;
			}
		}
		if(user.getId()==null) {
			logService.save(new Log(Log.INSERT_ACTION,"添加用户"+user));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION,"修改用户信息"+user));
		}
		userService.save(user);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions(value="用户管理")
	public Map<String,Object> delete(Integer id) {
		logService.save(new Log(Log.DELETE_ACTION,"删除用户信息"+userService.findById(id)));
		Map<String,Object> map = new HashMap<>();
		userRoleService.deleteByUserId(id); //删除用户角色对应关系
		userService.delete(id);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 设置用户角色信息
	 */
	@RequestMapping("/setUserRole")
	@ResponseBody
	@RequiresPermissions(value="用户管理") //@RequiresPermissions(value= {"权限一","权限二"},logical=Logical.OR)
	public Map<String,Object> setUserRole(String roleIds, Integer userId){
		Map<String,Object> map = new HashMap<String, Object>();
		userRoleService.deleteByUserId(userId);//删除已有的用户角色关系
		//重新设置并保存用户角色关系
		if(StringUtil.isNotEmpty(roleIds)) {
			String[] roleIdStr = roleIds.split(",");
			for (String roleId : roleIdStr) {
				UserRole userRole = new UserRole();
				userRole.setRole(roleService.findById(Integer.parseInt(roleId)));
				userRole.setUser(userService.findById(userId));
				userRoleService.saveUserRole(userRole );
			}
		}
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改用户角色"+userService.findById(userId)));
		return map;
	}
	
	@RequestMapping("/modifyPossword")
	@ResponseBody
	@RequiresPermissions(value="修改密码") 
	public Map<String , Object> modifyPossword(String newPassword,HttpSession session){
		Map<String,Object> map = new HashMap<String, Object>();
		User currentUser = (User) session.getAttribute("currentUser");
		currentUser.setPassword(newPassword);
		userService.save(currentUser);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改密码"));
		return map;
	}
	
	@RequestMapping("/logout")
	@RequiresPermissions(value="安全退出") 
	public String logout() {
		logService.save(new Log(Log.LOGOUT_ACTION,"注销登录"));
		SecurityUtils.getSubject().logout();
		return "redirect:/login.html";
	}
}


