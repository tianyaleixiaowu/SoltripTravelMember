package ${basePackageController};

import BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.templates.bind.annotation.CrossOrigin;
import org.springframework.templates.bind.annotation.RequestBody;
import org.springframework.templates.bind.annotation.RequestMapping;
import org.springframework.templates.bind.annotation.RequestMethod;
import org.springframework.templates.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import Log;
import IdEntity;
import JsonResult;
import ${basePackageModel}.${modelNameUpperCamel}VO;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageService}.${modelNameUpperCamel}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
* @Description: ${modelNameUpperCamel}Controller类
* @author ${author}
* @date ${date}
*/
@Api(tags = { "${modelNameUpperCamel}Controller接口" })
@RestController
@CrossOrigin
@RequestMapping("/${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

    @Autowired
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;
	/*
	*新增
	*/
	@Log("新增操作")
	@ApiOperation(value = "新增")
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JsonResult<?> insert(@RequestBody ${modelNameUpperCamel}VO ${modelNameLowerCamel}VO) throws Exception{
    	${modelNameLowerCamel}Service.insert(BeanMapper.map(${modelNameLowerCamel}VO,${modelNameUpperCamel}.class));
        return JsonResult.success();
    }
	/*
	*删除
	*/
	@Log("删除操作")
	@ApiOperation(value = "删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public JsonResult<?> remove(@RequestBody IdEntity ide) throws Exception {
        ${modelNameLowerCamel}Service.remove(ide.getId());
        return JsonResult.success();
    }
    /*
	 * 批量删除
	 */
	@Log("批量删除操作")
	@ApiOperation(value = "批量删除")
	@RequestMapping(value = "/batchRemove", method = RequestMethod.POST)
	public JsonResult<?> batchRemove(@RequestBody IdEntity ides)throws Exception {
		${modelNameLowerCamel}Service.batchRemove(ides.getIds());
		return JsonResult.success();
	}
    
	/*
	*修改
	*/
	@Log("修改操作")
	@ApiOperation(value = "修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public JsonResult<?> update(@RequestBody ${modelNameUpperCamel}VO ${modelNameLowerCamel}VO) throws Exception {
        ${modelNameLowerCamel}Service.update(BeanMapper.map(${modelNameLowerCamel}VO,${modelNameUpperCamel}.class));
        return JsonResult.success();
    }
	/*
	*主键查询
	*/
	@ApiOperation(value = "主键查询")
    @RequestMapping(value = "/queryById", method = RequestMethod.POST)
    public JsonResult<${modelNameUpperCamel}> queryById(@RequestBody IdEntity ide) throws Exception {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.queryById(ide.getId());
        return JsonResult.success(${modelNameLowerCamel});
    }

    
    /**
	* 分页查询
	* @Reutrn PageInfo<${modelNameUpperCamel}>
	*/
	@ApiOperation(value = "分页查询")
	@RequestMapping(value = "/queryAll", method = RequestMethod.POST)
	public JsonResult<PageInfo<${modelNameUpperCamel}>> queryAll(@RequestBody ${modelNameUpperCamel}VO ${modelNameLowerCamel}VO) throws Exception{
		return JsonResult.success(${modelNameLowerCamel}Service.queryAll(
			BeanMapper.map(${modelNameLowerCamel}VO,${modelNameUpperCamel}.class)
				,${modelNameLowerCamel}VO.pageNum,${modelNameLowerCamel}VO.pageSize,${modelNameLowerCamel}VO.orderBy));
	}
}