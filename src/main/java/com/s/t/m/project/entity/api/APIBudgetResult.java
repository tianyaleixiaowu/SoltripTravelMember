package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询预算口返回类
 */
@Data
public class APIBudgetResult extends BusResult{
    public APIBudgetResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

    public APIBudgetResult(){
    }
    /**
     * 当前预算值
     */
    private BigDecimal budgetAmout;
}
