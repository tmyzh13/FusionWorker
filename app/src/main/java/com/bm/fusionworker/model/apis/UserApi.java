package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.AddFeedBackRequestBean;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.UserBean;
import com.bm.fusionworker.model.beans.UserInfoBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/5/5.
 */

public interface UserApi {
    //获取用户信息
    @POST(Urls.GET_USER_INFO)
    Observable<BaseData<UserInfoBean>> getUserInfo(@Header("Access-Token") String token, @Body UserBean bean);
    //意见反馈
    @POST(Urls.ADD_FEEDBACK)
    Observable<BaseData> addFeedBack(@Header("Access-Token") String token,@Body AddFeedBackRequestBean bean);

}
