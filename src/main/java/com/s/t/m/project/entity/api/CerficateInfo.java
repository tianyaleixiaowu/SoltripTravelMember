package com.s.t.m.project.entity.api;

import lombok.Data;

/**
 * 证件信息口返回类
 */
@Data
public class CerficateInfo {

    /**
     * 身份证号
     */
    private String chinaID;

    /**
     * 护照号码
     */
    private String passportNo;

    /**
     * 护照签发国（国家二字码）
     */
    private String passportCountry;

    /**
     *  护照签发日期（YYYYMMDD）
     */
    private String passportStartDate;

    /**
     * 护照失效日期（YYYYMMDD）
     */
    private String passportEndDate;

    public CerficateInfo(){

    }
    public CerficateInfo(String chinaID){
        this.chinaID = chinaID;
    }
}
