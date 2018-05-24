package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.InspectionItemInfoApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.InspectionItemInfoBean;
import com.bm.fusionworker.model.beans.RepairItemInfoBean;
import com.bm.fusionworker.model.beans.RequestItemInfoBean;
import com.bm.fusionworker.model.interfaces.InspectionItemInfoView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/5/21.
 */

public class InspectionItemInfoPresenter extends BasePresenter<InspectionItemInfoView> {
    private InspectionItemInfoApi api;

    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(InspectionItemInfoApi.class);
    }

    public void getInspectionItemInfoAction(RequestItemInfoBean bean) {
        String token = UserHelper.getSavedUser().token;
        api.getInspectionItemInfo(token, bean)
                .compose(new ResponseTransformer<>(this.<BaseData<InspectionItemInfoBean>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<InspectionItemInfoBean>>(view) {

                    @Override
                    public void success(BaseData<InspectionItemInfoBean> inspectionItemInfoBeanBaseData) {
                        view.renderData(inspectionItemInfoBeanBaseData.data);
                    }
                });

    }
}
