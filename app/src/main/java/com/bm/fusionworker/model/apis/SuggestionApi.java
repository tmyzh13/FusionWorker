package com.bm.fusionworker.model.apis;

import com.bm.fusionworker.constants.Urls;
import com.bm.fusionworker.model.beans.ResultOnlyWithCodeBean;
import com.bm.fusionworker.model.beans.SuggestionBean;

import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by issuser on 2018/5/18.
 */

public interface SuggestionApi {
    @POST(Urls.PUBLISH_SUGGESTION)
    Observable<ResultOnlyWithCodeBean> commit(@Header("AccessToken") String token, @Body SuggestionBean bean);
}
