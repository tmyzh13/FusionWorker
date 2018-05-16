package com.bm.fusionworker.model.beans;

/**
 *  获取用户信息接口 返回数据 实体类
 */
public class UserInfoBean {

    public String photoUrl;
    public String name;
    public int sex;
    public String sexName;
    public String email;
    public String birth;
    public String address;

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "photoUrl='" + photoUrl + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", sexName='" + sexName + '\'' +
                ", email='" + email + '\'' +
                ", birth='" + birth + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
