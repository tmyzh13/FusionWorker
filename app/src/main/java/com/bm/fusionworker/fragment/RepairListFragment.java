package com.bm.fusionworker.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bm.fusionworker.MainActivity;
import com.bm.fusionworker.R;
import com.bm.fusionworker.adapter.HomeListAdapter;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.beans.RequestRepairListBean;
import com.bm.fusionworker.model.interfaces.RepairListView;
import com.bm.fusionworker.presenter.RepairListPresenter;
import com.corelibs.base.BaseFragment;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 维修列表
 */
public class RepairListFragment extends BaseFragment<RepairListView,RepairListPresenter> implements RepairListView,PtrLollipopLayout.RefreshCallback, PtrAutoLoadMoreLayout.RefreshLoadCallback {

    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptr;
    @Bind(R.id.lv_list)
    AutoLoadMoreListView listView;

    private HomeListAdapter homeListAdapter;
    private List<RepairDataBean> list = new ArrayList<>();

    @Override
    public void goLogin() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        RequestRepairListBean bean = new RequestRepairListBean();
        bean.type = 0;
//        presenter.getRepairListAction(bean);

        homeListAdapter = new HomeListAdapter(getContext());
        listView.setAdapter(homeListAdapter);
        ptr.setRefreshing();
        ptr.setRefreshLoadCallback(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(RepairItemActivity.getLauncher(getContext(), homeListAdapter.getItem(position)));
            }
        });
    }

    private void setData() {
        Log.e("dp", "----test-setData--");
        for (int i = 0; i < 2; i++) {
            RepairDataBean bean1 = new RepairDataBean();
            bean1.title = getString(R.string.repair_bean_title) + "01";
            bean1.status = 0;
            bean1.distance = getString(R.string.repair_bean_distance);
            bean1.time = getString(R.string.repair_bean_time);
            bean1.address = getString(R.string.repair_bean_address);

            RepairDataBean bean2 = new RepairDataBean();
            bean2.title = getString(R.string.repair_bean_title) + "02";
            bean2.status = 1;
            bean2.time = getString(R.string.repair_bean_time);
            bean2.distance = getString(R.string.repair_bean_distance);
            bean2.address = getString(R.string.repair_bean_address);
            RepairDataBean bean3 = new RepairDataBean();
            bean3.title = getString(R.string.repair_bean_title) + "03";
            bean3.status = 2;
            bean3.time = getString(R.string.repair_bean_time);
            bean3.distance = getString(R.string.repair_bean_distance);
            bean3.address = getString(R.string.repair_bean_address);
            list.add(bean1);
            list.add(bean2);
            list.add(bean3);
        }
        homeListAdapter.replaceAll(list);
    }

    @OnClick(R.id.tv_map)
    public void gotoMap() {
        startActivity(MainActivity.getLauncher(getContext()));
    }

    @Override
    protected RepairListPresenter createPresenter() {
        return new RepairListPresenter();
    }

    @Override
    public void showLoading() {
        ptr.setRefreshing();
    }

    @Override
    public void hideLoading() {
        ptr.complete();
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        //下拉刷新
        list.clear();
        setData();
        ptr.complete();
    }

    @Override
    public void onLoading(PtrFrameLayout frame) {
        if (!frame.isAutoRefresh()) {
            setData();
            ptr.complete();
        }
    }

    @Override
    public void renderData(List<RepairDataBean> list) {
        //TODO
    }
}
