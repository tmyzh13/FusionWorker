package com.bm.fusionworker.model;


import com.bm.fusionworker.model.beans.UserBean;
import com.bm.fusionworker.model.beans.UserInfoBean;
import com.corelibs.utils.PreferencesHelper;


public class UserHelper {
    public static void saveUser(UserBean user) {
        PreferencesHelper.saveData(user);
    }

    public static void saveUserInfo(UserInfoBean userInfoBean) {
        PreferencesHelper.saveData(userInfoBean);
    }

    public static UserBean getSavedUser() {
        return PreferencesHelper.getData(UserBean.class);
    }

    public static long getUserId() {
        return getSavedUser().id;
    }
    public static void clearUserInfo(Class user){
        PreferencesHelper.remove(user);
    }





    public static  void clearInfo(String key){
        PreferencesHelper.remove(key);
    }
}
