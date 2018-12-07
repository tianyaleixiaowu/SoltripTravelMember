package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

/**
 * 获取单位预算
 */
@Data
public class APIBudgetParm extends SafetyParm{
    /**
     * 结算单位ID
     */
    private String feeUnitID;
}
