package com.s.t.m.project.core;

import com.s.t.m.common.result.ReturnType;

/**
 * 阳光特有枚举返回
 */
public enum EnumStatusCode implements BusType{


    MethodIsError("-1","方法发生错误"),
    Success("0","执行成功"),
    AuthorizationLackParm("1000","身份认证参数不完整"),
    Exception("9999","处理失败"),
    TimeOut("1001","请求超时"),
    PasswordIsError("1007","密码错误"),
    IsNotMember("1004","无此会员"),
    NoMemberBalanceCompany("1013","不存在用户结算单位"),
    InfoIsNone("1012","没有查询到数据"),
    SearchSuccess("1011","查询成功"),
    ModifyPasswordSuccess("1010","密码修改成功"),
    ModifyPasswordDefeated("1009","修改密码失败"),
    AuthorizationSuccess("1008","认证成功"),
    ParmNullOrError("1006","请求参数为空或格式错误"),
    FormatError("1005","格式错误"),
    CellPhoneIsNull("1003","账号或密码参数为空"),
    AuthorizationIsNull("1002","身份认证参数值为空"),
    LOGINERROR("1013","登录信息有误"),
    PASSWORDISNOT("1014"," 您暂未设置登录密码");

    private String code;
    private String msgVal;

    EnumStatusCode(String code,String msgVal) {
        this.code = code;
        this.msgVal = msgVal;
    }

    @Override
    public String getCode() {
        return code;
    }
    @Override
    public String getMsgVal() {
        return msgVal;
    }

}
