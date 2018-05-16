package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.UserInfoBean;
import com.corelibs.base.BaseView;

public interface UserInfoView extends BaseView {
    void onGetUserInfoSuccess(UserInfoBean userInfoBean);

    void onGetUsrInfoFail();

    void onModifyUserInfoFail();

    void onModifySuccess();

    void onUploadPhotoSuccess(String imgUrl);

    void onUploadPhotoFail();
}
