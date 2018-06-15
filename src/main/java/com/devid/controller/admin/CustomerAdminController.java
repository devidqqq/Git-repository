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
import com.devid.entity.Customer;
import com.devid.service.LogService;
import com.devid.service.CustomerService;

@Controller
@RequestMapping("/admin/customer")
public class CustomerAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private CustomerService customerService;
	
	
	/**
	 * 查找所有客户信息
	 * @param customer
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/list")
	@ResponseBody
	@RequiresPermissions(value="客户管理")
	public Map<String, Object> list(Customer customer,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<Customer> customerList = customerService.list(customer, page, rows, Direction.ASC, "id");
		Long total = customerService.getCount(customer);
		map.put("rows", customerList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询客户信息"));
		return map;
	}
	
	/**
	 * 保存客户
	 * @param customer
	 * @return Map<String,Object>
	 * @throws Exception 
	 */
	@RequestMapping("/save")
	@ResponseBody
	@RequiresPermissions(value="客户管理")
	public Map<String,Object> save(Customer customer) {
		Map<String,Object> map = new HashMap<>();
		if(customer.getId()==null) {
			logService.save(new Log(Log.INSERT_ACTION,"添加客户"+customer));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION,"修改客户信息"+customer));
		}
		customerService.save(customer);
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除客户
	 * @param id
	 * @return Map<String,Object>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions(value="客户管理")
	public Map<String,Object> delete(String ids) {
		String[] idStrs = ids.split(",");
		for (String id : idStrs) {
			logService.save(new Log(Log.DELETE_ACTION,"删除客户信息"+customerService.findById(Integer.parseInt(id))));
			customerService.delete(Integer.parseInt(id));
		}
		Map<String,Object> map = new HashMap<>();
		map.put("success", true);
		return map;
	}
	
}


