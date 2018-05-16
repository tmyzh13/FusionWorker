package com.bm.fusionworker.views.checker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.ScanChargeInfo;
import com.bm.fusionworker.model.interfaces.ChargeInputNumberView;
import com.bm.fusionworker.presenter.ChargeInputNumberPresenter;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.google.gson.Gson;


import butterknife.Bind;
import butterknife.OnClick;

public class ChargeInputNumberActivity extends BaseActivity<ChargeInputNumberView,ChargeInputNumberPresenter> implements ChargeInputNumberView {



    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.et_input_number)
    EditText etInputNumber;
    @Bind(R.id.sure)
    TextView sure;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_input_number;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
            navBar.setBackground(getResources().getDrawable(R.drawable.nan_bg));
        } else {
            navBar.setColor(getResources().getColor(R.color.app_blue));
        }
        navBar.setNavTitle(this.getString(R.string.charge));

    }

    @Override
    protected ChargeInputNumberPresenter createPresenter() {
        return new ChargeInputNumberPresenter();
    }

    @OnClick(R.id.sure)
    public void onViewClicked() {
        String str = etInputNumber.getText().toString();
        long number = 0;
        try {
            number = Long.parseLong(str);
        } catch (Exception e){
            showToast(getString(R.string.hint_right_code));
            return;
        }

        showLoading();
        presenter.getData(number);

//        testt();
    }


    @Override
    public void goLogin() {}

    @Override
    public void onGetDataSuccess(ScanChargeInfo info) {
        hideLoading();

        if(info!= null) {
            Gson gson = new Gson();
            String data = gson.toJson(info);
            Intent intent = new Intent(ChargeInputNumberActivity.this,ChargeOrderDetailsActivity.class);
            intent.putExtra("data",data);
            startActivity(intent);
        } else {
            showToast(getString(R.string.no_user_info));
        }

        finish();
    }

    @Override
    public void onGetDataFail(String msg) {
        hideLoading();

    }
}
