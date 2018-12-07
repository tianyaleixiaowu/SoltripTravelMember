package com.s.t.m.common.pojo;

import com.s.t.m.common.utils.IdWorker;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 初始化数据父类
 * @author Bai
 *
 * @param <E> 初始化的实体
 */
public abstract class BaseDomain<E> {
    protected E po;
    private static final IdWorker idWorker = IdWorker.getInstance();//id生成策略

    protected BaseDomain(E e) {
        this.po = e;
    }

    public E getPO() {
        return po;
    }

    protected BigDecimal generateNewIdBig() {
        try {
        	return (BigDecimal) idWorker.nextId(BigDecimal.class);
		} catch (Exception e) {
			return null;
		}
    }
    
    protected Long generateNewIdLong() {
        try {
        	return (Long) idWorker.nextId(Long.class);
		} catch (Exception e) {
			return null;
		}
    }
    
    protected Date now() {
        return new Date();
    }


}
