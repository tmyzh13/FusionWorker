package com.bm.fusionworker.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.bm.fusionworker.MainActivity;
import com.bm.fusionworker.R;
import com.bm.fusionworker.adapter.HomeListAdapter;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.interfaces.InspectionListView;
import com.bm.fusionworker.presenter.InspectionListPresenter;
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
 * 巡检列表
 */

public class InspectionListFragment extends BaseFragment<InspectionListView,InspectionListPresenter> implements PtrLollipopLayout.RefreshCallback, PtrAutoLoadMoreLayout.RefreshLoadCallback {

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
        return R.layout.fragment_inspection_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        homeListAdapter = new HomeListAdapter(getContext());
        listView.setAdapter(homeListAdapter);
        ptr.setRefreshing();//进入页面自动刷新数据
        ptr.setRefreshLoadCallback(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(InspectionItemActivity.getLauncher(getContext(),homeListAdapter.getItem(position)));
            }
        });
    }

    private void setData() {
        Log.e("dp", "---test-setData");
        for (int i = 0; i < 2; i++) {
            RepairDataBean bean1 = new RepairDataBean();
            bean1.title = getString(R.string.repair_bean_title) + "0001";
            bean1.status = 0;
            bean1.distance = getString(R.string.repair_bean_distance);
            bean1.time = getString(R.string.repair_bean_time);
            bean1.address = getString(R.string.repair_bean_address);

            RepairDataBean bean2 = new RepairDataBean();
            bean2.title = getString(R.string.repair_bean_title) + "0002";
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
    protected InspectionListPresenter createPresenter() {
        return new InspectionListPresenter();
    }

    /**
     * 上拉加载更多
     * @param frame
     */
    @Override
    public void onLoading(PtrFrameLayout frame) {
        if (!frame.isAutoRefresh()) {
            setData();
            ptr.complete();
        }
    }

    /**
     * 刷新
     * @param frame
     */
    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        list.clear();
        setData();
        ptr.complete();
    }
}
