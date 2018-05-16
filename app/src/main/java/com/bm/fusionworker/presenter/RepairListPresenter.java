package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.apis.RepairListApi;
import com.bm.fusionworker.model.interfaces.RepairListView;
import com.corelibs.api.ApiFactory;
import com.corelibs.base.BasePresenter;

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
    public void getRepairListAction(){

    }
}
