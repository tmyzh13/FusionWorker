package com.bm.fusionworker.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.InspectionItemInfoBean;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.interfaces.InspectionItemInfoView;
import com.bm.fusionworker.presenter.InspectionItemInfoPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.checker.GuildingActivity;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class InspectionItemActivity extends BaseActivity<InspectionItemInfoView,InspectionItemInfoPresenter>implements InspectionItemInfoView{

    private Context context = InspectionItemActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.map)
    MapView map;
    @Bind(R.id.handle)
    LinearLayout handle;
    @Bind(R.id.status_tv)
    TextView status_tv;
    @Bind(R.id.action_tv)
    TextView action_tv;
    @Bind(R.id.action_tv2)
    TextView action_tv2;

    private AMap aMap;
    private int status = 0;
    private RepairDataBean bean;

    public static Intent getLauncher(Context context, RepairDataBean bean) {
        Intent intent = new Intent(context, InspectionItemActivity.class);
        intent.putExtra("bean", bean);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_inspection_item;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.inspection));
        map.onCreate(savedInstanceState);
        aMap = map.getMap();
        bean = (RepairDataBean) getIntent().getSerializableExtra("bean");
        status = bean.status;
        setHandleHeight();
        setStatus();

    }

    private void setStatus() {
        if (status == 0) {//未确认
            status_tv.setText(R.string.status_unconfirmed);
            status_tv.setBackgroundResource(R.drawable.semicircle_red_shape_bg);

            action_tv.setText(R.string.accept_work_order);
            action_tv.setTextColor(getResources().getColor(R.color.btn_blue));
            action_tv.setBackgroundResource(R.drawable.blue_stroke_bg);

            action_tv2.setVisibility(View.VISIBLE);
            action_tv2.setText(R.string.accept_work_order);
            action_tv2.setTextColor(getResources().getColor(R.color.btn_blue));
            action_tv2.setBackgroundResource(R.drawable.blue_stroke_bg);
        } else if (status == 1) {//进行中
            status_tv.setText(R.string.status_ongoing);
            status_tv.setBackgroundResource(R.drawable.semicircle_yellow_shape_bg);

            action_tv.setText(R.string.start_inspection);
            action_tv.setTextColor(getResources().getColor(R.color.white));
            action_tv.setBackgroundResource(R.drawable.little_coner_blue_shape);

            action_tv2.setVisibility(View.VISIBLE);
            action_tv2.setText(R.string.start_inspection);
            action_tv2.setTextColor(getResources().getColor(R.color.white));
            action_tv2.setBackgroundResource(R.drawable.little_coner_blue_shape);
        } else {//未验收
            status_tv.setText(R.string.status_not_acceptance);
            status_tv.setBackgroundResource(R.drawable.semicircle_green_shape_bg);

            action_tv.setVisibility(View.GONE);
            action_tv2.setVisibility(View.GONE);
        }
    }

    /**
     * 设置上拉布局把手高度
     */
    private void setHandleHeight() {
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.cdl);
        View bottomSheet = coordinatorLayout.findViewById(R.id.bottom_view);
        final BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);

        ViewTreeObserver vto = handle.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                handle.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                behavior.setPeekHeight(handle.getMeasuredHeight() + Tools.dip2px(context, 30));
            }
        });

        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                //newState 1:滑动 2：自动滑动 3：展开 4：关闭
                if (newState == 1 || newState == 3) {
                    action_tv.setVisibility(View.GONE);
                    if (status==2){
                        action_tv2.setVisibility(View.GONE);
                    }else {
                        action_tv2.setVisibility(View.VISIBLE);
                    }
                } else if (newState == 4) {
                    action_tv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        });
    }
    /**
     * 导航
     */
    @OnClick(R.id.guild_iv)
    public void guild() {
        startActivity(GuildingActivity.getLauncher(InspectionItemActivity.this,1,bean));
    }

    /**
     * 开始巡检or接受工单
     */
    @OnClick(R.id.action_tv)
    public void inspectionOrAccept() {
        if (status == 0) {
            //接受工单
        } else if (status == 1) {
            //开始巡检
            startActivity(InspectioningActivity.getLauncher(InspectionItemActivity.this));
        }
    }

    /**
     * 开始巡检or接受工单
     */
    @OnClick(R.id.action_tv2)
    public void repairOrAccept2() {
        if (status == 0) {
            //接受工单
        } else if (status == 1) {
            //开始巡检
            startActivity(InspectioningActivity.getLauncher(InspectionItemActivity.this));
        }
    }

    @Override
    protected InspectionItemInfoPresenter createPresenter() {
        return new InspectionItemInfoPresenter();
    }
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
    }

    @Override
    public void renderData(InspectionItemInfoBean inspectionItemInfoBean) {

    }
}
