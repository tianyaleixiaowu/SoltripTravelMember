package com.s.t.m.project.core;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * 阳光特有返回序列化
 */
@ApiModel(value="返回值对象")
public class BusResult implements Serializable {

    private String _code = "-1";

    /**
     * 操作代码  0 :成功
     */
    @ApiModelProperty(value="操作代码",name="resultCode")
    public String resultCode;


    /**
     * 描述
     */
    @ApiModelProperty(value="描述",name="description")
    public String description;


}
