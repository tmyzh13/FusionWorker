package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.ChargerStatueBean;
import com.bm.fusionworker.model.beans.RequestEndChargerBean;
import com.bm.fusionworker.model.beans.RequestStartChargerBean;
import com.bm.fusionworker.model.beans.RequstChargeStatueBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/4/24.
 */

public interface ChargeStatueApi {

    @POST(Urls.GET_CHARGE_STATUE)
    Observable<BaseData<ChargerStatueBean>> getChargeStatue(@Header("AccessToken") String token, @Body RequstChargeStatueBean bean);

    @POST(Urls.START_CHARGER)
    Observable<BaseData> startCharge(@Body RequestStartChargerBean bean);

    @POST(Urls.STOP_CHARGER)
    Observable<BaseData<String>> stopCharge(@Header("AccessToken") String token, @Body RequestEndChargerBean bean);
}
