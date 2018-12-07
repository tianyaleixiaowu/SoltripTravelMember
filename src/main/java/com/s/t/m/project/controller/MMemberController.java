package com.s.t.m.project.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.s.t.m.common.pojo.IdEntity;
import com.s.t.m.common.result.JsonResult;
import com.s.t.m.common.utils.*;
import com.s.t.m.project.core.BusResult;
import com.s.t.m.project.core.EnumStatusCode;
import com.s.t.m.project.core.SafetyParm;
import com.s.t.m.project.entity.MMember;
import com.s.t.m.project.entity.api.*;
import com.s.t.m.project.service.MMemberService;
import com.s.t.m.project.util.AESUtils;
import com.s.t.m.project.util.ConfigHelper;
import com.s.t.m.project.util.EncryptUtil;
import com.s.t.m.project.util.Tools;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.RegEx;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
* @Description: MMemberController类
* @author Bai
* @date 2018/11/22 13:24
*/
@Api(tags = { "会员API接口" })
@RestController
@CrossOrigin
@RequestMapping("/api/v1")
@Slf4j
public class MMemberController {

    @Autowired
    private MMemberService mMemberService;

	@ApiOperation(value = "获取员工会员号")
	@RequestMapping(value = "/user/getUserEmployee", method = RequestMethod.POST)
    public EmployeeResult getUserEmployee(@RequestBody EmployeeParm parm, BindingResult errors){
		EmployeeResult result = new EmployeeResult();
		try {

			BusResult busResult = SignatureSecurity(parm);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new EmployeeResult(busResult.resultCode, busResult.description);
			}

			//将接收的数据传到逻辑层进行查询
			result = mMemberService.getUserEmployee(parm);
		} catch (Exception e) {
			log.error("MMemberController.getUserEmployee控制器方法发生错误", e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}
	/**
	 * 获取账套编号
	 * @param parm
	 * @return
	 */
	@ApiOperation(value = "获取账套编号")
	@RequestMapping(value = "/user/getAccoutBookInfo", method = RequestMethod.POST)
    public UnitAccountPairResult getUnitAccountCodeList(@RequestBody UnitAccountPairParm parm){
		//实例化结果类
		UnitAccountPairResult result = new UnitAccountPairResult();
		try {

			BusResult busResult = SignatureSecurity(parm);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new UnitAccountPairResult(busResult.resultCode, busResult.description);
			}

			//将接收的数据传到逻辑层进行查询
			result = mMemberService.getUnitAccountCodeList(parm);
		} catch (Exception e) {
			log.error("MMemberController.authAccount控制器方法发生错误", e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}
	/**
	 * 认证
	 *
	 * @return 成功则返回响应信息，失败则返回失败原因
	 */
	@ApiOperation(value = "认证")
	@RequestMapping(value = "/user/auth", method = RequestMethod.POST)
	public APIAuthResult authAccount(@RequestBody APIAuthParm parm) {
		//实例化结果类
		APIAuthResult result = new APIAuthResult();
		try {

			BusResult busResult = SignatureSecurity(parm);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APIAuthResult(busResult.resultCode, busResult.description);
			}

			//将接收的数据传到逻辑层进行查询
			result = mMemberService.authAccount(parm);
		} catch (Exception e) {
			log.error("MMemberController.authAccount控制器方法发生错误", e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}

	/**
	 * 验证手机号码
	 *
	 * @return 如成功则返回响应信息，如失败则返回失败原因
	 */
	@ApiOperation(value = "验证手机号码")
	@RequestMapping(value = "/user/validate", method = RequestMethod.POST)
	public APIValidateResult validateCellPhone(@RequestBody APIValidateParam param) {
		APIValidateResult result = new APIValidateResult();


		BusResult busResult = SignatureSecurity(param);//验证签名
		if (!busResult.resultCode.equals("0")) {
			return new APIValidateResult(busResult.resultCode, busResult.description);
		}

		result = mMemberService.validateCellPhone(param);

		return result;
	}

	/**
	 * 修改密码
	 * @return 返回密码修改成功与否
	 */
	@ApiOperation(value = "修改密码")
	@RequestMapping(value = "/user/modifyPassword", method = RequestMethod.POST)
	public APIModifyPasswordResult modifyPassword(@RequestBody APIModifyPasswordParam param) {
		APIModifyPasswordResult result = new APIModifyPasswordResult();
		try {
			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APIModifyPasswordResult(busResult.resultCode, busResult.description);
			}

			//将数据传入逻辑层进行修改密码操作
			result = mMemberService.modifyPassword(param);
		} catch (Exception e) {
			log.error("MMemberController.modifyPassword控制器方法发生错误",e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}

	/**
	 * 搜索员工
	 * @return 如果成功，返回UserList
	 */
	@ApiOperation(value = "搜索员工")
	@RequestMapping(value = "/user/search", method = RequestMethod.POST)
	public APISearchResult searchEmployee(@RequestBody APISearchParam param) {
		APISearchResult result = new APISearchResult();
		try {
			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APISearchResult(busResult.resultCode, busResult.description);
			}

			result = mMemberService.searchEmployee(param);
		} catch (Exception e) {
			log.error("MMemberController.searchEmployee方法发生错误", e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}

	/**
	 * 获取员工信息
	 * @return 如果成功，返回此UserID相关的信息
	 */
	@ApiOperation(value = "获取员工信息")
	@RequestMapping(value = "/user/getInfo", method = RequestMethod.POST)
	public APIGetInfoResult searchEmployeeInfo(@RequestBody APIGetInfoParam param){
		APIGetInfoResult result = new APIGetInfoResult();
		try
		{

			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0"))
			{
				return new APIGetInfoResult(busResult.resultCode, busResult.description);
			}

			//将参数传入到逻辑层进行查询
			result = mMemberService.getEmployeeInfo(param);
		}
		catch (Exception e)
		{
			log.error("MMemberController.searchEmployeeInfo方法发生错误",e);
			result.resultCode = EnumStatusCode.MethodIsError.getCode();
			result.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return result;
	}

	/**
	 * 获取用户差旅标准
	 * @return
	 */
	@ApiOperation(value = "获取用户差旅标准")
	@RequestMapping(value = "/tripstandard/get", method = RequestMethod.POST)
	public APITripsTandardResult tripsTandard(@RequestBody APITripsTandardPram param) {
		APITripsTandardResult resultCode = new APITripsTandardResult();
		try {

			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APITripsTandardResult(busResult.resultCode, busResult.description);
			}

			resultCode = mMemberService.getTripsTandard(param);
		} catch (Exception e) {
			log.error("异常方法=>MMemberController.tripsTandard",e);
			resultCode.resultCode = EnumStatusCode.MethodIsError.getCode();
			resultCode.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return resultCode;
	}

	/**
	 * 获取审批人
	 * @return
	 */
	@ApiOperation(value = "获取审批人")
	@RequestMapping(value = "/approver/list", method = RequestMethod.POST)
	public APIApproverResult searchApprover(@RequestBody APIApproverParm param){
		APIApproverResult resultCode = new APIApproverResult();
		try {

			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APIApproverResult(busResult.resultCode, busResult.description);
			}

			resultCode = mMemberService.getApproverList(param);
		} catch (Exception ex) {
			log.error("异常方法=>MMemberController.searchApprover",ex);
			resultCode.resultCode = EnumStatusCode.MethodIsError.getCode();
			resultCode.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return resultCode;
	}

	/**
	 *获取单位剩余预算
	 * @return
	 */
	@ApiOperation(value = "获取单位剩余预算")
	@RequestMapping(value = "/budget/get", method = RequestMethod.POST)
	public APIBudgetResult searchCompanyBudget(@RequestBody APIBudgetParm param){
		APIBudgetResult resultCode = new APIBudgetResult();
		try
		{

			BusResult busResult = SignatureSecurity(param);//验证签名
			if (!busResult.resultCode.equals("0")) {
				return new APIBudgetResult(busResult.resultCode, busResult.description);
			}

			resultCode = mMemberService.getCompanyBudget(param);
		}
		catch (Exception ex)
		{
			log.error( "异常方法=>MMemberController.searchCompanyBudget",ex);
			resultCode.resultCode = EnumStatusCode.MethodIsError.getCode();
			resultCode.description = EnumStatusCode.MethodIsError.getMsgVal();
		}
		return resultCode;
	}
	/**
	 * 验证签名
	 * @param safetyParm 认证接收实体
	 * @return 成功则返回响应信息，失败则返回失败原因
	 */
	public BusResult SignatureSecurity(SafetyParm parms){
		BusResult result = new BusResult();
		try{
			if (!ObjectUtils.isEmpty(parms.getAccount()) && !ObjectUtils.isEmpty(parms.getPassword()) && !ObjectUtils.isEmpty(parms.getTimestamp())) {
				if(!ObjectUtils.isEmpty(parms.getTimestamp())){
					SimpleDateFormat formatter  = new SimpleDateFormat("yyyyMMddHHmmss");
					Date date = formatter.parse(parms.getTimestamp());
					if(Tools.dateToTwenty(date)){//服务方仅允许20分钟内的时间误差
						if(!ObjectUtils.isEmpty(parms.getAccount()) && !ObjectUtils.isEmpty(parms.getPassword())){
							String htAesKey = ConfigHelper.getAppConfig("HT_AESKey");
							String htAccount = ConfigHelper.getAppConfig("HT_Account");
							String htPassword = ConfigHelper.getAppConfig("HT_Password");
							//解密
							String password = EncryptUtil.decryptBase64(parms.getPassword(),htAesKey);
							if(password == null)
								log.error("验证签名、解密失败：密码参数("+parms.getPassword()+")==>秘钥("+htAesKey+")");
							if(parms.getAccount().equals(htAccount) && password.equals(htPassword)){
								result.resultCode = "0";
							}else{//参数不完整
								result.resultCode = EnumStatusCode.AuthorizationLackParm.getCode();
								result.description = EnumStatusCode.AuthorizationLackParm.getMsgVal();
							}
						}else{//参数不完整
							result.resultCode = EnumStatusCode.AuthorizationLackParm.getCode();
							result.description = EnumStatusCode.AuthorizationLackParm.getMsgVal();
						}
					}else{//请求超时
						result.resultCode = EnumStatusCode.TimeOut.getCode();
						result.description = EnumStatusCode.TimeOut.getMsgVal();
					}
				}else{//参数不完整
					result.resultCode = EnumStatusCode.AuthorizationLackParm.getCode();
					result.description = EnumStatusCode.AuthorizationLackParm.getMsgVal();
				}
			}else{//参数不完整
				result.resultCode = EnumStatusCode.AuthorizationLackParm.getCode();
				result.description = EnumStatusCode.AuthorizationLackParm.getMsgVal();
			}

		}catch (ParseException e){
			log.error("日期转换异常");
			result.resultCode = EnumStatusCode.Exception.getCode();
			result.description = EnumStatusCode.Exception.getMsgVal();
		}catch (Exception e){
			log.error("身份验证异常",e);
			result.resultCode = EnumStatusCode.Exception.getCode();
			result.description = EnumStatusCode.Exception.getMsgVal();
		}
		return result;
	}


	public static void main(String[] args){
		String s= EncryptUtil.encryptBase64("123","vX6LicvxUgZoVPUB");
		System.out.println(s);
		String vX6LicvxUgZoVPUB = EncryptUtil.decryptBase64("+DlVOw+taiUGa1I4LXuLEg==", "vX6LicvxUgZoVPUB");
		System.out.println(vX6LicvxUgZoVPUB);
	}
}