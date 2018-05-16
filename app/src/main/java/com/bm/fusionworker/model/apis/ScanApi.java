package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.ChargeDetailFeeBean;
import com.bm.fusionworker.model.beans.OrderRequestInfo;
import com.bm.fusionworker.model.beans.PileFeeBean;
import com.bm.fusionworker.model.beans.PileList;
import com.bm.fusionworker.model.beans.RequestChargePileDetailBean;
import com.bm.fusionworker.model.beans.RequestChargeQueryFeeBean;
import com.bm.fusionworker.model.beans.RequestFeeBean;
import com.bm.fusionworker.model.beans.RequestScanBean;
import com.bm.fusionworker.model.beans.ScanChargeInfo;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhangwei on 2018/4/26.
 */

public interface ScanApi {

    //充电桩信息
    @POST(Urls.SCAN_CHARGE)
    Observable<BaseData<ScanChargeInfo>> getScanChargeInfo(@Header("AccessToken") String token, @Body RequestScanBean bean);

    //开始充电
    @POST(Urls.START_CHARGER)
    Observable<BaseData> getOrderDetail(@Header("AccessToken") String token, @Body OrderRequestInfo bean);

    //充电站详情_STATION
//    @POST(Urls.CHARGE_PILE_DETAIL)
//    Observable<BaseData<ChargeStationDetailBean>> getChargeStationDetail(@Header("AccessToken") String token, @Body RequestChargePileDetailBean bean);

    //充电站详情_PILE
    @POST(Urls.CHARGE_PILE_DETAIL)
    Observable<BaseData<PileList>> getChargePileDetail(@Header("AccessToken") String token, @Body RequestChargePileDetailBean bean);


    //我的订单
//    @POST(Urls.MY_ORDER)
//    Observable<MyOrderData> getMyOrder(@Header("AccessToken") String token, @Body RequestMyOrderBean bean);

    //根据充电桩ID查询阶梯电价和服务费
    @POST(Urls.QUERY_FEE)
    Observable<BaseData<ChargeDetailFeeBean>> getQueryFee(@Header("AccessToken") String token, @Body RequestChargeQueryFeeBean bean);

    @POST(Urls.QUERY_FEE)
    Observable<BaseData<PileFeeBean>> getFeeData(@Body RequestFeeBean bean);

}
