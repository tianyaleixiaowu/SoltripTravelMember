package com.s.t.m.sys.dao;

import java.util.List;

import com.s.t.m.sys.entity.LogEntity;


/**
 * 日志
 * @author Bai
 *
 */
public interface LogMapper {
	//id查询
	public LogEntity getlogById(Long id);
	//动态获取列表
	public List<LogEntity> getListLogByPageInfo(LogEntity log);
	//新增一条
	public int saveLog(LogEntity log);
	//更新一条
	public int updateLog(LogEntity log);
	//删除一条
	public int removeLog(Long id);
	//删除多条
	public int batchRemoveLog(Long[] ids);
	//多条新增
	public void saveLogList(List<LogEntity> list);
}
