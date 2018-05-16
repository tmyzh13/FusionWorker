package com.bm.fusionworker.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.fusionworker.R;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;

import butterknife.OnClick;

/**
 * 巡检地图 Fragment
 */
public class InspectionFragment extends BaseFragment {

    @Override
    public void goLogin() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inspection;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @OnClick(R.id.tv_list)
    public void goList() {
        startActivity(HomeListActivity.getLauncher(getContext()));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
