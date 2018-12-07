package com.s.t.m.project.dao;

import cn.hutool.core.date.DateTime;
import com.s.t.m.project.entity.ApproveStatus;
import com.s.t.m.project.entity.MCompanytravelsetting;
import com.s.t.m.project.entity.MMember;
import com.s.t.m.project.entity.api.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MMemberMapper {
    int deleteByPrimaryKey(String cardnum);

    int insert(MMember record);

    int insertSelective(MMember record);

    MMember selectByPrimaryKey(String cardnum);

    int updateByPrimaryKeySelective(MMember record);

    int updateByPrimaryKey(MMember record);

    /**
     * 根据会员卡号获取会员信息
     * @param cellPhone 密文手机号
     * @return
     */
    MMember getMemberByCellPhone(@Param("cellPhone") String cellPhone);

    /**
     * 根据会员卡号获取会员信息
     * @param cardNum
     * @return
     */
    MMember getMemberByCardNum(@Param("cardNum") String cardNum);

    /**
     * 修改密码
     * @param cellPhone 手机号，新密码
     * @param newPassword 如果密码修改成功则返回修改条数，修改失败返回0或-1
     * @return
     */
    int ModifyPassword(@Param("cellPhone") String cellPhone,@Param("newPassword") String newPassword);

    /**
     * 搜索员工
     * @param param
     * @return
     */
    List<User> searchEmployee(APISearchParam param);

    /**
     * 根据会员卡号获取会员完整信息
     * @param userID 单位ID
     * @return
     */
    APIGetInfoResult getMemberFullInfoByCardNum(@Param("cardNum") String userID);

    /**
     * 获取会员结算单位
     * @param userID 会员卡号
     * @return
     */
    List<FeeUnit> getBalanceCompanyByCardNum(@Param("cardNum") String userID);

    /**
     * 获取用户差旅等级
     * @param userID 会员卡号
     * @param feeUnitID 结算单位ID
     * @return
     */
    MCompanytravelsetting getMemberTravelLevel(@Param("cardNum") String userID,@Param("companyID") String feeUnitID);

    /**
     * 获取用户住宿费标准
     * @param travellevelid 差旅等级ID
     * @param cityID 城市列表
     * @return
     */
    List<HotelFee> getMemberHotelFee(@Param("travelLevelID") Integer travellevelid,@Param("cityList") List<String> cityID);

    /**
     * 验证会员结算单位是否存在
     * @param feeUnitID 单位ID
     * @param userID 会员卡号
     * @return
     */
    int isExistMemberBalanceCompany(@Param("companyID") String feeUnitID,@Param("cardNum") String userID);

    /**
     * 是否部门审批
     * @param userID 会员卡号
     * @param feeUnitID 结算单位
     * @return
     */
    ApproveStatus isDeptApprove(@Param("cardNum") String userID,@Param("bCompanyID") String feeUnitID);

    /**
     * 获取单位审批人
     * @param departmentID 部门ID
     * @return
     */
    List<User> getDepartmenApprover(@Param("deptID") String departmentID);

    /**
     * 获取单位审批人
     * @param companyID 单位ID
     * @return
     */
    List<User> getCompanyApprover(@Param("companyID") String companyID);

    /**
     * 获取单位剩余预算
     * @param now 当前日期
     * @param feeUnitID 单位ID
     * @return
     */
    BigDecimal getSurplusBudget(@Param("nowDT") DateTime now, @Param("companyID") String feeUnitID);

    /**
     * 根据组织结构ID获取单位ID
     * @param orgID
     * @return
     */
    Integer getCompanyID(@Param("orgID") String orgID);

    /**
     * 获取帐套编号
     * @param unitIDList 单位列表
     * @return
     */
    List<UnitAccountPair> getUnitAccountCodeList(@Param("companyIDList") List<String> unitIDList);

    String getCardNumByHRCode(EmployeeParm parm);
}