package com.bm.fusionworker.constants;

/**
 * 一些用于记录的关键字
 * Created by john on 2017/11/7.
 */

public class Constant {

    //记住密码
    public static final String REMEMBER="remember";

    //是否登录过了
    public static final String LOGIN_STATUE="login_staue";

    //刷新首页的状态
    public static final String REFRESH_HOME_STATUE="refresh_home_statue";

    //登录失败
    //用户名或密码错误
    public static final int LOGIN_ACOUNT_PWD_FAILURE = 203;
    //用户名或验证码错误
    public static final int LOGIN_ACOUNT_CAPTCHA_FAILURE = 205;
    //验证码错误
    public static final int GET_CAPTCHA_FAILURE = 206;
    //验证码错误
    public static final int CAPTCHA_FAILURE = 202;

    //注册失败
    public static final int REGISTER_FAILURE = 1;
    //手机号已注册
    public static final int PHONE_HAS_USED = 201;

    //临时图片集合
    public static final String TEMP_PIC_LIST="temp_pic_list";

    //预约计时
    public static final String TIME_APPOINTMENT="time_appointment";
    //预约时长
    public static final String APPOINTMENT_DURING="appointment_during";

    //刷新预约时间
    public static final String REFRESH_APPOINTMENT_TIME="refresh_appointment_time";
    //预约超时
    public static final String APPOINTMENT_TIME_OUT="appointment_time_out";
    //充电时间
    public static final String CHARGING_TIME="charge_time";
}
