package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.RepairItemInfoBean;
import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/5/18.
 */

public interface RepairItemInfoView extends BaseView {
    void renderData(RepairItemInfoBean repairItemInfoBean);
}
