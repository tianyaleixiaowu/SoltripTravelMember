package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.util.List;

/**
 * 搜索员工口返回类
 */
@Data
public class APISearchResult extends BusResult{

    /**
     * 用户列表
     */
    private List<User> userLis;

    public APISearchResult(){}

    public APISearchResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }

}
