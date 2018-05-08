package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.CheckCodeBean;
import com.bm.fusionworker.model.beans.LoginRequestBean;
import com.bm.fusionworker.model.beans.RestPwdRequestBean;
import com.bm.fusionworker.model.beans.UserBean;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/5/5.
 */

public interface LoginApi {
    //login
    @POST(Urls.LOGIN)
    Observable<BaseData<UserBean>> login(@Body LoginRequestBean bean);
    //注册
    @POST(Urls.REGISTER)
    Observable<BaseData> register(@Body LoginRequestBean bean);
    //获取验证码
    @POST(Urls.GET_CODE)
    Observable<BaseData> getCode(@Body LoginRequestBean bean);

    @POST(Urls.CHECK_CODE)
    Observable<BaseData> checkCode(@Body CheckCodeBean bean);

    //重置密码、忘记密码
    @POST(Urls.RESET_PWD)
    Observable<BaseData> restPwd(@Body RestPwdRequestBean bean);
}
