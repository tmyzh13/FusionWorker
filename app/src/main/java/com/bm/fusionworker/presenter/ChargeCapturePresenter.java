package com.bm.fusionworker.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.ScanApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RequestScanBean;
import com.bm.fusionworker.model.beans.ScanChargeInfo;
import com.bm.fusionworker.model.interfaces.ChargeCaptureView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by zhangwei on 2018/4/30.
 */

public class ChargeCapturePresenter extends BasePresenter<ChargeCaptureView> {

    private ScanApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(ScanApi.class);
    }

    @Override
    public void onStart() {

    }

    public void getData(String contents){
        RequestScanBean bean = new RequestScanBean();
        bean.setAppUserId(UserHelper.getSavedUser().appUserId + "");
        Log.e("zw","UserHelper.getSavedUser().userId : " + UserHelper.getSavedUser().appUserId);
//        bean.setQrCode("1300000001");
        bean.setQrCode(contents);
        api.getScanChargeInfo(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ScanChargeInfo>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ScanChargeInfo>>(view) {
                               @Override
                               public void success(BaseData<ScanChargeInfo> baseData) {
                                   view.onSuccess(baseData.data);
                               }

                               @Override
                               public boolean operationError(BaseData<ScanChargeInfo> scanChargeInfoBaseData, int status, String message) {
                                   if(!TextUtils.isEmpty(message)) {
                                       view.onOperationError(message);
                                   }
                                   return super.operationError(scanChargeInfoBaseData, status, message);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                  view.onError();
                               }
                           }
                );
    }
}
