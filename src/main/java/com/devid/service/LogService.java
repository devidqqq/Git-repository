package com.devid.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.devid.entity.Log;

public interface LogService {

	/**
	 * 添加日志
	 * @param log void
	 */
	public void save(Log log);
	
	/**
	 * 分页带条件查询日志信息
	 * @param log
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return List<Log>
	 */
	public List<Log> list(Log log,Integer page,Integer pageSize,Direction direction, String...properties);
	
	/**
	 * 查询总记录数
	 * @param log
	 * @return Long
	 */
	public Long getCount(Log log);
}
