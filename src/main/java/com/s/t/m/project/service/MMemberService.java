package com.s.t.m.project.service;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.s.t.m.common.utils.ObjectUtils;
import com.s.t.m.project.entity.ApproveStatus;
import com.s.t.m.project.entity.MCompanytravelsetting;
import com.s.t.m.project.entity.api.*;
import com.s.t.m.project.util.AESUtils;
import com.s.t.m.common.utils.DateUtils;
import com.s.t.m.common.utils.SecurityKit;
import com.s.t.m.project.core.EnumStatusCode;
import com.s.t.m.project.dao.MMemberMapper;
import com.s.t.m.project.entity.MMember;
import com.s.t.m.project.util.ConfigHelper;
import com.s.t.m.project.util.EncryptUtil;
import com.s.t.m.project.util.Tools;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * MMemberService接口类
 *
 * @author Bai
 * @date 2018/11/22 13:24
 */
@Service
@Slf4j
public class MMemberService {

    @Autowired
    private MMemberMapper mMemberMapper;
    @Autowired
    private MDictionaryService mDictionaryService;


    /**
     * 认证
     *
     * @param parm 手机号，密码
     * @return 如果成功，则返回响应信息；如失败，则返回失败原因
     */
    public APIAuthResult authAccount(APIAuthParm parm) {
        String aesKey = ConfigHelper.getAppConfig("SoltripKey");

        //实例结果类
        APIAuthResult result = new APIAuthResult();

        try {
            MMember memberModel = new MMember();

            //验证手机参数是否符合手机格式
            if (Tools.IsMobile(parm.getMobile())) {
                //加密,查询
                memberModel = mMemberMapper.getMemberByCellPhone(EncryptUtil.encryptBase64(parm.getMobile(), aesKey));
            } else if (Tools.CheckNumber(parm.getMobile()) && parm.getMobile().length() == 8) {
                memberModel = mMemberMapper.getMemberByCardNum(EncryptUtil.encryptBase64(parm.getMobile(), aesKey));
            }else{
                result.resultCode = EnumStatusCode.LOGINERROR.getCode();
                result.description = EnumStatusCode.LOGINERROR.getMsgVal();
                return result;
            }

            if (memberModel != null)//存在会员
            {
                if(ObjectUtils.isEmpty(memberModel.getUserpwd())){//用户密码是否设置
                    result.resultCode = EnumStatusCode.PASSWORDISNOT.getCode();
                    result.description = EnumStatusCode.PASSWORDISNOT.getMsgVal();
                    return result;
                }
                //密码参数AES解密、MD5加密
                String passwordMD5 = passwordDeAESEnMD5(parm.getUserPassword());
                if (passwordMD5 == null) {
                    log.error("认证、密码接收明文,处理失败！！！！");
                    result.resultCode = EnumStatusCode.PasswordIsError.getCode();
                    result.description = EnumStatusCode.PasswordIsError.getMsgVal();
                    result.setAuthResult("2");
                    return result;
                }

                if (memberModel.getUserpwd().equals(passwordMD5))//密码正确
                {
                    result.resultCode = "0";
                    result.setAuthResult("0");
                    result.setUserID(memberModel.getCardnum());
                    result.setCnName(memberModel.getRealname());
                    result.setJobClass(memberModel.getJobtitle());
                    result.setEmployeeID(memberModel.getHrcode());
                    result.setCorpCode(memberModel.getCompanyid().toString());
                    result.setUserInfoVersion(DateUtils.formatDate(memberModel.getUpdatetime(), "yyyyMMddHHmmss"));
                } else {
                    result.resultCode = EnumStatusCode.PasswordIsError.getCode();
                    result.description = EnumStatusCode.PasswordIsError.getMsgVal();
                    result.setAuthResult("2");
                }
            } else {
                result.resultCode = EnumStatusCode.IsNotMember.getCode();
                result.description = EnumStatusCode.IsNotMember.getMsgVal();
                result.setAuthResult("1");
            }

        } catch (Exception e) {
            log.error("异常方法 => MMemberService.authAccount", e);
            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }

        return result;
    }

    /**
     * 验证手机号码
     *
     * @param param 手机号码
     * @return 如果验证通过，则ResultCode=0，并返回响应信息；如果失败，返回失败原因
     */
    public APIValidateResult validateCellPhone(APIValidateParam param) {
        APIValidateResult result = new APIValidateResult();
        String aesKey = ConfigHelper.getAppConfig("SoltripKey");
        try {
            MMember memberModel = new MMember();

            //验证手机参数是否符合手机格式
            if (Tools.IsMobile(param.getMobile())) {

                memberModel = mMemberMapper.getMemberByCellPhone(EncryptUtil.encryptBase64(param.getMobile(), aesKey));

                if (memberModel != null)//存在会员
                {
                    result.resultCode = "0";
                    result.setValidateResult("0");
                    result.setUserID(memberModel.getCardnum());
                    result.setCnName(memberModel.getRealname());
                    result.setJobClass(memberModel.getJobtitle());
                    result.setEmployeeID(memberModel.getHrcode());
                    result.setCorpCode(memberModel.getCompanyid().toString());
                    result.setUserInfoVersion(DateUtils.formatDate(memberModel.getUpdatetime(), "yyyyMMddHHmmss"));
                } else {
                    result.resultCode = EnumStatusCode.IsNotMember.getCode();
                    result.description = EnumStatusCode.IsNotMember.getMsgVal();
                    result.setValidateResult("1");
                }
            } else {
                result.resultCode = EnumStatusCode.FormatError.getCode();
                result.description = EnumStatusCode.FormatError.getMsgVal();
                result.setValidateResult("1");
            }
        } catch (Exception e) {
            log.error("异常方法 => MMemberService.validateCellPhone", e);
            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }

        return result;

    }

    /**
     * 修改密码
     *
     * @param param 手机号，新密码
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public APIModifyPasswordResult modifyPassword(APIModifyPasswordParam param) {
        APIModifyPasswordResult result = new APIModifyPasswordResult();
        String aesKey = ConfigHelper.getAppConfig("SoltripKey");
        MMember memberModel = new MMember();

        if (Tools.IsMobile(param.getMobile()) && !ObjectUtils.isEmpty(param.getNewPassword()))//验证手机参数是否符合手机格式
        {
            String passwordMD5 = passwordDeAESEnMD5(param.getNewPassword());
            if (passwordMD5 == null) {
                log.error("修改密码、密码接收明文,处理失败！！！");
                result.resultCode = EnumStatusCode.ModifyPasswordDefeated.getCode();
                result.description = EnumStatusCode.ModifyPasswordDefeated.getMsgVal();
                return result;
            }
            int i = mMemberMapper.ModifyPassword(EncryptUtil.encryptBase64(param.getMobile(), aesKey), passwordMD5);
            if (i > 0) {
                result.resultCode = "0";
            } else {
                result.resultCode = EnumStatusCode.ModifyPasswordDefeated.getCode();
                result.description = EnumStatusCode.ModifyPasswordDefeated.getMsgVal();
            }
        } else {
            result.resultCode = EnumStatusCode.ParmNullOrError.getCode();
            result.description = EnumStatusCode.ParmNullOrError.getMsgVal();
        }


        return result;
    }

    /**
     * 搜索员工
     *
     * @param param FeeUnitID：结算单位ID，Name：姓名，EmployeeID：员工工号，Mobile：手机号码，MaxNum：最多返回记录
     * @return 如果成功，返回UserList
     */
    public APISearchResult searchEmployee(APISearchParam param) {
        APISearchResult result = new APISearchResult();
        String aesKey = ConfigHelper.getAppConfig("SoltripKey");
        try {
            Integer companyID = mMemberMapper.getCompanyID(param.getFeeUnitID());
            if(null == companyID){
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
                log.info("根据组织结构ID获取单位ID没有数据，下面逻辑禁止处理");
                return result;
            }
            param.setFeeUnitID(String.valueOf(companyID));
            //手机号加密
            param.setMobile(EncryptUtil.encryptBase64(param.getMobile(), aesKey));
            param.setMaxNum(param.getMaxNum() > 20 ? 20 : param.getMaxNum());//限制最大返回记录数

            //将查询参数传到数据层进行查询
            if (!ObjectUtils.isEmpty(param.getName()))
                param.setName(param.getName() + "%");
            if (!ObjectUtils.isEmpty(param.getMobile()))
                param.setMobile(param.getMobile() + "%");
            if (!ObjectUtils.isEmpty(param.getEmployeeID()))
                param.setEmployeeID(param.getEmployeeID() + "%");
            List<User> userList = mMemberMapper.searchEmployee(param);

            //判断是否查询到数据
            if (userList != null && userList.size() > 0) {
                for (int i = 0; i < userList.size(); i++)//No参数按照列表长度进行赋值,对手机号进行解密
                {
                    User user = userList.get(i);
                    user.setNo(i);
                    user.setGender(user.getGender().equals("1") ? "M" : "F");
                    user.setMobile(EncryptUtil.decryptBase64(user.getMobile(), aesKey));
                }
                result.setUserLis(userList);
                result.resultCode = "0";
            } else {
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
            }
        } catch (Exception e) {
            log.error("异常方法 => MMemberService.searchEmployee", e);
            result.resultCode = EnumStatusCode.MethodIsError.getCode();
            result.description = EnumStatusCode.MethodIsError.getMsgVal();
        }
        return result;
    }

    /**
     * 获取员工信息
     *
     * @param param 会员卡号
     * @return 返回该会员的相关信息
     */
    public APIGetInfoResult getEmployeeInfo(APIGetInfoParam param) {
        APIGetInfoResult result = new APIGetInfoResult();

        try {
            if (ObjectUtils.isEmpty(param.getUserID()))//判断UserID是否为空
            {
                result.resultCode = EnumStatusCode.ParmNullOrError.getCode();
                result.description = EnumStatusCode.ParmNullOrError.getMsgVal();

                return result;
            }
            //获取会员信息
            APIGetInfoResult member = mMemberMapper.getMemberFullInfoByCardNum(param.getUserID());
            //判断是否有此用户信息
            if (member != null) {
                String aesKey = ConfigHelper.getAppConfig("SoltripKey");

                member.setJobClass(mDictionaryService.getDictionaryDValue(49, member.getJobClass()));
                member.setMobile(EncryptUtil.decryptBase64(member.getMobile(), aesKey));//手机号解密

                String idCard = EncryptUtil.decryptBase64(member.getEnFirstName(), aesKey);
                if (idCard != null) {
                    member.setCerficateInfo(new CerficateInfo(idCard));
                    member.setBirthday(member.getCerficateInfo().getChinaID() == null ? "" : Tools.getBirthByIdCard(member.getCerficateInfo().getChinaID()));
                } else {
                    log.error("获取员工信息、证件号解密失败、证件号==>" + member.getEnFirstName());
                }
                member.setEnFirstName("");

                if (member.getEnLastName().equals("2"))//企业会员
                {
                    member.setEnLastName("");
                    member.setFeeUnitList(
                            mMemberMapper.getBalanceCompanyByCardNum(param.getUserID())
                    );//获取结算单位
                    List<String> notifyTypeList = Arrays.asList(
                            ConfigHelper.getAppConfig("NotifyTypeList")
                                    .split("\\|"));
                    for (FeeUnit f : member.getFeeUnitList()) {
                        f.setNotifyTypeList(notifyTypeList);
                    }
                }
                member.resultCode = "0";
                return member;
            } else {
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
            }
        } catch (Exception e) {
            log.error("异常方法 => MMemberService.getEmployeeInfo", e);
            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }
        return result;
    }

    /**
     * 获取用户差旅标准
     *
     * @param param
     * @return
     */
    public APITripsTandardResult getTripsTandard(APITripsTandardPram param) {
        APITripsTandardResult result = new APITripsTandardResult();

        try {
            Integer companyID = mMemberMapper.getCompanyID(param.getFeeUnitID());
            if(null == companyID){
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
                log.info("根据组织结构ID获取单位ID没有数据，下面逻辑禁止处理");
                return result;
            }
            param.setFeeUnitID(String.valueOf(companyID));
            //1、获取用户机票/火车票差旅标准
            MCompanytravelsetting travelLevel = mMemberMapper.getMemberTravelLevel(param.getUserID(), param.getFeeUnitID());

            if (travelLevel != null) {
                //2、获取住宿标准
                List<HotelFee> hotelLevel = mMemberMapper.getMemberHotelFee(travelLevel.getTravellevelid(), param.getCityID());

                if (hotelLevel != null)//如果不存在当前城市
                {
                    result.resultCode = "0";
                    result.setFlightLevel(travelLevel.getPlanelevel());
                    result.setTrainLevel(travelLevel.getTrainlevel());
                    result.setHotelFeeList(hotelLevel);
                } else {
                    result.description = "未设置所选城市的住宿标准";
                }
            } else {
                result.description = "未设置用户差旅标准";
            }
        } catch (Exception ex) {
            log.error("异常方法 => MMemberService.getTripsTandard", ex);
            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }
        return result;
    }

    /**
     * 获取单位/部门审批人
     *
     * @param param 请求参数
     * @return
     */
    public APIApproverResult getApproverList(APIApproverParm param) {
        APIApproverResult result = new APIApproverResult();

        try {
            Integer companyID = mMemberMapper.getCompanyID(param.getFeeUnitID());
            if(null == companyID){
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
                log.info("根据组织结构ID获取单位ID没有数据，下面逻辑禁止处理");
                return result;
            }
            param.setFeeUnitID(String.valueOf(companyID));
            //验证用户是否存在该结算的单位
            int i = mMemberMapper.isExistMemberBalanceCompany(param.getFeeUnitID(), param.getUserID());
            if (i > 0) {
                //1、获取用户是否需要部门审批
                ApproveStatus deptApp = mMemberMapper.isDeptApprove(param.getUserID(), param.getFeeUnitID());

                if (deptApp.getIsNoApprove()) {//免审批
                    result.resultCode = "0";
                    result.description = "此用户无需差旅审批";
                    result.setApproveType("3");
                    return result;
                }

                //2、如果需要部门审批，判断当前结束单位是否与用户所属单位相同
                if (deptApp.getIsDeptApprove() && deptApp.getCompanyID().equals(param.getFeeUnitID())) {
                    //3、获取部门审批人
                    List<User> deptApprover = mMemberMapper.getDepartmenApprover(deptApp.getDepartmentID());

                    if (deptApprover != null && deptApprover.size() > 0)//部门有审批人
                    {
                        result.resultCode = "0";
                        result.setApproveType(deptApp.getDeptApproveType());
                        result.setApproverList(deptApprover);
                    } else {
                        result.description = "部门未添加审批人";
                    }
                } else//不需要审批或单位不相同
                {
                    //3、获取单位审批人
                    List<User> unitApprover = mMemberMapper.getCompanyApprover(deptApp.getCompanyID());

                    if (unitApprover != null && unitApprover.size() > 0)//单位有审批人
                    {
                        result.resultCode = "0";
                        result.setApproveType(deptApp.getComApproveType());
                        result.setApproverList(unitApprover);
                    } else {
                        result.description = "部门未添加审批人";
                    }
                }

                if (result.resultCode.equals("0")) {
                    String soltripKey = ConfigHelper.getAppConfig("SoltripKey");
                    for (User u : result.getApproverList()) {
                        u.setJobClass(mDictionaryService.getDictionaryDValue(49, u.getJobClass()));//职务
                        u.setMobile(EncryptUtil.decryptBase64(u.getMobile(), soltripKey));
                    }
                }
            } else {
                result.resultCode = EnumStatusCode.NoMemberBalanceCompany.getCode();
                result.description = EnumStatusCode.NoMemberBalanceCompany.getMsgVal();
            }
        } catch (Exception ex) {
            log.error("异常方法 => MMemberService.getApproverList", ex);

            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }

        return result;

    }

    /**
     * 获取单位可用预算
     *
     * @param param 请求参数
     * @return
     */
    public APIBudgetResult getCompanyBudget(APIBudgetParm param) {
        APIBudgetResult result = new APIBudgetResult();

        try {
            Integer companyID = mMemberMapper.getCompanyID(param.getFeeUnitID());
            if(null == companyID){
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
                log.info("根据组织结构ID获取单位ID没有数据，下面逻辑禁止处理");
                return result;
            }
            param.setFeeUnitID(String.valueOf(companyID));
            BigDecimal surplusCost = mMemberMapper.getSurplusBudget(DateTime.now(), param.getFeeUnitID());
            result.resultCode = "0";
            result.setBudgetAmout(surplusCost);
        } catch (Exception ex) {
            log.error("异常方法 => MMemberService.getCompanyBudget", ex);

            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }

        return result;
    }

    /**
     * 密码解密后MD5加密
     *
     * @param password 密码
     * @return
     */
    public String passwordDeAESEnMD5(String password) {
        String aesKey = ConfigHelper.getAppConfig("HT_AESKey");
        //密码AES解密
        String decryptPassword = EncryptUtil.decryptBase64(password, aesKey);

        //密码MD5加密
        String md5Password = SecurityKit.MD5.convert32(decryptPassword).toUpperCase();
System.err.println(md5Password);
        //返回MD5加密后的密码
        return md5Password;
    }

    /**
     * 获取账套编号
     *
     * @param parm
     * @return
     */
    public UnitAccountPairResult getUnitAccountCodeList(UnitAccountPairParm parm) {
        UnitAccountPairResult result = new UnitAccountPairResult();
        try {
            List<UnitAccountPair> list = mMemberMapper.getUnitAccountCodeList(parm.getUnitIDList());
            if(list != null && list.size() > 0) {
                result.setUnitAccountPairList(list);
                result.resultCode = "0";
            }else{
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
            }
        } catch (Exception ex) {
            log.error("异常方法 => MMemberService.getUnitAccountCodeList", ex);

            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }
        return result;
    }

    /**
     *获取员工会员号
     * @param parm
     * @return
     */
    public EmployeeResult getUserEmployee(EmployeeParm parm) {
        EmployeeResult result = new EmployeeResult();
        try{
            String userID = mMemberMapper.getCardNumByHRCode(parm);
            if(ObjectUtils.isEmpty(userID)){
                result.resultCode = EnumStatusCode.InfoIsNone.getCode();
                result.description = EnumStatusCode.InfoIsNone.getMsgVal();
                return result;
            }
            result.setUserID(userID);
            result.resultCode = "0";
        }catch (Exception ex) {
            log.error("异常方法 => MMemberService.getUserEmployee", ex);
            result.resultCode = EnumStatusCode.Exception.getCode();
            result.description = EnumStatusCode.Exception.getMsgVal();
        }
        return result;
    }
}