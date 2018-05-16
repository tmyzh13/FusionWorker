package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.PileFeeBean;
import com.corelibs.base.BaseView;

/**
 * Created by zhangwei on 2018/5/2.
 */

public interface ChargeOrderDetailView extends BaseView {

    public void onSuccess();

    public void onFail();
    void showPileFeeInfo(PileFeeBean bean);
}
