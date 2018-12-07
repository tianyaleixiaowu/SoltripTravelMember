package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

/**
 * 获取员工信息参数类
 */
@Data
public class APIGetInfoParam extends SafetyParm{

    /**
     * 用户ID
     */
    private String userID;
}
