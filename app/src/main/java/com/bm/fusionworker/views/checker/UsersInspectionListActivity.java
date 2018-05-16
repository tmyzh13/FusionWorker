package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
                ToastMgr.show("点击了=="+position);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private boolean isChecked = false;
    @Override
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.inspection_person_tv:
                if (isChecked){
                    ToastMgr.show("选择巡检人");
                }
                break;
            case R.id.inspection_time_tv:
                if (isChecked) {
                    ToastMgr.show("选择巡检时间");
                }
                break;
            case R.id.inspection_cycle_tv:
                if (isChecked) {
                    ToastMgr.show("选择巡检周期");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void itemChecked(int position) {
        isChecked = true;
    }
    @OnClick(R.id.delete_tv)
    public void deleteItem(){
        //TODO
    }
}
