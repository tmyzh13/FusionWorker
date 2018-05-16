package com.bm.fusionworker.views.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.views.checker.UsersInspectionListActivity;
import com.bm.fusionworker.views.setting.SettingsActivity;
import com.bm.fusionworker.weights.CircleImageView;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    public static final String tag = PersonalCenterActivity.class.getSimpleName();
    private Context context = PersonalCenterActivity.this;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.user_head_iv)
    CircleImageView user_head_iv;
    @Bind(R.id.user_name_tv)
    TextView user_name_tv;
    @Bind(R.id.user_email_tv)
    TextView user_email_tv;

    //验收
    @Bind(R.id.checker_ll)
    LinearLayout checker_ll;
    @Bind(R.id.inspector_rl)
    RelativeLayout inspector_rl;
    @Bind(R.id.waite_accept_check_rl)
    RelativeLayout waite_accept_check_rl;
    @Bind(R.id.waite_accept_check_count)
    TextView waite_accept_check_count;

    @Bind(R.id.already_accept_check_rl)
    RelativeLayout already_accept_check_rl;
    @Bind(R.id.already_accept_check_count)
    TextView already_accept_check_count;

    //巡检
    @Bind(R.id.repairer_ll)
    LinearLayout repairer_ll;
    @Bind(R.id.completed_rl)
    RelativeLayout completed_rl;

    //未完成工单
    @Bind(R.id.un_completed_rl)
    RelativeLayout un_completed_rl;
    @Bind(R.id.un_completed_count)
    TextView un_completed_count;
    //退单记录
    @Bind(R.id.refund_history_rl)
    RelativeLayout refund_history_rl;
    @Bind(R.id.refund_history_count)
    TextView refund_history_count;

    @Bind(R.id.settings_rl)
    RelativeLayout settings_rl;
    @Bind(R.id.log_out_rl)
    RelativeLayout log_out_rl;

    private boolean isChecker = true;//true:验收,false:巡检人员

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, PersonalCenterActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_center;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.dark_blue);
        clicks();
        if (isChecker) {
            checker_ll.setVisibility(View.VISIBLE);
            repairer_ll.setVisibility(View.GONE);
        } else {
            repairer_ll.setVisibility(View.VISIBLE);
            checker_ll.setVisibility(View.GONE);
        }
    }

    private void clicks() {
        user_head_iv.setOnClickListener(this);
        inspector_rl.setOnClickListener(this);
        waite_accept_check_rl.setOnClickListener(this);
        already_accept_check_rl.setOnClickListener(this);

        completed_rl.setOnClickListener(this);
        un_completed_rl.setOnClickListener(this);
        refund_history_rl.setOnClickListener(this);

        settings_rl.setOnClickListener(this);
        log_out_rl.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_head_iv:
                startActivity(UserInfoActivity.getLauncher(context));
                break;
            case R.id.inspector_rl:
                startActivity(UsersInspectionListActivity.getLauncher(PersonalCenterActivity.this));
                break;
            case R.id.waite_accept_check_rl:
                break;
            case R.id.already_accept_check_rl:
                break;
            case R.id.completed_rl:
                break;
            case R.id.un_completed_rl:
                break;
            case R.id.refund_history_rl:
                startActivity(RefundHistoryActivity.getLauncher(PersonalCenterActivity.this));
                break;
            case R.id.settings_rl:
                startActivity(SettingsActivity.getLauncher(context));
                break;
            case R.id.log_out_rl:
                break;
            default:
                break;
        }
    }
}
