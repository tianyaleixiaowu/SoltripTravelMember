package com.s.t.m.common.exceptions;

import com.s.t.m.common.result.JsonResult;
import com.s.t.m.common.result.ReturnType;

import java.util.logging.Logger;


/**
 * 通用异常
 *
 * @author Bai
 */
public class BusiException extends RuntimeException {
    private static final long serialVersionUID = -8095232506548749734L;
    private static final Logger log = Logger.getLogger("BusiException");
    private Exception exc;
    private ReturnType returnType;


    public Exception getCause() {
        return exc;
    }

    public BusiException(Exception ex) {
        exc = ex;
    }

    public BusiException(Exception ex, ReturnType ce) {
        exc = ex;
        returnType = ce;
    }

    public BusiException(ReturnType ce) {
        returnType = ce;
    }

    public ReturnType getCodeEnum() {
        return returnType;
    }

    public String getCode() {
        return returnType != null ? returnType.getCode() : "未知错误码";
    }

    public String getMsgKey() {
        return returnType != null ? returnType.getMsgKey() : "未知错误解析主键";
    }

    public String getMsgVal() {
        return returnType != null ? returnType.getMsgVal() : "未知错误解析解释";
    }

    public JsonResult<?> createJsonResult() {
        log.severe("---捕获到异常 e={},{}" + returnType.getCode() + returnType.getMsgKey());
        return JsonResult.error(returnType);
    }
}
