package com.s.t.m.project.entity.api;

import lombok.Data;

import java.util.List;

/**
 * 结算单位
 */
@Data
public class FeeUnit {

    /**
     * 结算单位ID
     */
    private String feeUnitID;

    /**
     * 结算单位名称
     */
    private String feeUnitName;


    /**
     * 审批通知方式列表
     */
    private List<String> notifyTypeList;
}
