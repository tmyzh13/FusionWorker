package com.bm.fusionworker.model.interfaces;

import com.bm.fusionworker.model.beans.ChargerStatueBean;
import com.corelibs.base.BaseView;

/**
 * Created by issuser on 2018/4/24.
 */

public interface ChargerStatueView extends BaseView {

    void renderChargerStatueData(ChargerStatueBean bean);
    void endChargeSuccess();
    void endChargeFail();
}
