package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;

public class APIModifyPasswordResult extends BusResult {
    public APIModifyPasswordResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

    public APIModifyPasswordResult(){
    }
}
