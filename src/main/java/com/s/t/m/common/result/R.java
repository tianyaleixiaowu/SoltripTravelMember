package com.s.t.m.common.result;

/**
 * 状态枚举
 */
public enum R implements ReturnType {

	OK("200","","成功"),
	FAIL("400","","失败"),
    FAILURE("500","failure","服务器内部错误"),
	NOT_FOUND("404","error","错误请求路径"),
    UNAUTHORIZED("401", "unauthorized","签名认证失败,请联系管理员"),
	WAITTING("0-0", "waitting", "等待"), 
	STARTED("0-1", "started", "开始"), 
	END("0-2", "end","结束"),
	INSERT("0-3", "insert","新增失败"),
	ELETE("0-4", "elete","删除失败"),
	UPDATE("0-5", "update","修改失败"),
	SELECT("0-6", "select","查询失败"),
	LONGIN_ERROR("0-7","longin_error","登录失败,账号或密码错误"),
	LOGIN("0-8","login","请登录"),
	LOGIN_DATE("0-9","login_date","登录过期，请从新登录"),
	IS_LOGIN("1-0","is_login","账号已在它处登录,请从新登录");

	private String code;
	private String msgKey;
	private String msgVal;

	R(String code, String msgKey, String msgVal) {
		this.code = code;
		this.msgKey = msgKey;
		this.msgVal = msgVal;
	}
	@Override
	public String getCode() {
		return code;
	}
	@Override
	public String getMsgKey() {
		return msgKey;
	}
	@Override
	public String getMsgVal() {
		return msgVal;
	}

	  //-----------------通过枚举属性获得枚举------------工具
  	/**
  	 * 返回指定编码的'枚举'
  	 * 
  	 * @param code
  	 * @return SharedObjTypeEnum
  	 * @throws
  	 */
  	public static <T extends ReturnType> T getEnumBycode(Class<T> clazz, String code) {
  		for (T _enum : clazz.getEnumConstants()) {
  			if (code.equals(_enum.getCode())) {
  				return _enum;
  			}
  		}
  		return null;
  	}

  	/**
  	 * 返回指定名称的'枚举'
  	 * 
  	 * @param msgKey
  	 * @return SharedObjTypeEnum
  	 * @throws
  	 */
  	public static <T extends ReturnType> T getEnumByName(Class<T> clazz, String msgKey) {
  		for (T _enum : clazz.getEnumConstants()) {
  			if (msgKey.equals(_enum.getMsgKey())) {
  				return _enum;
  			}
  		}
  		return null;
  	}

  	/**
  	 * 返回指定描述的'枚举'
  	 * 
  	 * @param msgVal
  	 * @return SharedObjTypeEnum
  	 * @throws
  	 */
  	public static <T extends ReturnType> T getEnumByDesc(Class<T> clazz, String msgVal) {
  		for (T _enum : clazz.getEnumConstants()) {
  			if (msgVal.equals(_enum.getMsgVal())) {
  				return _enum;
  			}
  		}
  		return null;
  	}


}