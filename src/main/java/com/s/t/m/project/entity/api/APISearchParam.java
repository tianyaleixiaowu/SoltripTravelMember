package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

@Data
public class APISearchParam extends SafetyParm {
    /**
     * 结算单位ID
     */
    private String feeUnitID;

    /**
     * 姓名
     */
    private String name;

    /**
     * 员工工号
     */
    private String employeeID;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 最多返回记录条数
     */
    private int maxNum;
}
