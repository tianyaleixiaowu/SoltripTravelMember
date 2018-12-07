package com.s.t.m.common.exceptions;

import com.s.t.m.common.result.JsonResult;
import com.s.t.m.common.result.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * 捕获Contoller层的异常信息
 * @author Bai
 *
 */
@ControllerAdvice
public class ExceptionHandle {
	private static final Logger log = Logger.getLogger("ExceptionHandle");
	/**
	 * 统一截获Exception
	 * @param e
	 * @return JsonResult
	 */
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public JsonResult<?> handle(Exception e,HttpServletRequest request) {
        if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
        	return JsonResult.error(R.NOT_FOUND,"接口 [" + request.getRequestURI() + "] 不存在");
        } else {
        	return getExceptionAsJson(e);
        }
	}
	

	/**
	 * 将Exception转化为JsonResult.
	 */
	public static JsonResult<?> getExceptionAsJson(Exception e) {
		log.log(Level.SEVERE, "捕获到异常: ", e);
		if(e instanceof BusiException){
			return ((BusiException)e).createJsonResult();
		}else {
			return JsonResult.error(R.FAILURE);
		}
		
	}
	
}
