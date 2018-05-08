package com.bm.fusionworker.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.interfaces.LoginView;
import com.bm.fusionworker.presenter.LoginPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;
import butterknife.OnClick;

public class MessageLoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView {

    public static final String TAG = MessageLoginActivity.class.getSimpleName();
    private Context context = MessageLoginActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.phone_et)
    EditText phoneNumberEt;
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.get_code_tv)
    TextView getCodeTv;

    private String phoneNumber;
    private String code;

    public static Intent getLaunch(Context context) {
        Intent intent = new Intent(context, MessageLoginActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getResources().getString(R.string.login_type_message));
        navBar.setColor(getResources().getColor(R.color.app_blue));
    }

    @OnClick(R.id.get_code_tv)
    void getCode() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(getCodeTv, 60000, 1000);
            timer.start();
        } else {
            ToastMgr.show(R.string.input_correct_phone);
            return;
        }
    }

    @OnClick(R.id.login_tv)
    void login() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (!Tools.isChinaPhoneLegal(phoneNumber)) {
            ToastMgr.show(R.string.input_correct_phone);
            return;
        }
        code = codeEt.getText().toString().trim();
        if (TextUtils.isEmpty(code)) {
            ToastMgr.show(R.string.hint_input_code);
            return;
        }
        if (code.length() < 6) {
            ToastMgr.show(R.string.hint_input_code_6);
            return;
        }
        //1:验证码 登录
        presenter.loginAction(1, phoneNumber, "", code);
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void loginSuccess() {
        Log.e(TAG, "----登录成功！！！");
        //todo
    }

    @Override
    public void registerSuccess() {
    }

    @Override
    public void getCodeSuccess() {
    }

    @Override
    public void loginFailure(int type) {
        ToastMgr.show(R.string.login_error);
    }

    @Override
    public void checkCodeSuccess() {

    }


    public static class MyCountDownTimer extends CountDownTimer {
        TextView view;

        public MyCountDownTimer(TextView v, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.view = v;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            view.setClickable(false);
            view.setText(millisUntilFinished / 1000 + "s");
        }

        @Override
        public void onFinish() {
            view.setClickable(true);
            view.setText(R.string.action_get_code_again);
        }
    }
}
