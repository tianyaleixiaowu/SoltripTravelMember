package com.s.t.m.project.entity.api;

import com.s.t.m.project.core.BusResult;
import lombok.Data;

import java.util.List;

@Data
public class APIGetInfoResult extends BusResult {
    /**
     * 员工工号（HR编号）
     */
    private String employeeID;

    /**
     * 单位编码（所属单位ID）
     */
    private String corpCode;

    /**
     * 单位名称（所属单位名称）
     */
    private String corpName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 姓名
     */
    private String cnName;

    /**
     * 英文名（表中没有此字段，DAL返回数据为PaperNum，在BLL需要清空）
     */
    private String enFirstName;

    /**
     * 英文姓（表中没有此字段，DAL返回数据为MemberType 1个人，在BLL需要清空）
     */
    private String enLastName;

    /**
     * 性别（M：男，F：女）
     */
    private String gender;

    /**
     * 职级（中文）
     */
    private String jobClass;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 出生日期（YYYYMMDD）
     */
    private String birthday;

    /**
     * 国籍（国家二字码）
     */
    private String nation;

    /**
     * 证件信息
     */
    private CerficateInfo cerficateInfo;

    /**
     * 结算单位列表
     */
    private List<FeeUnit> feeUnitList;

    public APIGetInfoResult(){}

    public APIGetInfoResult(String resultCode,String description){
        super.resultCode = resultCode;
        super.description = description;
    }
}
