package com.bm.fusionworker.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.mine.PersonalCenterActivity;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import butterknife.Bind;

public class InspectioningActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.layout_work_list_details)
    LinearLayout layout_work_list_details;//工单详情
    @Bind(R.id.arrive_time_tv)
    TextView arrive_time_tv;//到达现场时间
    @Bind(R.id.onsite_person_tv)
    TextView onsite_person_tv;//现场人员
    @Bind(R.id.onsite_des_tv)
    TextView onsite_des_tv;//现场描述
    @Bind(R.id.sw_btn)
    Switch sw_btn;

    @Bind(R.id.layout_fault)
    LinearLayout layout_fault;

    @Bind(R.id.register_more_fault_tv)
    TextView register_more_fault_tv;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, InspectioningActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspectioning;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.inspection_list));
        sw_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout_fault.setVisibility(View.VISIBLE);
                } else {
                    layout_fault.setVisibility(View.GONE);
                }
            }
        });
        arrive_time_tv.setText(Tools.getCurrentTime());
        clicks();
    }

    private void clicks() {
        layout_work_list_details.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_work_list_details:
                break;
            default:
                break;
        }
    }
}
