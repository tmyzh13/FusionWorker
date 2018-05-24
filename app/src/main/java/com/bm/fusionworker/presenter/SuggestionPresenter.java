package com.bm.fusionworker.presenter;

import com.bm.fusionworker.model.UserHelper;
import com.bm.fusionworker.model.apis.SuggestionApi;
import com.bm.fusionworker.model.beans.ResultOnlyWithCodeBean;
import com.bm.fusionworker.model.beans.SuggestionBean;
import com.bm.fusionworker.model.interfaces.SuggestionView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.trello.rxlifecycle.ActivityEvent;

/**
 * Created by admin on 2018/5/8.
 */

public class SuggestionPresenter extends BasePresenter<SuggestionView> {
    SuggestionApi api;

    @Override
    public void onStart() {
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(SuggestionApi.class);
    }

    public void commit(SuggestionBean bean){
        api.commit(UserHelper.getSavedUser().token, bean)
        .compose(new ResponseTransformer<>(this.<ResultOnlyWithCodeBean>bindUntilEvent(ActivityEvent.DESTROY)))
                .subscribe(new ResponseSubscriber<ResultOnlyWithCodeBean>(view) {
                    @Override
                    public void success(ResultOnlyWithCodeBean baseData) {
                        view.CommitSucess();
                    }
                });
    }

}
