package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.SafetyParm;
import lombok.Data;

@Data
public class APIAuthParm extends SafetyParm {

    /**
     * 电话号码（会员卡号、邮箱）
     */
    private String mobile;

    /**
     * 密码
     */
    private String userPassword;
}
