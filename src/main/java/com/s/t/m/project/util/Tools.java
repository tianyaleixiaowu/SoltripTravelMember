package com.s.t.m.project.util;

import com.alibaba.fastjson.JSON;
import com.s.t.m.common.utils.DateUtils;
import com.s.t.m.common.utils.ObjectUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import static org.aspectj.bridge.Version.text;

/**
 * 工具
 */
public class Tools {

    /**
     * 判断是否过去20分钟
     * @param date 时间
     * @return
     */
    public static boolean dateToTwenty(Date date){
        long pastHour = DateUtils.pastMinutes(date);//判断时间过去多久
        if(pastHour == 20 || pastHour > 20 || pastHour < 0){//判断时间是否大于20分钟
            return false;//过20分
        }
        return true;//未过20分
    }

    /**
     * 判断是否是手机
     * @param mobile
     * @return true 是手机号，false不是手机号
     */
    public static boolean IsMobile(String mobile) {
        if (ObjectUtils.isEmpty(text)) {
            return false;
        }else{
            return Pattern.matches("^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$", mobile);
        }
    }

    /**
     * 验证非数字
     * @param text
     * @return true 是数字， false 不是数字
     */
    public static Boolean CheckNumber(String text) {
        if (ObjectUtils.isEmpty(text)) {
            return false;
        } else {
            return Pattern.matches("^[0-9]+$",text);
        }
    }

    /** 中国公民身份证号码最小长度。 */
    public  final int CHINA_ID_MIN_LENGTH = 15;

    /** 中国公民身份证号码最大长度。 */
    public  final int CHINA_ID_MAX_LENGTH = 18;
    /**
     * 根据身份编号获取年龄
     *
     * @param idCard
     *            身份编号
     * @return 年龄
     */
    public static int getAgeByIdCard(String idCard) {
        int iAge = 0;
        Calendar cal = Calendar.getInstance();
        String year = idCard.substring(6, 10);
        int iCurrYear = cal.get(Calendar.YEAR);
        iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 根据身份编号获取生日
     *
     * @param idCard 身份编号
     * @return 生日(yyyyMMdd)
     */
    public static String getBirthByIdCard(String idCard) {
        return idCard.substring(6, 14);
    }

    /**
     * 根据身份编号获取生日年
     *
     * @param idCard 身份编号
     * @return 生日(yyyy)
     */
    public static Short getYearByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(6, 10));
    }

    /**
     * 根据身份编号获取生日月
     *
     * @param idCard
     *            身份编号
     * @return 生日(MM)
     */
    public static Short getMonthByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(10, 12));
    }

    /**
     * 根据身份编号获取生日天
     *
     * @param idCard
     *            身份编号
     * @return 生日(dd)
     */
    public static Short getDateByIdCard(String idCard) {
        return Short.valueOf(idCard.substring(12, 14));
    }

    /**
     * 根据身份编号获取性别
     *
     * @param idCard 身份编号
     * @return 性别(M-男，F-女，N-未知)
     */
    public static String getGenderByIdCard(String idCard) {
        String sGender = "未知";

        String sCardNum = idCard.substring(16, 17);
        if (Integer.parseInt(sCardNum) % 2 != 0) {
            sGender = "1";//男
        } else {
            sGender = "2";//女
        }
        return sGender;
    }
   


    public static void main(String[] args){
        Boolean b = Pattern.matches("^[0-9]+$", "");
        System.out.println("123001100221".length() ==8);
    }
}
