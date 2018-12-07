package com.s.t.m.project.core;

import lombok.Data;

/**
 * 安全验证参数
 */
@Data
public class SafetyParm {
    /**
     * 时间戳
     */
    private String timestamp;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
