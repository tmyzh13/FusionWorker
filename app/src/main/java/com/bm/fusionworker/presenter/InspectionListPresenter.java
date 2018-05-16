package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.apis.InspectionListApi;
import com.bm.fusionworker.model.interfaces.InspectionListView;
import com.corelibs.api.ApiFactory;
import com.corelibs.base.BasePresenter;

/**
 * Created by issuser on 2018/5/16.
 */

public class InspectionListPresenter extends BasePresenter<InspectionListView> {
    InspectionListApi api;

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(InspectionListApi.class);
    }

    @Override
    public void onStart() {
    }

    /**
     * 获取巡检列表数据
     */
    public void getInspectionListDataAction(){

    }
}
