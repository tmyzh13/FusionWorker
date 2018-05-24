package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.InspectionItemInfoBean;
import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/5/21.
 */

public interface InspectionItemInfoView extends BaseView{
    void renderData(InspectionItemInfoBean inspectionItemInfoBean);
}
