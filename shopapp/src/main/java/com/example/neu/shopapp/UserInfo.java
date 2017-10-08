package com.example.neu.shopapp;

import com.example.neu.util.Md5Util;

/**
 * Created by hasee on 2017/9/18.
 */

class UserInfo {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;
    private String authority;

    public String getPassword() {
        return userPass;
    }

    public void setPassword(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setAuthority() {
        this.authority = Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPass));;
        //authority=Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPass));
    }

    public String getAuthority() {
        return authority;
    }
}
