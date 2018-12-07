package com.s.t.m.project.entity.api;

import lombok.Data;

/**
 * User用户信息类
 */
@Data
public class User {
    /**
     * 序号（逐级审批时按序号升序排列）
     */
    private int no;

    /**
     * 用户ID（会员卡号）
     */
    private String userID;

    /**
     * 员工工号（HR编号）
     */
    private String employeeID;

    /**
     * 单位编码（所属单位ID）
     */
    private String corpCode;

    /**
     * 姓名
     */
    private String cnName;

    /**
     * 性别（M：男，F：女）
     */
    private String gender;

    /**
     * 职级（中文）
     */
    private String jobClass;

    /**
     * 单位名称（所属单位名称）
     */
    private String corpName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

}
