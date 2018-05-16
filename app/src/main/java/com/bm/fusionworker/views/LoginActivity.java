package com.bm.fusionworker.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.constants.Constant;
import com.bm.fusionworker.model.interfaces.LoginView;
import com.bm.fusionworker.presenter.LoginPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.mine.UserInfoActivity;
import com.bm.fusionworker.weights.CommonDialog;
import com.corelibs.base.BaseActivity;
import com.corelibs.utils.PreferencesHelper;
import com.corelibs.utils.ToastMgr;
import com.corelibs.utils.rxbus.RxBus;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginView, LoginPresenter> implements LoginView, View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private Context context = LoginActivity.this;

    @Bind(R.id.register)
    TextView register;
    @Bind(R.id.login)
    TextView login;

    @Bind(R.id.phone_et)
    EditText phoneNumberEt;
    @Bind(R.id.pwd_et)
    EditText pwdEt;
    @Bind(R.id.code_rl)
    RelativeLayout typeCodeLl;//验证码登录ui
    @Bind(R.id.code_et)
    EditText codeEt;
    @Bind(R.id.get_code_tv)
    TextView getCodeTv;

    @Bind(R.id.login_tv)
    TextView loginTv;
    @Bind(R.id.forget_pwd_tv)
    TextView forgetPwdTv;
    @Bind(R.id.pwd_ic_del)
    ImageView pwd_ic_del;
    @Bind(R.id.pwd_ic_see)
    ImageView pwd_ic_see;

    @Bind(R.id.cb_appointment)
    CheckBox cb_appointment;

    @Bind(R.id.worker_login)
    LinearLayout worker_login;
    @Bind(R.id.message_login)
    LinearLayout message_login;

    private String phoneNumber;
    private String pwd;
    private String code;
    private PopupWindow popupWindow;
    private TextView phoneTypeTv;
    private TextView workerTypeTv;
    private TextView codeTypeTv;
    private int type = 1;//手机号+密码登录

    private boolean isRegister = false;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1://手机号+密码登录UI
//                    login.setTextColor(getResources().getColor(R.color.white));
//                    register.setTextColor(getResources().getColor(R.color.light_gray));
                    typeCodeLl.setVisibility(View.GONE);
                    forgetPwdTv.setVisibility(View.VISIBLE);
                    loginTv.setText(getText(R.string.login));
                    cb_appointment.setVisibility(View.GONE);
                    isRegister = false;
                    break;
                case 4://忘记密码
                    startActivity(ForgetPwdActivity.getLaunch(context));
                    break;
                case 5://显示注册UI
//                    register.setTextColor(getResources().getColor(R.color.white));
//                    login.setTextColor(getResources().getColor(R.color.light_gray));
                    typeCodeLl.setVisibility(View.VISIBLE);
                    forgetPwdTv.setVisibility(View.GONE);
                    loginTv.setText(getText(R.string.regist));
                    cb_appointment.setVisibility(View.VISIBLE);
                    isRegister = true;
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 用户注册
     */
    private boolean registerUser() {

        if (getUserInput()) {
            code = codeEt.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                showHintDialog(getString(R.string.hint), getString(R.string.hint_input_code));
                return false;
            }
            if (!cb_appointment.isChecked()) {
                showHintDialog(getString(R.string.hint), getString(R.string.choice_appointment));
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
//        login.setTextColor(getResources().getColor(R.color.white));
//        register.setTextColor(getResources().getColor(R.color.light_gray));
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        forgetPwdTv.setOnClickListener(this);
        loginTv.setOnClickListener(this);
        getCodeTv.setOnClickListener(this);

        worker_login.setOnClickListener(this);
        message_login.setOnClickListener(this);

        if (null != PreferencesHelper.getData("phone")) {
            phoneNumber = PreferencesHelper.getData("phone");
            phoneNumberEt.setText(phoneNumber);
            phoneNumberEt.setSelection(phoneNumber.length());
        }
        final PopupWindow popupWindow;
        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pwd_hint, null);
        popupWindow.setContentView(view);

        pwdEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
//                    popupWindow.showAsDropDown(v);
                    ToastMgr.show(R.string.regist_password_hint);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            popupWindow.dismiss();
                        }
                    }, 1000);
                }
            }
        });
        pwdEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.toString().isEmpty()) {
                    if (pwd_ic_del.getVisibility() != View.GONE) {
                        pwd_ic_del.setVisibility(View.GONE);
                        pwd_ic_see.setVisibility(View.GONE);
                    }
                } else {
                    if (pwd_ic_del.getVisibility() != View.VISIBLE) {
                        pwd_ic_del.setVisibility(View.VISIBLE);
                        pwd_ic_see.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @OnClick(R.id.pwd_ic_del)
    void resetPwdEditContent() {
        pwdEt.setText("");
        pwd_ic_del.setVisibility(View.GONE);
        pwd_ic_see.setVisibility(View.GONE);
        pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    @OnClick(R.id.pwd_ic_see)
    void showPwdEditContent() {
        if (InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == pwdEt.getInputType()) {
            pwdEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            pwdEt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    public void getCode() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        if (Tools.isChinaPhoneLegal(phoneNumber)) {
            MyCountDownTimer timer = new MyCountDownTimer(getCodeTv, 60000, 1000);
            timer.start();
        } else {
            showHintDialog(getString(R.string.hint), getString(R.string.input_correct_phone));
            return;
        }
        //1：获取注册短信验证码
        presenter.getCodeAction(1, phoneNumber);

    }

    private void showHintDialog(String title, String meg) {
        CommonDialog dialog = new CommonDialog(context, title, meg, 1);
        dialog.show();
    }

    private boolean getUserInput() {
        phoneNumber = phoneNumberEt.getText().toString().trim();
        pwd = pwdEt.getText().toString();
        if (pwd != null && pwd.contains(" ")) {
//            showHintDialog(getString(R.string.hint), getString(R.string.pwd_cannot_contain_space));
            ToastMgr.show(getString(R.string.pwd_cannot_contain_space));
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber) || !Tools.isChinaPhoneLegal(phoneNumber)) {
//            showHintDialog(getString(R.string.hint), getString(R.string.input_correct_phone));
            ToastMgr.show(getString(R.string.input_correct_phone));
            return false;
        } else if (TextUtils.isEmpty(pwd)) {
//            showHintDialog(getString(R.string.hint), getString(R.string.hint_input_password));
            ToastMgr.show(getString(R.string.hint_input_password));
            return false;
        } else if (!Tools.isPwdRight(pwd)) {
//            showHintDialog(getString(R.string.hint), getString(R.string.regist_password_hint));
            ToastMgr.show(getString(R.string.regist_password_hint));
            return false;
        }
        return true;
    }

    @Override
    public void loginSuccess() {
        RxBus.getDefault().send(new Object(), Constant.REFRESH_HOME_STATUE);
//        startActivity(MainActivity.getLauncher(context));
        finish();
    }

    @Override
    public void loginFailure(int type) {
        Log.e(TAG, "----loginFailure:type=" + type);
        if (type == Constant.LOGIN_ACOUNT_PWD_FAILURE) {
//            showHintDialog(getString(R.string.hint), getString(R.string.account_pwd_fault));
            ToastMgr.show(getString(R.string.account_pwd_fault));
        } else if (type == Constant.PHONE_HAS_USED) {
//            showHintDialog(getString(R.string.hint), getString(R.string.phone_has_used));
            ToastMgr.show(getString(R.string.phone_has_used));
        }
    }

    @Override
    public void checkCodeSuccess() {

    }

    @Override
    public void registerSuccess() {
        ToastMgr.show(getString(R.string.register_success));
        handler.sendEmptyMessage(1);
    }

    @Override
    public void getCodeSuccess() {
        Log.e(TAG, "----getCodeSuccess:");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Log.e(TAG, "----onClick:密码登录");
                handler.sendEmptyMessage(1);
                break;
            case R.id.register:
                handler.sendEmptyMessage(5);
                break;
            case R.id.forget_pwd_tv:
                handler.sendEmptyMessage(4);
                break;
            case R.id.get_code_tv:
                getCode();
                break;
            case R.id.login_tv:
                if (!isRegister) {
                    //手机号密码登录
                    if (TextUtils.isEmpty(phoneNumberEt.getText().toString().trim()) || !Tools.isChinaPhoneLegal(phoneNumberEt.getText().toString().trim())) {
//                            showHintDialog(getString(R.string.hint), getString(R.string.input_correct_phone));
                        ToastMgr.show(R.string.input_correct_phone);
                    } else if (TextUtils.isEmpty(pwdEt.getText().toString().trim())) {
//                            showHintDialog(getString(R.string.hint), getString(R.string.hint_input_password));
                        ToastMgr.show(R.string.hint_input_password);
                    } else {
                        presenter.loginAction(0, phoneNumberEt.getText().toString().trim(), pwdEt.getText().toString().trim(), code);
                    }
                } else {
                    //调用注册接口
                    if (registerUser()) {
                        presenter.registerAction(phoneNumber, pwd, code);
                    }
                }
                break;
            case R.id.worker_login:
                //todo
                break;
            case R.id.message_login:
                startActivity(MessageLoginActivity.getLaunch(context));
                break;
            default:
                break;
        }
    }

    /**
     * 登录方式选择
     *
     * @param v
     */
    private void showPopuWindow(View v) {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(context);
        }

        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.title_popuwindow, null);
        phoneTypeTv = view.findViewById(R.id.phone_login_tv);
        workerTypeTv = view.findViewById(R.id.worker_login_tv);
        codeTypeTv = view.findViewById(R.id.code_login_tv);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(v);
        }

//        getLoginType();
    }

    private void getLoginType() {
        phoneTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 1;
                handler.sendEmptyMessage(type);

                popupWindow.dismiss();
            }
        });
        workerTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 2;
                handler.sendEmptyMessage(type);
                popupWindow.dismiss();
            }
        });
        codeTypeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = 3;
                handler.sendEmptyMessage(type);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void goLogin() {

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
