package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.InspectionItemInfoBean;
import com.bm.fusionworker.model.beans.RequestItemInfoBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/5/21.
 */

public interface InspectionItemInfoApi {
    @POST(Urls.GET_ITEM_INFO)
    Observable<BaseData<InspectionItemInfoBean>> getInspectionItemInfo(@Header("AccessToken")String token, @Body RequestItemInfoBean bean);
}
