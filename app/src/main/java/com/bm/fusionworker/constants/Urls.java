package com.bm.fusionworker.constants;

/**
 * 接口地址
 * Created by john on 2017/11/7.
 */

public class Urls {

        public static final String ROOT = "http://114.115.169.46:8088/charger/api/v1/";

    //登录
    public static final String LOGIN = "user/login";
    //注册
    public static final String REGISTER = "user/register";
    //忘记密码
    public static final String RESET_PWD = "user/restpwd";
    //修改密码
    public static final String MODIFY_PWD = "user/modifypwd";
    //获取验证码
    public static final String GET_CODE = "user/captcha";
    //校验验证码
    public static final String CHECK_CODE="user/checkCaptcha";

    public static final String GET_USER_INFO = "user/getUserInfo";

    public static final String MODIFY_USER_INFO = "user/modifyUserInfo";
    public static final String ADD_FEEDBACK = "user/modifyUserInfo";

    //上传头像
    public static final String MODIFY_IMG = "user/modifyimg";
    //上传照片
    public static final String UPLOAD_IMAGE="upload/img";

    //结束充电
    public static final String STOP_CHARGER = "charging/stop";
    //开始充电
    public static final String START_CHARGER = "charging/start";

    //扫码充电
    public static final String SCAN_CHARGE = "charging/scan";
    //充电站信息
    public static final String CHARGE_PILE_DETAIL = "appMap/info";
    //查询充电桩费率
    public static final String QUERY_FEE = "appMap/queryFee";

    //获取充电详情
    public static final String GET_CHARGE_STATUE = "charging/queryChargingState";
}
