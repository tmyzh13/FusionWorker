package com.bm.fusionworker.presenter;

import android.util.Log;

import com.bm.fusionworker.model.apis.UserApi;
import com.bm.fusionworker.model.beans.AddFeedBackRequestBean;
import com.bm.fusionworker.model.beans.BaseData;
import com.bm.fusionworker.model.beans.UserBean;
import com.bm.fusionworker.model.beans.UserInfoBean;
import com.bm.fusionworker.model.interfaces.UserView;
import com.corelibs.api.ApiFactory;
import com.corelibs.api.ResponseTransformer;
import com.corelibs.base.BasePresenter;
import com.corelibs.subscriber.ResponseSubscriber;
import com.corelibs.utils.PreferencesHelper;

/**
 * Created by issuser on 2018/5/5.
 */

public class UserPresenter extends BasePresenter<UserView>{

    private UserApi api;
    @Override
    public void onStart() {}

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        api = ApiFactory.getFactory().create(UserApi.class);
    }

    /**
     * @param
     * @param
     */
    public void getUserInfo(){
        String token= PreferencesHelper.getData("token");
        Log.e("yzh","token--"+token);
        String token1=token.replaceAll("\"","");
        UserBean bean=new UserBean();
        api.getUserInfo(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData<UserInfoBean>>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData<UserInfoBean>>() {
                    @Override
                    public void success(BaseData<UserInfoBean> userInfoBeanBaseData) {
                        view.getUserInfo(userInfoBeanBaseData.data);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.getUserInfoError();
                    }

                    @Override
                    public boolean operationError(BaseData<UserInfoBean> userInfoBeanBaseData, int status, String message) {
                        view.getUserInfoError();
                        return super.operationError(userInfoBeanBaseData, status, message);
                    }
                });
    }
    public void  addFeedBack(String content){
        AddFeedBackRequestBean bean=new AddFeedBackRequestBean();
        bean.content=content;
        String token= PreferencesHelper.getData("token");
        String token1=token.replaceAll("\"","");
        api.addFeedBack(token1,bean)
                .compose(new ResponseTransformer<>(this.<BaseData>bindToLifeCycle()))
                .subscribe(new ResponseSubscriber<BaseData>() {
                    @Override
                    public void success(BaseData userInfoBeanBaseData) {
                        view.addFeedBackSuccess();
                    }

                });

    }

}
