package com.s.t.m.common.result;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 服务端返回给客户端的数据封装对象
 */
@ApiModel(value="返回值对象")
public class JsonResult<T> implements Serializable {

    
	private static final long serialVersionUID = -7227854916001288491L;

	/**
     * 服务端业务逻辑是否执行成功
     */
	@ApiModelProperty(value="是否执行成功",name="success")
    private boolean success;

    /**
     * 错误编号
     */
	@ApiModelProperty(value="错误编号",name="errorCode")
    private String errorCode = "";

    /**
     * 信息(如果发生错误，那么代表错误信息)
     */
	@ApiModelProperty(value="信息(如果发生错误，那么代表错误信息)",name="message")
    private String message = "";

    /**
     * 返回给客户端的数据对象
     */
	@ApiModelProperty(value="返回给客户端的数据对象",name="data")
    private T data;

    public JsonResult(){

    }

   public JsonResult(boolean success, ReturnType returnType, T data){
        this.success = success;
        this.errorCode = returnType.getCode();
        this.message = returnType.getMsgVal();
        this.data = data;
    }

    /**
     * 返回一个代表成功的JsonResult，无返回对象，无信息
     *
     * @return 成功的JsonResult
     */
    public static <T> JsonResult<T> success() {
        return new JsonResult<>(true, R.OK, null);
    }

    /**
     * 返回一个代表成功的JsonResult，包括返回的数据
     *
     * @param data 需要返回的数据
     * @return 成功的JsonResult
     */
    public static <T> JsonResult<T> success(T data) {
        return new JsonResult<>(true, R.OK, data);
    }
    
    public static <T> JsonResult<T> success(T data,ReturnType returnType) {
        return new JsonResult<>(true, returnType, data);
    }

    /**
     * 返回一个代表失败的JsonResult，包含错误信息
     * @param ReturnType
     * @param data
     * @return
     */
    public static <T> JsonResult<T> error(ReturnType returnType,T data){
        return new JsonResult<>(false,returnType,data);
    }

    public static <T> JsonResult<T> error(ReturnType returnType) {
        return new JsonResult<>(false, returnType, null);
    }

    public static <T> JsonResult<T> success(ReturnType returnType) {
        return new JsonResult<>(true, returnType, null);
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JsonResult [success=" + success + ", errorCode=" + errorCode + ", message=" + message + ", data=" + data
				+ "]";
	}

	
}