package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

/**
 * 获取审批人请求参数
 */
@Data
public class APIApproverParm extends SafetyParm{

    /**
     * 结算单位ID
     */
    private String feeUnitID;

    /**
     * 用户ID
     */
    private String userID;
}
