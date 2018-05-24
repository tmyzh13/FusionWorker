package com.bm.fusionworker.views.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.LoginActivity;
import com.bm.fusionworker.weights.CommonDialog;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    private Context context = SettingsActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.tv_about_us)
    TextView tv_about_us;
    @Bind(R.id.tv_version)
    TextView tv_version;
    @Bind(R.id.tv_help)
    TextView tv_help;
    @Bind(R.id.tv_clause)
    TextView tv_clause;//法律条规
    @Bind(R.id.tv_advice)
    TextView tv_advice;//意见反馈
    @Bind(R.id.contact_us_tv)
    TextView contact_us_tv;//联系客服

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.settings));
        clicks();
        tv_version.setText(Tools.getVersionName(context));
    }

    private void clicks() {
        tv_about_us.setOnClickListener(this);
        tv_help.setOnClickListener(this);
        tv_clause.setOnClickListener(this);
        tv_advice.setOnClickListener(this);
        contact_us_tv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_about_us:
                startActivity(AboutUsActivity.getLauncher(context));
                break;
            case R.id.tv_help:
                break;
            case R.id.tv_clause:
                break;
            case R.id.tv_advice:
                startActivity(FeedBackActivity.getLauncher(context));
                break;
            case R.id.contact_us_tv:
                break;
            default:
                break;
        }
    }

    /**
     * 退出当前用户
     */
    @OnClick(R.id.tv_login_out)
    public void gotoLoginout() {
        final CommonDialog dialog = new CommonDialog(this, "", getString(R.string.sure_logout), 2);
        dialog.show();
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(LoginActivity.getLauncher(context));
                finish();
            }
        });

        dialog.setNagitiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 退出应用
     */
    @OnClick(R.id.tv_exit)
    public void exit() {
        final CommonDialog dialog = new CommonDialog(this, "", getString(R.string.sure_exit), 2);
        dialog.show();
        dialog.setPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PreferencesHelper.clearData();
                finish();
                AppManager.getAppManager().appExit();
            }
        });
        dialog.setNagitiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
