package com.bm.fusionworker;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.fusionworker.fragment.InspectionFragment;
import com.bm.fusionworker.fragment.RepairFragment;
import com.bm.fusionworker.views.mine.PersonalCenterActivity;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.common.AppManager;

import butterknife.Bind;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Context context = MainActivity.this;
    public static final int CHOSED_REPAIR = 0;
    public static final int CHOSED_INSPECTION = 1;

    @Bind(R.id.account_iv)
    ImageView account_iv;
    @Bind(R.id.repair_tv)
    TextView repair_tv;
    @Bind(R.id.line1)
    View line1;
    @Bind(R.id.inspection_tv)
    TextView inspection_tv;
    @Bind(R.id.line2)
    View line2;

    private RepairFragment repairFragment;
    private InspectionFragment inspectionFragment;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case CHOSED_REPAIR:
                    chosedRepair();
                    break;
                case CHOSED_INSPECTION:
                    chosedInspection();
                    break;
            }
            return true;
        }
    });

    private void chosedInspection() {
        inspection_tv.setTextColor(getResources().getColor(R.color.white));
        repair_tv.setTextColor(getResources().getColor(R.color.light_gray));
        line2.setVisibility(View.VISIBLE);
        line1.setVisibility(View.INVISIBLE);
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        chosedRepair();
        repairFragment = new RepairFragment();
        inspectionFragment = new InspectionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.content,repairFragment).commit();
        clicks();
    }

    private void chosedRepair() {
        repair_tv.setTextColor(getResources().getColor(R.color.white));
        inspection_tv.setTextColor(getResources().getColor(R.color.light_gray));
        line1.setVisibility(View.VISIBLE);
        line2.setVisibility(View.INVISIBLE);
    }

    private void clicks() {
        account_iv.setOnClickListener(this);
        repair_tv.setOnClickListener(this);
        inspection_tv.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.account_iv:
                startActivity(PersonalCenterActivity.getLauncher(context));
                break;
            case R.id.repair_tv:
                handler.sendEmptyMessage(CHOSED_REPAIR);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(inspectionFragment);
                if (!repairFragment.isAdded()){
                    transaction.add(R.id.content,repairFragment).show(repairFragment);
                }else {
                    transaction.show(repairFragment);
                }
                transaction.commit();
                break;
            case R.id.inspection_tv:
                handler.sendEmptyMessage(CHOSED_INSPECTION);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(repairFragment);
                if (!inspectionFragment.isAdded()){
                    ft.add(R.id.content,inspectionFragment).show(inspectionFragment);
                }else {
                    ft.show(inspectionFragment);
                }
                ft.commit();
                break;
            default:
                break;
        }
    }

    /**
     * 双击两次返回键退出应用
     */
    private boolean isBackPressed = false;//判断是否已经点击过一次回退键

    private void doublePressBackToast() {
        if (!isBackPressed) {
            isBackPressed = true;
            showToast(getString(R.string.double_press_for_exit));
        } else {
            AppManager.getAppManager().finishAllActivity();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isBackPressed = false;
            }
        }, 2000);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            doublePressBackToast();
            return true;
        } else {
            return super.onKeyUp(keyCode, event);
        }
    }
}
