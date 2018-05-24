package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RepairListData;
import com.bm.fusionworker.model.beans.RequestRepairListBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/5/9.
 */

public interface RepairListApi {
    @POST(Urls.GET_REPAIR_LIST)
    Observable<BaseData<RepairListData>> getRepairListData(@Header("AccessToken") String token,
                                                           @Body RequestRepairListBean bean);
}
