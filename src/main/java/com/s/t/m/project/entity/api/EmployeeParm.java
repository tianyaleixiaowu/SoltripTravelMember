package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

@Data
public class EmployeeParm extends SafetyParm{
    /**
     * 员工编号
     */
    private String employeeID;
    /**
     * 账套编号
     */
    private String accountBookCode;
}
