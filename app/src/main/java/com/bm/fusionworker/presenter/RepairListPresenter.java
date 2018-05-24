package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.RepairListApi;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.RepairListData;
import com.bm.fusionworker.model.beans.RequestRepairListBean;
import com.bm.fusionworker.model.interfaces.RepairListView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by issuser on 2018/5/9.
 */

public class RepairListPresenter extends BasePresenter<RepairListView> {
    RepairListApi api;
    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(RepairListApi.class);
    }

    /**
     * 获取维修数据
     */
    public void getRepairListAction(RequestRepairListBean bean){
        String token = UserHelper.getSavedUser().token;
        api.getRepairListData(token,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<RepairListData>>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<BaseData<RepairListData>>(view) {
                    @Override
                    public void success(BaseData<RepairListData> repairListDataBaseData) {
                        view.renderData(repairListDataBaseData.data.list);
                    }
                });
    }
}
