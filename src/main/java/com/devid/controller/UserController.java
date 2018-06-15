package com.devid.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devid.entity.Log;
import com.devid.entity.Menu;
import com.devid.entity.Role;
import com.devid.entity.User;
import com.devid.service.LogService;
import com.devid.service.MenuService;
import com.devid.service.RoleService;
import com.devid.service.UserService;
import com.devid.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	
	@ResponseBody
	@RequestMapping("/login")
	public Map<String,Object> login(String imageCode,@Valid User user,BindingResult bindingResult,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		if(bindingResult.hasFieldErrors("userName")) {
			map.put("success", false);
			map.put("errorMsg", bindingResult.getFieldError("userName").getDefaultMessage());
			return map;
		}
		if(bindingResult.hasFieldErrors("password")) {
			map.put("success", false);
			map.put("errorMsg", bindingResult.getFieldError("password").getDefaultMessage());
			return map;
		}
		if(StringUtil.isEmpty(imageCode)) {
			map.put("success", false);
			map.put("errorMsg", "请输入验证码！");
			return map;
		}
		if(!session.getAttribute("checkcode").equals(imageCode)) {
			map.put("success", false);
			map.put("errorMsg", "验证码错误！");
			return map;
		}
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try{
			subject.login(token);
			String userName = (String) SecurityUtils.getSubject().getPrincipal();
			User currentUser = userService.findUserByUserName(userName);
			session.setAttribute("currentUser", currentUser);
			List<Role> roleList = roleService.findByUserId(currentUser.getId());//获取登录用户的角色集合
			map.put("roleList", roleList);
			map.put("roleSize", roleList.size());
			map.put("success", true);
			logService.save(new Log(Log.LOGIN_ACTION,"用户登录"));
			return map;
		}catch(Exception e){
			e.printStackTrace();
			map.put("success", false);
			map.put("errorMsg", "用户名或者密码错误!");
			return map;
		}
	}
	
	/**
	 * 保存用户角色
	 * @return Map<String,Object>
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	public Map<String, Object> saveRole(String roleId,HttpSession session){
		Map<String,Object> map = new HashMap<String,Object>();
		Role currentRole = roleService.findById(Integer.parseInt(roleId));
		session.setAttribute("currentRole", currentRole);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 加载当前登录用户信息
	 * @param session
	 * @return
	 * @throws Exception String
	 */
	@ResponseBody
	@RequestMapping("/loadInfo")
	public String loadInfo(HttpSession session) {
		User currentUser = (User) session.getAttribute("currentUser");
		Role currentRole = (Role) session.getAttribute("currentRole");
		return "欢迎您："+currentUser.getUserName()+"&nbsp;[&nbsp;"+currentRole.getName()+"&nbsp;]";
	}
	
	/**
	 * 加载树菜单信息
	 * @param parentId
	 * @param session
	 * @return
	 * @throws Exception String
	 */
	@ResponseBody
	@RequestMapping("/loadMenuInfo")
	public String loadMenuInfo(String parentId,HttpSession session) {
		Role currentRole = (Role) session.getAttribute("currentRole");
		return getAllMenu(Integer.parseInt(parentId),currentRole.getId()).toString();
	}
	
	/**
	 * 递归获取所有菜单
	 * @param parentId
	 * @param roleId
	 * @return
	 * @throws Exception JsonArray
	 */
	public JsonArray getAllMenu(Integer parentId, Integer roleId) {
		JsonArray jsonArray = new JsonArray();
		jsonArray = this.getMenuByPidAndRid(parentId,roleId);
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllMenu(jsonObject.get("id").getAsInt(), roleId));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 根据parentId和roleId获取一级菜单
	 * @param parentId
	 * @param roleId
	 * @return JsonArray
	 */
	public JsonArray getMenuByPidAndRid(Integer parentId, Integer roleId) {
		JsonArray jsonArray = new JsonArray();
		List<Menu> menuList = menuService.findMenuByPidAndRid(parentId, roleId);
		for (Menu menu : menuList) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", menu.getId());
			jsonObject.addProperty("text", menu.getName());
			jsonObject.addProperty("iconCls", menu.getIcon());
			if(menu.getState()==1) {
				jsonObject.addProperty("state","closed"); //下面有子节点
			}else {
				jsonObject.addProperty("state","open"); // 下面无子节点
			}
			JsonObject attributes = new JsonObject();
			attributes.addProperty("url", menu.getUrl());
			jsonObject.add("attributes", attributes);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
}


