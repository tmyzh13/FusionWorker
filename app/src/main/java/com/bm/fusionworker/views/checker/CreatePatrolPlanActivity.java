package com.bm.fusionworker.views.checker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.PickerView;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

public class CreatePatrolPlanActivity extends BaseActivity {

    @Bind(R.id.cancel_iv)
    ImageView cancel_iv;
    @Bind(R.id.hour_pick)
    PickerView hour_pick;
    @Bind(R.id.second_pick)
    PickerView second_pick;

    private List<String> hours = new ArrayList<>();
    private List<String> seconds = new ArrayList<>();

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, CreatePatrolPlanActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_create_patrol_plan;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTimePicker();
    }

    private void initTimePicker() {
        for (int i = 0; i < 24; i++) {
            hours.add(i < 10 ? "0" + i : "" + i);
        }
        for (int i = 0; i < 60; i++) {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        hour_pick.setData(hours);
        second_pick.setData(seconds);

        hour_pick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });
        second_pick.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {

            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
