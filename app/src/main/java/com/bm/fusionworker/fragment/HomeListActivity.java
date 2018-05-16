package com.bm.fusionworker.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.views.mine.PersonalCenterActivity;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;

public class HomeListActivity extends BaseActivity {

    private Context context = HomeListActivity.this;

    @Bind(R.id.repair_tv)
    TextView repair_tv;
    @Bind(R.id.inspection_tv)
    TextView inspection_tv;

    @Bind(R.id.content)
    FrameLayout content;
    private RepairListFragment repairListFragment;
    private InspectionListFragment inspectionListFragment;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, HomeListActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        repair_tv.setTextColor(getResources().getColor(R.color.btn_blue));
        inspection_tv.setTextColor(getResources().getColor(R.color.text_main));
        repair_tv.setBackgroundResource(R.drawable.repair_shape_selected_bg);
        inspection_tv.setBackgroundResource(R.drawable.inspection_shape_normal_bg);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        repairListFragment = new RepairListFragment();
        inspectionListFragment = new InspectionListFragment();
        ft.add(R.id.content, repairListFragment).commit();
    }

    @OnClick(R.id.repair_tv)
    public void chosedRepair() {
        repair_tv.setTextColor(getResources().getColor(R.color.btn_blue));
        inspection_tv.setTextColor(getResources().getColor(R.color.text_main));
        repair_tv.setBackgroundResource(R.drawable.repair_shape_selected_bg);
        inspection_tv.setBackgroundResource(R.drawable.inspection_shape_normal_bg);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(inspectionListFragment);
        if (!repairListFragment.isAdded()) {
            ft.add(R.id.content, repairListFragment).show(repairListFragment);
        } else {
            ft.show(repairListFragment);
        }
        ft.commit();
    }

    @OnClick(R.id.inspection_tv)
    public void chosedInspection() {
        inspection_tv.setTextColor(getResources().getColor(R.color.btn_blue));
        repair_tv.setTextColor(getResources().getColor(R.color.text_main));
        repair_tv.setBackgroundResource(R.drawable.repair_shape_normal_bg);
        inspection_tv.setBackgroundResource(R.drawable.inspection_shape_selected_bg);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(repairListFragment);
        if (!inspectionListFragment.isAdded()) {
            ft.add(R.id.content, inspectionListFragment).show(inspectionListFragment);
        } else {
            ft.show(inspectionListFragment);
        }
        ft.commit();
    }

    @OnClick(R.id.account_iv)
    public void goPersonalCenterActivity() {
        startActivity(PersonalCenterActivity.getLauncher(context));
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

}
