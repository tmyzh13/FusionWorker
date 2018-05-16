package com.bm.fusionworker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bm.fusionworker.fragment.InspectioningActivity;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.checker.CreatePatrolPlanActivity;
import com.bm.fusionworker.views.checker.NotAcceptanceActivity;
import com.bm.fusionworker.views.checker.PileStatusActivity;
import com.bm.fusionworker.views.mine.PersonalCenterActivity;
import com.bm.fusionworker.weights.CircleProgressView;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.text.DecimalFormat;

import butterknife.Bind;

public class MainCheckerActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.iv_back)
    ImageView iv_back;
    @Bind(R.id.iv_seach)
    ImageView iv_seach;
    @Bind(R.id.iv_add)
    ImageView iv_add;

    @Bind(R.id.progress)
    CircleProgressView progress;
    @Bind(R.id.repair_completion_degree_tv)
    TextView repair_completion_degree_tv;
    @Bind(R.id.has_repaired_count_tv)
    TextView has_repaired_count_tv;
    @Bind(R.id.not_repaired_count_tv)
    TextView not_repaired_count_tv;

    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptr;
    @Bind(R.id.list_view)
    AutoLoadMoreListView listView;
    @Bind(R.id.go_map_iv)
    ImageView go_map_iv;
    private int has_repaired_count = 4;
    private int not_repaired_count = 10;

    public static Intent getLauncher(Context context){
        Intent intent = new Intent(context,MainCheckerActivity.class);
        return intent;
    }
    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main_checker;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setNavBar();
        clicks();
        setProgress();

    }

    private void setProgress() {
        has_repaired_count_tv.setText(has_repaired_count + "");
        not_repaired_count_tv.setText(not_repaired_count + "");
        progress.setMax(100);
        Double d = Tools.getDouble(has_repaired_count, (has_repaired_count + not_repaired_count), 2);
        progress.setProgress((long) (d * 100));
        repair_completion_degree_tv.setText((long) (d * 100) + "%");
        progress.setType(0);
        progress.startAnimation(0, (int) (d * 100), 1000,0);
    }


    private void clicks() {
        iv_back.setOnClickListener(this);
        iv_add.setOnClickListener(this);
        iv_seach.setOnClickListener(this);
        go_map_iv.setOnClickListener(this);
    }

    private void setNavBar() {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.worker_list));
        iv_back.setImageResource(R.drawable.ic_delete_photo);
        iv_back.setVisibility(View.VISIBLE);
        iv_seach.setImageResource(R.drawable.ic_delete_photo);
        iv_seach.setVisibility(View.VISIBLE);
        iv_add.setImageResource(R.drawable.ic_delete_photo);
        iv_add.setVisibility(View.VISIBLE);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                startActivity(CreatePatrolPlanActivity.getLauncher(MainCheckerActivity.this));
                break;
            case R.id.iv_add:
                startActivity(PersonalCenterActivity.getLauncher(MainCheckerActivity.this));
                break;
            case R.id.iv_seach:
                ToastMgr.show("充电");
                startActivity(NotAcceptanceActivity.getLauncher(MainCheckerActivity.this));
                break;
            case R.id.go_map_iv:
                startActivity(PileStatusActivity.getLaunher(MainCheckerActivity.this));
                break;
            default:
                break;
        }
    }
}
