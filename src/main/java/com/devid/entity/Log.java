package com.devid.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.test.context.transaction.TransactionConfiguration;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="t_log")
public class Log {
	
	public static final String LOGIN_ACTION="登录操作";
	public static final String LOGOUT_ACTION="退出操作";
	public static final String SEARCH_ACTION="查询操作";
	public static final String INSERT_ACTION="添加操作";
	public static final String UPDATE_ACTION="修改操作";
	public static final String DELETE_ACTION="删除操作";

	@Id
	@GeneratedValue	
	private Integer id;
	
	@Column(length=20)
	private String name;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date operateTime;
	
	@Column(length=500)
	private String content;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;
	
	@Transient
	private Date btime;
	
	@Transient
	private Date etime;
	
	public Log(String name, String content) {
		super();
		this.name = name;
		this.content = content;
	}
	
	public Log() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Date getBtime() {
		return btime;
	}

	public void setBtime(Date btime) {
		this.btime = btime;
	}

	public Date getEtime() {
		return etime;
	}

	public void setEtime(Date etime) {
		this.etime = etime;
	}

	
}
