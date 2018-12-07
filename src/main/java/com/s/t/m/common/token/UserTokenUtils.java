package com.s.t.m.common.token;

import java.util.Date;
import java.util.Map;

import com.s.t.m.common.utils.DateUtils;
import com.s.t.m.common.utils.SecurityKit;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 登录加密解密
 * @author Bai
 *
 */
public class UserTokenUtils {
	/**
	 * token过期时间2小时
	 */
	private static final long TOKEN_DATE = 119;
	
	/**
     * 生成RSA密钥对
     * @param key 用户唯一标识
     * @return publicKey -- 公钥, privateKey -- 私钥
     */
	public static Map<String, String> keyPair(String key){
		return SecurityKit.RSA.genKeyPair(key);
	}
	/**
	 * 密码加密
	 * @param pwd 加密内容
	 * @param key 加密密码
	 * @return
	 */
	public static String pwdEncrypt(String pwd,String key) {
		return SecurityKit.AES.encrypt(pwd, key);
	}
	/**
	 * 密码解密
	 * @param encryptPassword 解密内容
	 * @param key 解密密码
	 * @return
	 */
	public static String pwdDecrypt(String encryptPassword,String key) {
		return SecurityKit.AES.decrypt(encryptPassword, key);
	}
	/**
	 * 公钥加密
	 * @param data 加密数据,用户的信息加密成token
	 * @return
	 */
	public static String tokenEncrypt(String pubKey,JSONObject data){
        String encryptData = SecurityKit.RSA.encrypt(data.toJSONString(), pubKey, SecurityKit.RSA.PUBLIC_KEY, "UTF-8");
        return encryptData;
        
	}
	/**
	 * 私钥解密
	 * @param encryptData 加密数据
	 * @param priKey 私钥
	 * @return
	 */
	public static String tokenDecrypt(String encryptData,String priKey){
		String decryptData = SecurityKit.RSA.decrypt( encryptData, priKey, SecurityKit.RSA.PRIVATE_KEY, "UTF-8" );
        return decryptData;
	}
	
	/**
	 * 获取用户登录成功token过期时间、（2小时）true 未过期，false过期
	 * @return
	 */
	public static boolean tokenDate(Date date){
		long pastHour = DateUtils.pastMinutes(date);//获取token生成时间
		if(pastHour == TOKEN_DATE || pastHour > TOKEN_DATE || pastHour < 0){//判断时间是否大于120分钟
			return false;//过期
		}
		return true;//未过期
	}
	
	
	public static void main(String[] args) {
		//增加俩小时后的时间
		Date addHours = DateUtils.addHours(new Date(), -2);//正数加小时负数减小时
		String format = DateFormatUtils.format(addHours, "yyyy-MM-dd HH:mm:ss");
		System.err.println(format);
		//现在时间和前两个小时的时间算分钟差
		long pastHour = DateUtils.pastMinutes(addHours);
		System.out.println(pastHour);
	}
}
