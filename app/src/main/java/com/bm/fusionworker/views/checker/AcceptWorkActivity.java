package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;

public class AcceptWorkActivity extends BaseActivity {
    @Bind(R.id.nav)
    NavBar navBar;
    @Override
    public void goLogin() {}

    public static Intent getLauncher(Context context){
        Intent intent = new Intent(context,AcceptWorkActivity.class);
        return intent;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_accept_work;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.accept_work));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
