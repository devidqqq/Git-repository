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
import com.devid.entity.Supplier;
import com.devid.service.LogService;
import com.devid.service.SupplierService;

@Controller
@RequestMapping("/admin/supplier")
public class SupplierAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private SupplierService supplierService;
	
	
	/**
	 * 查找所有供应商信息
	 * @param supplier
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions(value="供应商管理")
	public Map<String, Object> list(Supplier supplier,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<Supplier> supplierList = supplierService.list(supplier, page, rows, Direction.ASC, "id");
		Long total = supplierService.getCount(supplier);
		map.put("rows", supplierList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询供应商信息"));
		return map;
	}
	
	/**
	 * 保存供应商
	 * @param supplier
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions(value="供应商管理")
	public Map<String,Object> save(Supplier supplier) {
		Map<String,Object> map = new HashMap<>();
		if(supplier.getId()==null) {
			logService.save(new Log(Log.INSERT_ACTION,"添加供应商"+supplier));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION,"修改供应商信息"+supplier));
		}
		supplierService.save(supplier);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除供应商
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions(value="供应商管理")
	public Map<String,Object> delete(String ids) {
		String[] idStrs = ids.split(",");
		for (String id : idStrs) {
			logService.save(new Log(Log.DELETE_ACTION,"删除供应商信息"+supplierService.findById(Integer.parseInt(id))));
			supplierService.delete(Integer.parseInt(id));
		}
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
}


