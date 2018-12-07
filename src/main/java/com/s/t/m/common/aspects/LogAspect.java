package com.s.t.m.common.aspects;

import com.s.t.m.common.aspects.annotation.Log;
import com.s.t.m.common.token.BaseContext;
import com.s.t.m.common.utils.HttpContextUtils;
import com.s.t.m.common.utils.Utils;
import com.s.t.m.sys.entity.LogEntity;
import com.s.t.m.sys.service.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;


/**
 * 日志切面
 * 
 * @author Bai
 *
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
	
	@Autowired
    private LogQueue logQueue;
	
	/**
	 * 定义aop切点
	 */
	@Pointcut("@annotation(com.s.t.m.common.aspects.annotation.Log)")
	public void logPointCut() {
	}
	
	@Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        // 执行方法
        Object ob = point.proceed();
        // 执行时长(毫秒)
        long time = System.currentTimeMillis() - beginTime;
        //异步保存日志
        saveLog(point, time);
        return ob;
    }

    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogEntity sysLog = new LogEntity();
        Log syslog = method.getAnnotation(Log.class);
        if (syslog != null) {
            // 注解上的描述
            sysLog.setOperation(syslog.value());
        }
        // 请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        // 请求的参数
        sysLog.setParams(Arrays.toString(joinPoint.getArgs()));
        // 获取request
        HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
        // 设置IP地址
        sysLog.setIp(Utils.getIpAddr(request));
        // 用户名信息获取
        try{
        	sysLog.setUserId(BaseContext.getUserIdlong());
            sysLog.setUsername(BaseContext.getUserName());
        }catch (Exception e) {
        	log.error("操作日志记录：获取全局用户信息失败");
		}
        sysLog.setTime((int) time);
        // 系统当前时间Z
        Date date = new Date();
        sysLog.setGmtCreate(date);
//        sysLog.setId(123L);
        log.info("操作日志-->"+sysLog);
        // 保存系统日志
        logQueue.add(sysLog);
    }
	
}
