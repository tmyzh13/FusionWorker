package com.bm.fusionworker.fragment;

import android.os.Bundle;

import com.bm.fusionworker.R;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

public class HomeListActivity extends BaseActivity {

    @Override
    public void goLogin() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
