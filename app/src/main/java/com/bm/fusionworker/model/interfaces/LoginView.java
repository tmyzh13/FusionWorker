package com.bm.fusionworker.model.interfaces;

import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/5/5.
 */

public interface LoginView extends BaseView {
    void loginSuccess();
    void registerSuccess();
    void getCodeSuccess();
    void loginFailure(int type);
    void checkCodeSuccess();
}
