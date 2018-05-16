package com.bm.fusionworker.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.model.beans.RepairDataBean;
import com.bm.fusionworker.views.checker.GuildingActivity;
import com.bm.fusionworker.weights.NavBar;
import com.bm.fusionworker.weights.WrappingSlidingDrawer;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.OnClick;

public class RepairItemActivity extends BaseActivity {
    private Context context = RepairItemActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.sliding_drawer)
    WrappingSlidingDrawer drawer;
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
        bean = (RepairDataBean) getIntent().getSerializableExtra("bean");
        status = bean.status;
        if (status == 0) {//未确认
            status_tv.setText(R.string.status_unconfirmed);
            status_tv.setBackgroundResource(R.drawable.semicircle_red_shape_bg);

            action_tv.setVisibility(View.VISIBLE);
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

            action_tv.setVisibility(View.VISIBLE);
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

        drawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                action_tv.setVisibility(View.GONE);
            }
        });
        drawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                if (status != 2) {
                    action_tv.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * 导航
     */
    @OnClick(R.id.guild_iv)
    public void guild() {
        startActivity(GuildingActivity.getLauncher(context,0));
    }

    /**
     * 继续维修or接受工单
     */
    @OnClick(R.id.action_tv)
    public void repairOrAccept() {
        drawer.close();
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

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
