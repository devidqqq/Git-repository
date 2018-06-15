package com.devid.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.devid.entity.Log;
import com.devid.entity.Goods;
import com.devid.service.LogService;
import com.devid.service.GoodsService;

@Controller
@RequestMapping("/admin/goods")
public class GoodsAdminController {

	@Resource
	private GoodsService goodsService;
	
	
	@Resource
	private LogService logService;
	
	/**
	 * 查找除超级管理员外的所有商品信息及其拥有的角色
	 * @param goods
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public Map<String, Object> list(Goods goods,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<Goods> goodsList = goodsService.list(goods, page, rows, Direction.ASC, "id");
		Long total = goodsService.getCount(goods);
		map.put("rows", goodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询商品信息"));
		return map;
	}
	
	/**
	 * 保存商品
	 * @param goods
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public Map<String,Object> save(Goods goods) {
		Map<String,Object> map = new HashMap<>();
		if(goods.getId()==null) {
			logService.save(new Log(Log.INSERT_ACTION,"添加商品"+goods));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION,"修改商品信息"+goods));
		}
		goodsService.save(goods);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除商品
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions(value="商品管理")
	public Map<String,Object> delete(Integer id) {
		logService.save(new Log(Log.DELETE_ACTION,"删除商品信息"+goodsService.findById(id)));
		Map<String,Object> map = new HashMap<>();
		goodsService.delete(id);
		map.put("success", true);
		return map;
	}
	
}


