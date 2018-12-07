package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

import java.util.List;

/**
 * 查询差标参数
 */
@Data
public class APITripsTandardPram extends SafetyParm{

    /**
     * 结算单位ID
     */
    private String feeUnitID;

    /**
     * 用户ID
     */
    private String userID;

    /**
     * 城市ID列表
     */
    private List<String> cityID;
}
