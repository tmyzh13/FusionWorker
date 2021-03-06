package com.bm.fusionworker.presenter;

import android.util.Log;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.LoginApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.ModifyUserInfoRequestBean;
import com.bm.fusionworker.model.beans.ResponseMessageBean;
import com.bm.fusionworker.model.beans.UploadImageBean;
import com.bm.fusionworker.model.beans.UserInfoBean;
import com.bm.fusionworker.model.interfaces.UserInfoView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;

import com.trello.rxlifecycle.ActivityEvent;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;


public class UserInfoPresenter extends BasePresenter<UserInfoView> {


    private LoginApi api;

    @Override
    public void onStart() {

    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(LoginApi.class);
    }

    /**
     * 获取用户信息请求
     */
    public void doGetUserInfoRequest() {
        api.getUserInfo(PreferencesHelper.getData("token"))
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>(view) {
                    @Override
                    public void success(BaseData<UserInfoBean> baseData) {

                        Log.e("getuserinfo", baseData.data.toString());
                        if (baseData.code == 0) {
                            UserHelper.saveUserInfo(baseData.data);
                            view.onGetUserInfoSuccess(baseData.data);
                        } else {
                            view.onGetUsrInfoFail();
                        }
                    }
                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userBeanBaseData, int status, String message) {

                        return super.operationError(userBeanBaseData, status, message);
                    }
                });
    }

    /**
     * 修改用户信息
     * @param userInfoRequest
     */
    public void doModifyUserInfo(ModifyUserInfoRequestBean userInfoRequest) {
        api.modifyUserInfo(PreferencesHelper.getData("token"),userInfoRequest)
                .compose(new ResponseTransformer<>(this.<BaseData<ResponseMessageBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ResponseMessageBean>>(view) {
                    @Override
                    public void success(BaseData<ResponseMessageBean> baseData) {

                        if (baseData.code == 0) {
                            view.onModifySuccess();
                        } else {
                            view.onModifyUserInfoFail();
                        }
                    }
                    @Override
                    public boolean operationError(BaseData<ResponseMessageBean> userBeanBaseData, int status, String message) {

                        return super.operationError(userBeanBaseData, status, message);
                    }
                });
    }

    /**
     * 上传用户头像
     */

    public void uploadImage(File file){

        //图片参数  MediaType.parse("image/*"
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Log.e("zw","token :" + UserHelper.getSavedUser().token);
        api.upload(UserHelper.getSavedUser().token,body)
                .compose(new ResponseTransformer<>(this.<BaseData<UploadImageBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<UploadImageBean>>(view) {
                    @Override
                    public void success(BaseData<UploadImageBean> baseData) {
                        view.onUploadPhotoSuccess(baseData.data.getImgurl());
                    }

                    @Override
                    public boolean operationError(BaseData<UploadImageBean> baseData, int status, String message) {
                        view.onUploadPhotoFail();
                        return super.operationError(baseData, status, message);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.onUploadPhotoFail();
                    }
                });

    }

}
