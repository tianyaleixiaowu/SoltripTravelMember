package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

/**
 * 认证接口返回类
 */
@Data
public class APIAuthResult extends BusResult{
    /**
     * 认证结果（0：认证通过 1：用户不存在 2：密码错误）
     */
    private String authResult;

    /**
     * 用户ID（会员卡号）
     */
    private String userID;

    /**
     * 姓名
     */
    private String cnName;

    /**
     * 职级（中文）
     */
    private String jobClass;

    /**
     * 员工工号（HR编号）
     */
    private String employeeID;

    /**
     * 单位编码（所属单位）
     */
    private String corpCode;

    /**
     * 用户信息版本（YYYYMMDDhhmmss）
     */
    private String userInfoVersion;

    public APIAuthResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

    public APIAuthResult(){
    }

}
