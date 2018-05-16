package com.bm.fusionworker.fragment;
import android.os.Bundle;

import com.bm.fusionworker.R;
import com.corelibs.base.BaseFragment;
import com.corelibs.base.BasePresenter;

import butterknife.OnClick;

/**
 * 维修
 */
public class RepairFragment extends BaseFragment {

    @Override
    public void goLogin() {}

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
    }

    @OnClick(R.id.tv_list)
    public void goList(){
        startActivity(HomeListActivity.getLauncher(getContext()));
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
