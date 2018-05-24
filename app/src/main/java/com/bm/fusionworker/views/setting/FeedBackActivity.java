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
import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.beans.SuggestionBean;
import com.bm.fusionworker.model.interfaces.SuggestionView;
import com.bm.fusionworker.presenter.SuggestionPresenter;
import com.bm.fusionworker.utils.Tools;
import com.bm.fusionworker.weights.NavBar;
import com.corelibs.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

public class FeedBackActivity extends BaseActivity<SuggestionView, SuggestionPresenter> implements SuggestionView {

    @Bind(R.id.nav)
    NavBar nav;
    @Bind(R.id.edit_suggestion)
    EditText edit_suggestion;

    public static Intent getLauncher(Context context) {
        Intent intent = new Intent(context, FeedBackActivity.class);
        return intent;
    }

    @Override
    public void goLogin() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feed_back;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        nav.setColorRes(R.color.app_blue);
        nav.setNavTitle(getString(R.string.feed_back));
    }

    @OnClick(R.id.submit_view)
    public void submit() {
        if (Tools.isNull(edit_suggestion.getText().toString())) {
            showToast(getString(R.string.advice_is_null));
            return;
        }
        SuggestionBean bean = new SuggestionBean();
        bean.appUserId = UserHelper.getSavedUser().appUserId;
        bean.feedbackContent = edit_suggestion.getText().toString();
        presenter.commit(bean);
    }

    @Override
    protected SuggestionPresenter createPresenter() {
        return new SuggestionPresenter();
    }

    @Override
    public void CommitSucess() {
        showToast(getString(R.string.user_info_modify_success));
        FeedBackActivity.this.finish();
    }
}
