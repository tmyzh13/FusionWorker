package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.RepairItemInfoApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RepairItemInfoBean;
import com.bm.fusionworker.model.beans.RequestItemInfoBean;
import com.bm.fusionworker.model.interfaces.RepairItemInfoView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/5/18.
 */

public class RepairItemInfoPresenter extends BasePresenter<RepairItemInfoView> {
    RepairItemInfoApi api;
    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(RepairItemInfoApi.class);
    }

    public void getRepairItemInfoAction(RequestItemInfoBean bean){
        String token = UserHelper.getSavedUser().token;
        api.getItemInfo(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<RepairItemInfoBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<RepairItemInfoBean>>(view) {

                    @Override
                    public void success(BaseData<RepairItemInfoBean> repairItemInfoBeanBaseData) {
                        view.renderData(repairItemInfoBeanBaseData.data);
                    }
                });
    }
}
