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

/**
 * 巡检 Fragment
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
