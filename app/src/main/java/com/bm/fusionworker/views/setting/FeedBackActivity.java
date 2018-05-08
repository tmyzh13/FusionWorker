package com.bm.fusionworker.views.setting;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.bm.fusionworker.R;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;
import com.corelibs.base.BasePresenter;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.nav)
    NavBar nav;

    @Bind(R.id.feedback_text)
    EditText mFeedbackView;
    @Bind(R.id.feedback_text_show_num)
    TextView mNumTextShow;
    @Bind(R.id.submit_view)
    TextView mSubmitView;

    private static final int MAX_LENGTH = 150;//最大输入字符数150 
    private int Rest_Length = MAX_LENGTH;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        return intent;
    }
    @Override
    public void goLogin() {}

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.feed_back));
        mNumTextShow.setText(Html.fromHtml("<font color=\"red\">" + MAX_LENGTH + "/" + MAX_LENGTH + "</font>"));
        mFeedbackView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mNumTextShow.setText(Html.fromHtml("您还可以输入:" + "<font color=\"red\">" + Rest_Length + "/" + MAX_LENGTH + "</font>"));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Rest_Length > 0) {
                    Rest_Length = MAX_LENGTH - mFeedbackView.getText().length();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mNumTextShow.setText(Html.fromHtml("<font color=\"red\">" + Rest_Length + "/" + MAX_LENGTH + "</font>"));
            }
        });
    }

    @OnClick(R.id.submit_view)
    public void submit() {
        if (mFeedbackView.getText().length() <= 0) {
            return;
        }
        //todo
//        presenter.addFeedBack(mFeedbackView.getText().toString());
        //上传反馈信息
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
