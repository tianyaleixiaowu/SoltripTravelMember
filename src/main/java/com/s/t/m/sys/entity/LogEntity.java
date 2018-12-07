package com.s.t.m.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.s.t.m.common.pojo.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel(value="日志对象")
public class LogEntity extends BaseEntity {
	
	/**
	 * 主键
	 */
	@ApiModelProperty(value="主键",name="id")
	private Long id;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id",name="userId")
	private Long userId;
	/**
	 * 用户名称
	 */
	@ApiModelProperty(value="用户姓名",name="username")
	private String username;
	/**
	 * 方法上描述
	 */
	@ApiModelProperty(value="方法上描述",name="operation")
	private String operation;
	/**
	 * 执行时长
	 */
	@ApiModelProperty(value="执行时长",name="time")
	private Integer time;
	/**
	 * 方法全路径
	 */
	@ApiModelProperty(value="方法全路径",name="method")
	private String method;
	/**
	 * 请求参数
	 */
	@ApiModelProperty(value="请求参数",name="params")
	private String params;
	/**
	 * 请求ip
	 */
	@ApiModelProperty(value="请求ip",name="ip")
	private String ip;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value="创建时间",name="gmtCreate")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	private Date gmtCreate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation == null ? null : operation.trim();
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method == null ? null : method.trim();
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params == null ? null : params.trim();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public Date getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	@Override
	public String toString() {
		return "LogEntity [id=" + id + ", userId=" + userId + ", username=" + username + ", operation=" + operation
				+ ", time=" + time + ", method=" + method + ", params=" + params + ", ip=" + ip + ", gmtCreate="
				+ gmtCreate + "]";
	}

	
}
