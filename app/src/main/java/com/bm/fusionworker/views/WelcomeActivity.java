package com.bm.fusionworker.views;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bm.fusionworker.R;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

public class WelcomeActivity extends BaseActivity {

    @Override
    public void goLogin() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(LoginActivity.getLauncher(WelcomeActivity.this));
                finish();
            }
        }, 2000);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
