package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.beans.RepairItemInfoBean;
import com.bm.fusionworker.model.beans.RequestItemInfoBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 抽屉 详情数据
 * Created by issuser on 2018/5/18.
 */

public interface RepairItemInfoApi {
    @POST(Urls.GET_ITEM_INFO)
    Observable<BaseData<RepairItemInfoBean>> getItemInfo(@Header("AccessToken")String token, @Body RequestItemInfoBean bean);
}
