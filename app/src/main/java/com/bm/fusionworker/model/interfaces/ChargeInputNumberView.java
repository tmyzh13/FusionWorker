package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.ScanChargeInfo;
import com.corelibs.base.BaseView;

/**
 * Created by zhangwei on 2018/4/30.
 */

public interface ChargeInputNumberView extends BaseView {

    public void onGetDataSuccess(ScanChargeInfo info);

    public void onGetDataFail(String msg);
}
