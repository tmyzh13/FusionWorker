package com.bm.fusionworker.views;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.RestPwdRequestBean;
import com.bm.fusionworker.model.interfaces.LoginView;
import com.bm.fusionworker.presenter.LoginPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.weights.CommonDialog;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;


import butterknife.Bind;
import butterknife.OnClick;

public class InputNewPwdActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    private Context context = InputNewPwdActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.input_new_pwd_et)
    EditText inputNewPwdEt;
    @Bind(R.id.confirm_pwd_et)
    EditText confirmPwdEt;
    @Bind(R.id.submit_tv)
    TextView submit_tv;

    private String newPwd1, newPwd2;
    private String phone;
    private String captcha;

    private PopupWindow popupWindow;

    public static Intent getLaunch(Context context, String phone, String captcha) {
        Intent intent = new Intent(context, InputNewPwdActivity.class);
        intent.putExtra("phone", phone);
        intent.putExtra("captcha", captcha);
        return intent;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_input_new_pwd;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getResources().getString(R.string.reset_password_input_password));
        navBar.setColor(getResources().getColor(R.color.app_blue));
        phone = getIntent().getStringExtra("phone");
        captcha = getIntent().getStringExtra("captcha");
    }

    @OnClick(R.id.submit_tv)
    public void actionSubmit() {
        newPwd1 = inputNewPwdEt.getText().toString().trim();
        newPwd2 = confirmPwdEt.getText().toString().trim();
        if (Tools.isPwdRight(newPwd1)) {
            if (newPwd1.equals(newPwd2)) {
                RestPwdRequestBean bean = new RestPwdRequestBean();
                bean.phone = phone;
                bean.passWord = newPwd1;
                bean.captcha = captcha;
                presenter.restPwdAction(bean);
            }else {
                showHintDialog(getString(R.string.hint),getString(R.string.pwd2_not_agumention));
            }
        }else {
            showHintDialog(getString(R.string.hint),getString(R.string.regist_password_hint));
        }
    }

    private void showHintDialog(String title,String meg) {
        CommonDialog dialog = new CommonDialog(context,title,meg,1);
        dialog.show();
    }
    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void goLogin() {

    }

    @Override
    public void loginSuccess() {
        ToastMgr.show(R.string.rest_pwd_success);
        startActivity(LoginActivity.getLauncher(InputNewPwdActivity.this));
    }

    @Override
    public void loginFailure(int type) {

    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void registerSuccess() {

    }

    @Override
    public void getCodeSuccess() {

    }
}
