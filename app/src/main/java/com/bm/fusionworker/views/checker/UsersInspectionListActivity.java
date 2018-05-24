package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.adapter.UserInspectionListAdapter;
import com.bm.fusionworker.model.beans.UserInspectionListItemBean;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;
import com.corelibs.utils.ToastMgr;
import com.corelibs.views.ptr.layout.PtrAutoLoadMoreLayout;
import com.corelibs.views.ptr.loadmore.widget.AutoLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class UsersInspectionListActivity extends BaseActivity implements UserInspectionListAdapter.InnerItemOnClickListener {

    private Context context = UsersInspectionListActivity.this;
    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.tv_appointment)
    TextView tv_appointment;
    @Bind(R.id.ptrLayout)
    PtrAutoLoadMoreLayout<AutoLoadMoreListView> ptr;
    @Bind(R.id.lv_list)
    AutoLoadMoreListView listView;
    @Bind(R.id.delete_tv)
    TextView delete_tv;

    private List<UserInspectionListItemBean> list = new ArrayList<>();
    private UserInspectionListAdapter adapter;

    private boolean isEdite = true;
    private RadioButton radioButton;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //在此处设置此方法后，viewClick里面的view.isSelected()才会生效
                    listView.getChildAt(msg.arg1).setSelected(true);
                    break;
                default:
                    break;
            }
            return true;
        }
    });

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, UsersInspectionListActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_users_inspection_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setNavTitle(getString(R.string.inspection_list));
        navBar.setColorRes(R.color.app_blue);
        tv_appointment.setVisibility(View.VISIBLE);
        tv_appointment.setText(getString(R.string.edit));
        adapter = new UserInspectionListAdapter(context);
        listView.setAdapter(adapter);
        adapter.setListener(this);
        ptr.enableLoading();
        setData();
        if (isEdite) {

        }
    }

    private void setData() {
        for (int i = 0; i < 4; i++) {
            UserInspectionListItemBean bean = new UserInspectionListItemBean();
            bean.location = getString(R.string.repair_bean_address) + i;
            bean.inspector = "李国强";
            bean.inspectionTime = "2018.05.15";
            bean.inspectionCycle = "7天/次";
            list.add(bean);
        }
        adapter.replaceAll(list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastMgr.show("点击了" + position);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void viewClick(View view) {

        switch (view.getId()) {
            case R.id.inspection_person_tv:
                if (view.isSelected()) {
                    ToastMgr.show("选择巡检人");
                }
                break;
            case R.id.inspection_time_tv:
                if (view.isSelected()) {
                    ToastMgr.show("选择巡检时间");
                }
                break;
            case R.id.inspection_cycle_tv:
                if (view.isSelected()) {
                    ToastMgr.show("选择巡检周期");
                }
                break;
            case R.id.rl_title:
                ToastMgr.show("进入详情");
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.tv_appointment)
    public void down() {
        if (isEdite) {
            tv_appointment.setText(getString(R.string.down));
            isEdite = false;
        } else {//文字是完成的时候点击
            tv_appointment.setText(getString(R.string.edit));
            isEdite = true;
            //点击完成，上传信息
            //TODO
        }
    }

    private int selectedPosition = 0;//当前选中的位置
    int lastPosition = 0;//点击不同item的button，上一次选中位置

    @Override
    public void itemChecked(int position, View view) {
        delete_tv.setVisibility(View.VISIBLE);
        lastPosition = selectedPosition;
        selectedPosition = position;
        Message message = new Message();
        message.what = 0;
        message.arg1 = selectedPosition;
        handler.sendMessage(message);

        if (selectedPosition != lastPosition) {//切换了item
            radioButton = listView.getChildAt(lastPosition).findViewById(R.id.rb);
            radioButton.performClick();
            RadioButton radioButton2 = (RadioButton) view;
            radioButton2.performClick();
        }
    }

    @OnClick(R.id.delete_tv)
    public void deleteItem() {
        if (listView.getChildAt(selectedPosition).isSelected()) {
            adapter.remove(selectedPosition);
            RadioButton radioButton = listView.getChildAt(selectedPosition).findViewById(R.id.rb);
            radioButton.performClick();
            radioButton.setChecked(false);
            adapter.notifyDataSetChanged();
        }
    }
}
