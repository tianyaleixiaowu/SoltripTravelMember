package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

/**
 * 修改密码参数类
 */
@Data
public class APIModifyPasswordParam extends SafetyParm {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 新密码
     */
    private String newPassword;
}
