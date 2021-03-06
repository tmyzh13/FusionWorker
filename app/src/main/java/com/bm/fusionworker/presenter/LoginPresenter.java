package com.bm.fusionworker.presenter;

import android.util.Log;

import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.LoginApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.CheckCodeBean;
import com.bm.fusionworker.model.beans.LoginRequestBean;
import com.bm.fusionworker.model.beans.RestPwdRequestBean;
import com.bm.fusionworker.model.beans.UserBean;
import com.bm.fusionworker.model.interfaces.LoginView;
import com.bm.fusionworker.utils.Tools;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;

import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/4/19.
 */

public class LoginPresenter extends BasePresenter<LoginView> {
    LoginApi api;

    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(LoginApi.class);
    }

    /**
     * @param type 0：手机号+密码 1：手机号+验证码 2：华为账号登录
     * @param
     * @param
     */
    public void loginAction(int type, final String phone, String pwd, String captcha) {

        LoginRequestBean bean = new LoginRequestBean();
        bean.phone = phone;
        bean.carrier = Tools.getPhoneType();
        bean.type = type;
        if (type == 0) {//密码登录
            bean.passWord = pwd;
        } else if (type == 1) {//验证码登录
            bean.captcha = captcha;
        }
        view.showLoading();
        api.login(bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserBean>>(view) {
                    @Override
                    public void success(BaseData<UserBean> baseData) {

                        Log.e("loginAction", "---code:" + baseData.code);
                        if (baseData.code == 0) {
//                            PreferencesHelper.saveData(Constant.LOGIN_STATUE, "1");
                            UserHelper.saveUser(baseData.data);
                            if (null != PreferencesHelper.getData("phone")) {
                                PreferencesHelper.remove("phone");
                            }
                            PreferencesHelper.saveData("phone", phone);
                            if (null != PreferencesHelper.getData("token")) {
                                PreferencesHelper.remove("token");
                            }
                            PreferencesHelper.saveData("token", baseData.data.token);
                            view.loginSuccess();
                        } else {
                            view.loginFailure(baseData.code);
                        }
                    }
                    @Override
                    public boolean operationError(BaseData<UserBean> userBeanBaseData, int status, String message) {
                        Log.e("dp","----operationError:code="+status);
                        view.loginFailure(status);
                        return super.operationError(userBeanBaseData, status, message);
                    }
                });
    }

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param captcha
     */
    public void registerAction(String phone, String pwd, String captcha) {

        LoginRequestBean bean = new LoginRequestBean();
        bean.phone = phone;
        bean.passWord = pwd;
        bean.captcha = captcha;
        view.showLoading();
        api.register(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        if (baseData.code == 0) {
                            view.registerSuccess();
                        }
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {
                        view.loginFailure(status);
                        return super.operationError(baseData, status, message);
                    }
                });
    }

    /**
     * @param type  type:短信类型（1:获取注册短信验证码;2:获取找回密码短信,3:获取登录验证码）
     *              phone:手机号码
     * @param phone
     */
    public void getCodeAction(int type, final String phone) {
        LoginRequestBean bean = new LoginRequestBean();
        bean.type = type;
        bean.phone = phone;
        view.showLoading();
        api.getCode(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        PreferencesHelper.saveData("phone", phone);
                        view.getCodeSuccess();
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {

                        return super.operationError(baseData, status, message);
                    }
                });
    }

    /**
     * 重置密码
     *
     * @param bean
     */
    public void restPwdAction(RestPwdRequestBean bean) {
        view.showLoading();
        api.restPwd(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.loginSuccess();
                    }

                    @Override
                    public boolean operationError(BaseData baseData, int status, String message) {

                        return super.operationError(baseData, status, message);
                    }
                });
    }

    public void checkCode(CheckCodeBean bean){
        view.showLoading();
        api.checkCode(bean)
                .compose(new ResponseTransformer<BaseData>())
                .subscribe(new ResponseSubscriber<BaseData>(view) {
                    @Override
                    public void success(BaseData baseData) {
                        view.checkCodeSuccess();
                    }
                });
    }

}
