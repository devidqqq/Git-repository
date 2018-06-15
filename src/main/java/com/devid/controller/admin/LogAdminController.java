package com.devid.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devid.entity.Log;
import com.devid.service.LogService;

@RestController
@RequestMapping("/admin/log")
public class LogAdminController {

	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	
	/**
	 * 分页查询日志信息
	 * @param log
	 * @param page
	 * @param rows
	 * @return Map<String,Object>
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="系统日志")
	public Map<String, Object> list(Log log,@RequestParam(value="page")Integer page,@RequestParam(value="rows")Integer rows){
		Map<String, Object> map = new HashMap<>();
		List<Log> logList = logService.list(log, page, rows, Direction.DESC, "operateTime");
		Long total = logService.getCount(log);
		map.put("rows", logList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION,"查询日志信息"));
		return map;
	}
}
