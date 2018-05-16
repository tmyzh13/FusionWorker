package com.bm.fusionworker.views.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bm.fusionworker.R;
import com.bm.fusionworker.adapter.RefundHistoryAdapter;
import com.bm.fusionworker.model.beans.RefundItemBean;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.views.cube.ptr.PtrFrameLayout;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.layout.PtrLollipopLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class RefundHistoryActivity extends BaseActivity implements PtrLollipopLayout.RefreshCallback{
    private Context context = RefundHistoryActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptr;
    @Bind(R.id.list_view)
    AutoLoadMoreListView listView;

    private List<RefundItemBean> list = new ArrayList<>();
    private RefundHistoryAdapter adapter;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, RefundHistoryActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refund_history;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.refund_history));
        adapter = new RefundHistoryAdapter(context);
        listView.setAdapter(adapter);
        ptr.disableLoading();
        setData();
    }

    private void setData() {
        for (int i = 0; i < 3; i++) {
            RefundItemBean bean = new RefundItemBean();
            bean.title = i + getString(R.string.repair_bean_title);
            bean.time = getString(R.string.repair_bean_time);
            bean.distance = "500米";
            bean.address = getString(R.string.repair_bean_address);
            bean.reason = getString(R.string.refund_des);
            list.add(bean);
        }
        adapter.replaceAll(list);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onRefreshing(PtrFrameLayout frame) {
        ptr.complete();
    }
}
