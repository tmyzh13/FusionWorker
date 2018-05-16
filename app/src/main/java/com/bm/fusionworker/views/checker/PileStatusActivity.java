package com.bm.fusionworker.views.checker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bm.fusionworker.MainCheckerActivity;
import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;
import retrofit2.http.POST;

public class PileStatusActivity extends BaseActivity {
    private Context context = PileStatusActivity.this;
    public static final int STATUS_NORMAL = 1;
    public static final int STATUS_USING = 2;
    public static final int STATUS_STOPPED = 3;

    @Bind(R.id.nav)
    NavBar navBar;
    @Bind(R.id.tv_title)
    TextView tv_title;
    @Bind(R.id.go_work_list_iv)
    ImageView go_work_list_iv;

    private PopupWindow popupWindow;
    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case STATUS_NORMAL:
                    break;
                case STATUS_USING:
                    break;
                case STATUS_STOPPED:
                    break;
                default:
                    break;
            }

            return true;
        }
    });

    public static Intent getLaunher(Context context) {
        Intent intent = new Intent(context, PileStatusActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pile_status;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        navBar.setColorRes(R.color.app_blue);
        navBar.setNavTitle(getString(R.string.pile_status));
        navBar.hideBack();
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.tv_title)
    public void choseStatus() {
        View view = LayoutInflater.from(context).inflate(R.layout.popupwindow_layout, null);
        popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        final Drawable dr1 = context.getResources().getDrawable(R.mipmap.expand);
        if (!popupWindow.isShowing()) {
            popupWindow.showAsDropDown(navBar);
            tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, dr1, null);
            setBackgroundAlpha(0.5f);
        }
        TextView pile_status_normal_tv = view.findViewById(R.id.pile_status_normal_tv);
        TextView pile_status_using_tv = view.findViewById(R.id.pile_status_using_tv);
        TextView pile_status_stopped_tv = view.findViewById(R.id.pile_status_stopped_tv);
        final Drawable dr2 = context.getResources().getDrawable(R.mipmap.pop_ic_down);
        tv_title.setCompoundDrawablePadding(5);
        pile_status_normal_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.setNavTitle(getString(R.string.pile_status_normal));
                handler.sendEmptyMessage(STATUS_NORMAL);
                tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, dr2, null);
                popupWindow.dismiss();
            }
        });
        pile_status_using_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.setNavTitle(getString(R.string.pile_status_using));
                handler.sendEmptyMessage(STATUS_USING);
                tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, dr2, null);
                popupWindow.dismiss();
            }
        });
        pile_status_stopped_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navBar.setNavTitle(getString(R.string.pile_status_stopped));
                handler.sendEmptyMessage(STATUS_STOPPED);
                tv_title.setCompoundDrawablesWithIntrinsicBounds(null, null, dr2, null);
                popupWindow.dismiss();
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        ((Activity) context).getWindow().setAttributes(lp);
    }
    /**
     * 回到工单列表
     */
    @OnClick(R.id.go_work_list_iv)
    public void goWorkList() {
        finish();
    }
}
