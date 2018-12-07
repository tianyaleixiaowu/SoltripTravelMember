package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

/**
 * 验证手机参数类
 */
@Data
public class APIValidateParam extends SafetyParm {
    /**
     * 手机号
     */
    private String mobile;
}
