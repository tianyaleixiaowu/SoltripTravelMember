package ${basePackageService};

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import ${basePackageModel}.${modelNameUpperCamel}Domain;
import ${basePackageModel}.${modelNameUpperCamel};
import ${basePackageDao}.${modelNameUpperCamel}Mapper;

/**
* ${modelNameUpperCamel}Service接口类
* @author ${author}
* @date ${date}
*/
@Service
public class ${modelNameUpperCamel}Service{

	@Autowired
	private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;
	
	/*
	*新增
	*/
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void insert(${modelNameUpperCamel} ${modelNameLowerCamel}) throws Exception{
		${modelNameUpperCamel}Domain domain = new ${modelNameUpperCamel}Domain(${modelNameLowerCamel});
		domain.createDomain();
		sysLog = domain.getPO();
    	${modelNameLowerCamel}Mapper.insertSelective(${modelNameLowerCamel});
    }
	/*
	*删除
	*/
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void remove(Long id) throws Exception {
        int deleteByPrimaryKey = ${modelNameLowerCamel}Mapper.deleteByPrimaryKey(id);
    }
    /*
	 * 批量删除
	 */
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
	public void batchRemove(Long[] ids)throws Exception {
		for (Long l : ids) {
			remove(l);
		}
	}
	/*
	*修改
	*/
	@Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public void update(${modelNameUpperCamel} ${modelNameLowerCamel}) throws Exception {
        ${modelNameLowerCamel}Mapper.updateByPrimaryKeySelective(${modelNameLowerCamel});
    }
	/*
	*主键查询
	*/
    public ${modelNameUpperCamel} queryById(Long id) throws Exception {
        return ${modelNameLowerCamel}Mapper.selectByPrimaryKey(id);
    }
    /**
	* 分页查询
	* @Reutrn PageInfo<${modelNameUpperCamel}>
	*/
	public PageInfo<${modelNameUpperCamel}> queryAll(${modelNameUpperCamel} ${modelNameLowerCamel}, int pageNum, int pageSize, String orderBy) throws Exception{
 		PageHelper.startPage(pageNum, pageSize, orderBy);
 		List<${modelNameUpperCamel}> list = null;//${modelNameLowerCamel}Mapper.selectAll();
 		PageInfo<${modelNameUpperCamel}> info = new PageInfo<>(list);
 		return info;
	}
	
	
}