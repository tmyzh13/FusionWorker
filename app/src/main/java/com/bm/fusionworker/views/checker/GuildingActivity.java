package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.fragment.RepairingActivity;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;

public class GuildingActivity extends BaseActivity {

    private Context context = GuildingActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.start_accept_tv)
    TextView start_accept_tv;

    private int status = 0;//0:开始维修导航 1：开始验收导航

    public static Intent getLauncher(Context context, int status) {
        Intent intent = new Intent(context, GuildingActivity.class);
        intent.putExtra("status", status);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guilding;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.guilding));
        iv_back.setVisibility(View.VISIBLE);
        iv_back.setImageResource(R.drawable.pop_ic_del);
        status = getIntent().getIntExtra("status", 0);
        if (status == 0) {
            start_accept_tv.setText(getString(R.string.start_repair));
        } else if (status == 1){
            start_accept_tv.setText(getString(R.string.start_accept));
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.start_accept_tv)
    public void startAccept() {
        if (status == 0) {
            startActivity(RepairingActivity.getLauncher(context));
        } else if (status == 1){
            startActivity(AcceptWorkActivity.getLauncher(context));
        }
    }

    @OnClick(R.id.iv_back)
    public void cancel() {
        finish();
    }
}
