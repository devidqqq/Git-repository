package com.devid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devid.entity.Menu;

public interface MenuRepository extends JpaRepository<Menu, Integer>{
	
	/**
	 *根据父节点id和角色id查找菜单 
	 * @param userName
	 * @return User
	 */
	@Query(value="SELECT * FROM t_menu WHERE p_id=?1 AND id IN (SELECT menu_id FROM `t_role_menu` WHERE role_id=?2)",nativeQuery=true)
	public List<Menu> findMenuByPidAndRid(Integer parentId, Integer roleId);
	
	/**
	 * 根据角色id查询所有菜单权限
	 * @param roleId
	 * @return List<Menu>
	 */
	@Query(value="SELECT m.* FROM t_menu m,t_role_menu rm,t_role r WHERE rm.`role_id`=r.`id` AND rm.`menu_id`=m.`id` AND r.`id`=?1",nativeQuery=true)
	public List<Menu> findByRoleId(Integer roleId);
	
	/**
	 * 根据父节点查找下一级子节点
	 * @param pId
	 * @return List<Menu>
	 */
	@Query(value="SELECT * FROM t_menu WHERE p_id=?1",nativeQuery=true)
	public List<Menu> findByPid(Integer pId);
	
}


