package ${basePackageModel};

import BaseDomain;
import BaseContext;
import ${basePackageModel}.${modelNameUpperCamel};

import java.util.Date;
/**
* ${modelNameUpperCamel}Domain 初始化实体类
* @author ${author}
* @date ${date}
*/
public class ${modelNameUpperCamel}Domain extends BaseDomain<${modelNameUpperCamel}> {

    public ${modelNameUpperCamel}Domain(${modelNameUpperCamel} e) {
    super(e);
    }

    /*
    新增初始化数据
    */
    public void createDomain() {
        po.setId(generateNewIdLong());
    }

    /*
    修改初始化数据
    */
    public void updateDomain() {
    }

    /*
    删除初始化数据
    */
    public void deleteDomain() {
    }

    /*
    初始化数据
    */
    public void initDomain() {
    }

    public static void main(String[] args) {
        ${modelNameUpperCamel} po = new ${modelNameUpperCamel}Domain(new ${modelNameUpperCamel}()).getPO();
    }
    }
