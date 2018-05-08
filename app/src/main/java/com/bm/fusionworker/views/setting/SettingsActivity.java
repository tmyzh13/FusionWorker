package com.bm.fusionworker.views.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;

public class SettingsActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar navBar;

    public static Intent getLauncher(Context context){
        Intent intent = new Intent(context,SettingsActivity.class);
        return intent;
    }
    @Override
    public void goLogin() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.settings));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
