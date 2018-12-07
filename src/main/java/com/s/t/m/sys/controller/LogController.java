package com.s.t.m.sys.controller;

import com.github.pagehelper.PageInfo;
import com.s.t.m.common.aspects.annotation.Log;
import com.s.t.m.common.pojo.IdEntity;
import com.s.t.m.common.result.JsonResult;
import com.s.t.m.common.result.R;
import com.s.t.m.sys.entity.LogEntity;
import com.s.t.m.sys.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = { "日志操作接口" })
@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogController {

	@Autowired
	private LogService logService;

	/**
	 * 分页查询
	 * 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	@ApiOperation(value = "动态参数分页查询")
	public JsonResult<PageInfo<LogEntity>> list(@RequestBody LogEntity entity) throws Exception {
		return JsonResult.success(logService.queryLogList(entity));
	}

	/**
	 * 单条删除
	 * 
	 * @param ide
	 * @return
	 */
	@Log("删除操作")
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	@ApiOperation(value = "单条删除")
	public JsonResult<?> remove(@RequestBody IdEntity ide) throws Exception {
		if (logService.remove(ide.getId()) > 0) {
			return JsonResult.success();
		}
		return JsonResult.error(R.FAILURE);
	}

	/**
	 * 批量删除
	 * 
	 * @param ides
	 * @return
	 */
	@Log("批量删除操作")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除")
	public JsonResult<?> batchRemove(@RequestBody IdEntity ides) throws Exception {
		int r = logService.batchRemove(ides.getIds());
		if (r > 0) {
			return JsonResult.success();
		}
		return JsonResult.error(R.FAILURE);
	}

}
