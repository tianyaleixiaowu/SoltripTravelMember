package com.s.t.m.project.entity;

import lombok.Data;

/**
 * 审批状态
 */
@Data
public class ApproveStatus {

    /**
     * 会员所属单位
     */
    private String CompanyID;

    /**
     * 部门ID
     */
    private String DepartmentID;

    /**
     * 是否部门审批
     */
    private Boolean IsDeptApprove;

    /**
     * 部门审批类型
     */
    private String DeptApproveType;


    /**
     * 单位审批类型
     */
    private String ComApproveType;
    /**
     * 是否免审批
     */
    private Boolean IsNoApprove;
}
