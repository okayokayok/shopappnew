package com.example.neu.shopapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.neu.util.Md5Util;

/**
 * Created by hasee on 2017/9/18.
 */

class UserManage {
    private static UserManage instance;

    private UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }

    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))&&!TextUtils.isEmpty(userInfo.getAuthority())) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
        }
    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String userName, String userPass) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("authority", Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPass)));
        editor.putString("userName",userName );
        editor.putString("userPass", userPass);
        editor.commit();
    }

    private UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("userName", ""));
        userInfo.setPassword(sp.getString("userPass", ""));
        //authority=Md5Util.getMD5Str(userName+","+Md5Util.getMD5Str(userPass));
        userInfo.setAuthority();
        return userInfo;
    }
}

