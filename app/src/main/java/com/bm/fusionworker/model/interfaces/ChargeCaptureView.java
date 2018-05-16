package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.ScanChargeInfo;
import com.corelibs.base.BaseView;

/**
 * Created by zhangwei on 2018/4/30.
 */

public interface ChargeCaptureView extends BaseView {

    public void onSuccess(ScanChargeInfo info);

    public void onOperationError(String msg);

    public void onError();

}
