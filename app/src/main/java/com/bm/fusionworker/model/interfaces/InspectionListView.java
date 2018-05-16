package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.RepairDataBean;
import com.corelibs.base.BaseView;

import java.util.List;

/**
 * Created by issuser on 2018/5/16.
 */

public interface InspectionListView extends BaseView{
    void getInspectionListDataFailure();
    void renderData(List<RepairDataBean> list);
}
