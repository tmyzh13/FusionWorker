package com.bm.fusionworker.presenter;

import android.util.Log;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.ScanApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RequestScanBean;
import com.bm.fusionworker.model.beans.ScanChargeInfo;
import com.bm.fusionworker.model.interfaces.ChargeInputNumberView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;

import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by Administrator on 2018/5/9.
 */

public class ChargeInputNumberPresenter extends BasePresenter<ChargeInputNumberView> {

    private ScanApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();

        api = ApiFactory.getFactory().create(ScanApi.class);
    }

    @Override
    public void onStart() {

    }


    public void getData(long number){

        RequestScanBean bean = new RequestScanBean();
        bean.setAppUserId(UserHelper.getSavedUser().appUserId + "");
        bean.setQrCode(number + "");

        //正确
        api.getScanChargeInfo(UserHelper.getSavedUser().token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<ScanChargeInfo>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<ScanChargeInfo>>(view) {
                               @Override
                               public void success(BaseData<ScanChargeInfo> baseData) {
                                    view.onGetDataSuccess(baseData.data);
                               }

                               @Override
                               public boolean operationError(BaseData<ScanChargeInfo> scanChargeInfoBaseData, int status, String message) {
                                   view.onGetDataFail(scanChargeInfoBaseData.msg);
                                   return super.operationError(scanChargeInfoBaseData,status,message);

                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   view.onGetDataFail(getString(R.string.time_out_or_qrcode_wrong));
                               }
                           }
                );


     /*   LoginApi api1 = ApiFactory.getFactory().create(LoginApi.class);
        LoginRequestBean bean1 = new LoginRequestBean();
        bean1.phone = "17366206080";
//        bean.carrier = Tools.getPhoneType();
        bean1.carrier = "iphone";
        bean1.type = 0;
        if (0 == 0) {//密码登录
            bean1.passWord = "123";
        }
        api1.login(bean1)
                .compose(new ResponseTransformer<>(this.<BaseData>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData baseData) {
                        Log.e("zw",baseData.toString());

                    }
                });*/

    }

}
