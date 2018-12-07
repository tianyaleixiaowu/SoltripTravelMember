package com.s.t.m.sys.service;

import java.util.List;

import com.s.t.m.sys.dao.LogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.s.t.m.sys.entity.LogEntity;

@Service
public class LogService {
	
	@Autowired
	private LogMapper logMapper;
	
	/**
	 * 新增
	 * @param log
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void addLog(LogEntity log){
		logMapper.saveLog(log);
	}
	/**
	 * 分页
	 * @param log
	 * @return info
	 */
	public PageInfo<LogEntity> queryLogList(LogEntity log){
		PageHelper.startPage(log.pageNum, log.pageSize, log.orderBy);
		List<LogEntity> list = logMapper.getListLogByPageInfo(log);
		PageInfo<LogEntity> info = new PageInfo<>(list);
		return info;
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public int remove(Long id) throws Exception{
		return logMapper.removeLog(id);
	}
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public int batchRemove(Long[] ids){
		return logMapper.batchRemoveLog(ids);
	}
	
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void addLogList(List<LogEntity> logList){
		logMapper.saveLogList(logList);
	}
} 
