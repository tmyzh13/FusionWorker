package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.UserInfoBean;
import com.corelibs.base.BasePaginationView;

/**
 * Created by issuser on 2018/4/17.
 */

public interface UserView extends BasePaginationView {
    void getUserInfo(UserInfoBean userInfoBean);

    void getUserInfoError();

    void addFeedBackSuccess();

    void  updatePwdSuccess();

    void updatePwdError();
}
