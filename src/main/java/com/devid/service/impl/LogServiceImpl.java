package com.devid.service.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.devid.entity.Log;
import com.devid.repository.LogRepository;
import com.devid.repository.UserRepository;
import com.devid.service.LogService;
import com.devid.util.StringUtil;

@Service("logService")
public class LogServiceImpl implements LogService{

	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void save(Log log) {
		log.setOperateTime(new Date());
		log.setUser(userRepository.findUserByUserName((String)SecurityUtils.getSubject().getPrincipal()));
		logRepository.save(log);
	}

	@Override
	public List<Log> list(Log log, Integer page, Integer pageSize,Direction direction, String... properties) {
		Pageable pageable = new PageRequest(page-1,pageSize,direction,properties);
		Page<Log> pageLog = logRepository.findAll(new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(StringUtil.isNotEmpty(log.getName())) {
					predicate.getExpressions().add(cb.like(root.get("name"), log.getName()));
				}
				if(log.getUser()!= null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
					predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
				}
				if(log.getBtime()!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("operateTime"), log.getBtime()));
				}
				if(log.getEtime()!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("operateTime"), log.getEtime()));
				}
				return predicate;
			}
		}, pageable);
		return pageLog.getContent();
	}

	@Override
	public Long getCount(Log log) {
		Long count = logRepository.count(new Specification<Log>() {
			@Override
			public Predicate toPredicate(Root<Log> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate = cb.conjunction();
				if(StringUtil.isNotEmpty(log.getName())) {
					predicate.getExpressions().add(cb.like(root.get("name"), log.getName()));
				}
				if(log.getUser()!= null && StringUtil.isNotEmpty(log.getUser().getTrueName())) {
					predicate.getExpressions().add(cb.like(root.get("user").get("trueName"), "%"+log.getUser().getTrueName()+"%"));
				}
				if(log.getBtime()!=null) {
					predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("operateTime"), log.getBtime()));
				}
				if(log.getEtime()!=null) {
					predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("operateTime"), log.getEtime()));
				}
				return predicate;
			}
		});
		return count;
	}

}
