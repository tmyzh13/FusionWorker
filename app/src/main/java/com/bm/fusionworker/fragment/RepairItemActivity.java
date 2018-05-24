package com.bm.fusionworker.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CoordinateConverter;
import com.amap.api.maps.MapView;
import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.model.beans.RepairItemInfoBean;
import com.bm.fusionworker.model.beans.RequestItemInfoBean;
import com.bm.fusionworker.model.interfaces.RepairItemInfoView;
import com.bm.fusionworker.presenter.RepairItemInfoPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.views.checker.GuildingActivity;
import com.bm.fusionworker.weights.NavBar;
import com.bm.fusionworker.weights.WrappingSlidingDrawer;
import com.corelibs.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class RepairItemActivity extends BaseActivity<RepairItemInfoView, RepairItemInfoPresenter> implements RepairItemInfoView {
    private Context context = RepairItemActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;

    @Bind(R.id.handle)
    LinearLayout handle;
    @Bind(R.id.status_tv)
    TextView status_tv;
    @Bind(R.id.action_tv)
    TextView action_tv;
    @Bind(R.id.action_tv2)
    TextView action_tv2;

    @Bind(R.id.title_tv)
    TextView title_tv;
    @Bind(R.id.address_tv)
    TextView address_tv;
    @Bind(R.id.distance_tv)
    TextView distance_tv;

    @Bind(R.id.work_orders_num_tv)
    TextView work_orders_num_tv;
    @Bind(R.id.create_time_tv)
    TextView create_time_tv;
    @Bind(R.id.urgent_level_tv)
    TextView urgent_level_tv;
    @Bind(R.id.assignor_tv)
    TextView assignor_tv;
    @Bind(R.id.pile_num_tv)
    TextView pile_num_tv;
    @Bind(R.id.parts_info_tv)
    TextView parts_info_tv;
    @Bind(R.id.repair_parts_tv)
    TextView repair_parts_tv;
    @Bind(R.id.repair_time_tv)
    TextView repair_time_tv;

    @Bind(R.id.map)
    MapView map;
    private AMap aMap;

    private RepairDataBean bean;
    private int status = 0;

    public static Intent getLauncher(Context context, RepairDataBean bean) {
        Intent intent = new Intent(context, RepairItemActivity.class);
        intent.putExtra("bean", bean);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_repair_item;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.repair));
        map.onCreate(savedInstanceState);
        aMap = map.getMap();
        bean = (RepairDataBean) getIntent().getSerializableExtra("bean");
        status = bean.status;
        //get data
        RequestItemInfoBean requestItemInfoBean = new RequestItemInfoBean();
        requestItemInfoBean.id = bean.id;
//        presenter.getRepairItemInfoAction(requestItemInfoBean);

        setHandleHeight();
        setStatus();
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

            action_tv.setText(R.string.continue_repair);
            action_tv.setTextColor(getResources().getColor(R.color.white));
            action_tv.setBackgroundResource(R.drawable.little_coner_blue_shape);

            action_tv2.setVisibility(View.VISIBLE);
            action_tv2.setText(R.string.continue_repair);
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
     * 导航
     */
    @OnClick(R.id.guild_iv)
    public void guild() {
        startActivity(GuildingActivity.getLauncher(context, 0, bean));
    }

    /**
     * 继续维修or接受工单
     */
    @OnClick(R.id.action_tv)
    public void repairOrAccept() {
        if (status == 0) {
            //接受工单
        } else if (status == 1) {
            //继续维修
            startActivity(RepairingActivity.getLauncher(RepairItemActivity.this));
        }
    }

    /**
     * 继续维修or接受工单
     */
    @OnClick(R.id.action_tv2)
    public void repairOrAccept2() {
        if (status == 0) {
            //接受工单
        } else if (status == 1) {
            //继续维修
            startActivity(RepairingActivity.getLauncher(RepairItemActivity.this));
        }
    }

    /**
     * 紧急程度
     */
    public void setUrgentLevel() {
        String urgent = "";
        if (urgent.equals(R.string.urgent_low)) {
            urgent_level_tv.setText(R.string.urgent_low);
            urgent_level_tv.setTextColor(getResources().getColor(R.color.green));
        } else if (urgent.equals(R.string.urgent_middle)) {
            urgent_level_tv.setText(R.string.urgent_middle);
            urgent_level_tv.setTextColor(getResources().getColor(R.color.yellow));
        } else if (urgent.equals(R.string.urgent_high)) {
            urgent_level_tv.setText(R.string.urgent_high);
            urgent_level_tv.setTextColor(getResources().getColor(R.color.red));
        }
    }

    @Override
    protected RepairItemInfoPresenter createPresenter() {
        return new RepairItemInfoPresenter();
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

    /**
     * 填充数据
     *
     * @param repairItemInfoBean
     */
    @Override
    public void renderData(RepairItemInfoBean repairItemInfoBean) {
        //TODO
        setUrgentLevel();
    }
}
