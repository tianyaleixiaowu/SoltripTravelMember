package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.util.List;

@Data
public class UnitAccountPairResult extends BusResult{

    private List<UnitAccountPair> unitAccountPairList;

    public UnitAccountPairResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

    public UnitAccountPairResult(){
    }
}
