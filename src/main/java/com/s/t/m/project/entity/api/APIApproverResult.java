package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.util.List;

/**
 * 查询审批人口返回类
 */
@Data
public class APIApproverResult extends BusResult{

    public APIApproverResult(){}

    public APIApproverResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }
    /**
     * 审批类型（0：逐级审批 1：会签审批）
     */
    private String approveType;

    /**
     * 用户ID（会员卡号）
     */
    private List<User> approverList;
}
