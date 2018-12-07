package ${basePackageModel};
import BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


/**
* ${modelNameUpperCamel}VO 实体类
* @author ${author}
* @date ${date}
*/
@ApiModel(value="${modelNameUpperCamel}VO 实体")
public class ${modelNameUpperCamel}VO extends BaseEntity{

<#if model_column?exists>
    <#list model_column as model>
        /**
        *${model.columnComment!}
        */
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT') >
        @ApiModelProperty(value="${model.columnComment!}",name="${model.changeColumnName?uncap_first}")
        private String ${model.changeColumnName?uncap_first};

        </#if>
        <#if (model.columnType = 'DATETIME' || model.columnType = 'TIMESTAMP') >
        @ApiModelProperty(value="${model.columnComment!}",name="${model.changeColumnName?uncap_first}")
        private Date ${model.changeColumnName?uncap_first};

        </#if>
        <#if model.columnType = 'BIGINT' >
        @ApiModelProperty(value="${model.columnComment!}",name="${model.changeColumnName?uncap_first}")
        private Long ${model.changeColumnName?uncap_first};

        </#if>
        <#if model.columnType = 'INT' >
        @ApiModelProperty(value="${model.columnComment!}",name="${model.changeColumnName?uncap_first}")
        private Integer ${model.changeColumnName?uncap_first};

        </#if>
    </#list>
</#if>

<#if model_column?exists>
    <#list model_column as model>
        <#if (model.columnType = 'VARCHAR' || model.columnType = 'TEXT')>
        public String get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(String ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
        <#if (model.columnType = 'DATETIME' || model.columnType = 'TIMESTAMP') >
        public Date get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(Date ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
        <#if model.columnType = 'BIGINT' >
        public Long get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(Long ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
        <#if model.columnType = 'INT' >
        public Integer get${model.changeColumnName}() {
        return this.${model.changeColumnName?uncap_first};
        }

        public void set${model.changeColumnName}(Integer ${model.changeColumnName?uncap_first}) {
        this.${model.changeColumnName?uncap_first} = ${model.changeColumnName?uncap_first};
        }

        </#if>
    </#list>
</#if>

}
