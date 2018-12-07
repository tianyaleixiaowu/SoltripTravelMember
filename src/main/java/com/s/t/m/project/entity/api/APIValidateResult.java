package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

/**
 * 校验手机口返回类
 */
@Data
public class APIValidateResult extends BusResult {

    /**
     * 校验结果（0：校验通过 1：校验失败）
     */
    private String validateResult;

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
     * 单位编码（结算单位ID）
     */
    private String corpCode;

    /**
     * 用户信息版本（YYYYMMDDhhmmss）
     */
    private String userInfoVersion;

    public APIValidateResult(){}

    public APIValidateResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }
}
