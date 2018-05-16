package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;

public class NotAcceptanceActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_add)
    ImageView iv_add;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, NotAcceptanceActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_accpetance;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.not_acceptance_work));
        iv_add.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.drawable.ic_delete_photo);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.iv_add)
    public void goToCharge() {
        startActivity(ChargeCaptureActivity.getLauncher(NotAcceptanceActivity.this));
    }

    @OnClick(R.id.action_tv)
    public void startAccept() {
        startActivity(AcceptWorkActivity.getLauncher(NotAcceptanceActivity.this));
    }

    @OnClick(R.id.action_tv2)
    public void startAccept2() {
        startActivity(AcceptWorkActivity.getLauncher(NotAcceptanceActivity.this));
    }

}
