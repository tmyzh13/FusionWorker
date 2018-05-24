package com.bm.fusionworker.views.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;

public class AboutUsActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar bar;
    @Bind(R.id.version_name)
    TextView version_name;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, AboutUsActivity.class);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        bar.setColorRes(R.color.app_blue);
        bar.setNavTitle(getString(R.string.about_us));
        version_name.setText(Tools.getVersionName(this));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void goLogin() {
    }
}
