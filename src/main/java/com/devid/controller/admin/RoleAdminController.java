package com.devid.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devid.entity.Log;
import com.devid.entity.Menu;
import com.devid.entity.Role;
import com.devid.entity.RoleMenu;
import com.devid.service.LogService;
import com.devid.service.MenuService;
import com.devid.service.RoleMenuService;
import com.devid.service.RoleService;
import com.devid.service.UserRoleService;
import com.devid.util.StringUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


/**
 * 角色后台管理
 * @author 10172
 * 2018年3月14日
 */
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private RoleMenuService roleMenuService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 获取所有角色信息
	 * @return Map<String,Object>
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> list(){
		Map<String,Object> map = new HashMap<String, Object>();
		List<Role> roleList = roleService.findAll();
		map.put("rows", roleList);
		logService.save(new Log(Log.SEARCH_ACTION,"查询所有角色信息"));
		return map;
	}
	
	/**
	 * 获取除管理员外的角色信息，带条件和分页
	 * @param role
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> list(Role role,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<Role> roleList = roleService.list(role, page, rows, Direction.ASC, "id");
		Long total = roleService.getCount(role);
		map.put("rows", roleList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询角色信息"));
		return map;
	}
	
	/**
	 * 保存角色，添加和修改
	 * @param user
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> save(Role role) {
		Map<String,Object> map = new HashMap<>();
		if(role.getId()==null) {
			if(roleService.findRoleByRoleName(role.getName())!=null) {
				map.put("success", false);
				map.put("errorMsg", "角色已存在！");
				return map;
			}
		}
		if(role.getId()!=null) {
			logService.save(new Log(Log.UPDATE_ACTION,"修改角色信息"+role));
		}else {
			logService.save(new Log(Log.INSERT_ACTION,"添加角色"+role));
		}
		roleService.save(role);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除角色
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="角色管理")
	public Map<String,Object> delete(Integer id) {
		Map<String,Object> map = new HashMap<>();
		logService.save(new Log(Log.DELETE_ACTION,"删除角色信息"+roleService.findById(id)));
		userRoleService.deleteByRoleId(id); //根据角色id删除对应的用户角色关系
		roleMenuService.deleteByRoleId(id); //根据角色id删除对应的菜单权限关系
		roleService.delete(id);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 设置菜单权限
	 * 	1.获取当前角色拥有的菜单权限
	 * 		1).根据父节点Id1获取一级菜单
	 * 		2).判断获取的每个节点是否有子节点
	 * 		3).若有子节点，用自身节点作为父节点，获取下一级子节点
	 * 		4).递归过去所有菜单
	 * 		5).根据当前角色Id获取当前角色所拥有的菜单权限
	 * 		6).在所有菜单列表中选中当前角色所拥有的菜单
	 *  2.重新设置菜单所拥有的权限
	 *  	1).删除原有的角色菜单关联
	 *  	2).根据前台传过来的数据重新设置角色所拥有的菜单权限
	 * @return String
	 */
	@RequestMapping("/getRoleMenu")
	@RequiresPermissions(value="角色管理")
	public String getRoleMenu(String pId,Integer roleId) {
		List<Menu> menuList = menuService.findByRoleId(roleId);
		List<Integer> muenIdList = new ArrayList<>();
		for (Menu menu : menuList) {
			muenIdList.add(menu.getId());
		}
		JsonArray jsonArray = this.getAllMenu(Integer.parseInt(pId),muenIdList);
		
		return jsonArray.toString();
	}
	
	/**
	 * 获取所有的菜单
	 * @return JsonArray
	 */
	public JsonArray getAllMenu(Integer pId,List<Integer> muenIdList) {
		JsonArray jsonArray = this.getFirstLevalMenu(pId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			if(muenIdList.contains(jsonObject.get("id").getAsInt())) {
				jsonObject.addProperty("checked", true); //判断当前角色是否拥有该菜单权限
			}
			if((jsonObject.get("state").getAsString()).equals("open")) {//判断下面是否有子节点
				continue;
			}else{
				jsonObject.add("children", getAllMenu(jsonObject.get("id").getAsInt(),muenIdList));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 根据父节点第一级菜单
	 * @param pid
	 * @return JsonArray
	 */
	public JsonArray getFirstLevalMenu(Integer pId) {
		JsonArray jsonArray = new JsonArray();
		List<Menu> menuList = menuService.findByPid(pId);
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
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	/**
	 * 添加或修改角色拥有的菜单权限
	 * @param roleId
	 * @param menuIds
	 * @return Map<String,Object>
	 */
	@RequestMapping("/saveRoleMenu")
	@RequiresPermissions(value="角色管理")
	public Map<String, Object> saveRoleMenu(Integer roleId,String menuIds){
		Map<String,Object> map = new HashMap<>();
		roleMenuService.deleteByRoleId(roleId); //根据角色id删除对应的菜单权限关系
		if(StringUtil.isNotEmpty(menuIds)) {
			String[] menuIdStrs = menuIds.split(",");
			for (String menuId : menuIdStrs) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setMenu(menuService.findById(Integer.parseInt(menuId)));
				roleMenu.setRole(roleService.findById(roleId));
				roleMenuService.save(roleMenu);
			}
		}
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改角色权限"+roleService.findById(roleId)));
		return map;
	}
	
	
}
