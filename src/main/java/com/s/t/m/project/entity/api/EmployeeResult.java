package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

@Data
public class EmployeeResult extends BusResult {

    /**
     * 用户id
     */
    private String userID;

    public EmployeeResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

    public EmployeeResult(){
    }
}
