//package com.example.neu.shopapp;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import com.android.volley.Response;
//import com.example.neu.util.PrefStore;
//
//import org.json.JSONObject;
//
//public class AutoLoginActivity extends Activity implements Response.Listener<JSONObject> {
//    //
//    private static final int GO_HOME = 0;//去主页
//    private static final int GO_LOGIN = 1;//去登录页
//    /**
//     * 跳转判断
//     */
//    private Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case GO_HOME://去主页
//                    Intent intent = new Intent(AutoLoginActivity.this, HomeActivity.class);
//                    startActivity(intent);
//                    finish();
//                    break;
//                case GO_LOGIN://去登录页
//                    Intent intent2 = new Intent(AutoLoginActivity.this, MainActivity.class);
//                    startActivity(intent2);
//                    finish();
//                    break;
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_auto_login);
//
//        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
//        {
//            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
//        } else {
//            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
//        }
//    }
//
//    @Override
//    public void onResponse(JSONObject jsonObject) {
//        if (jsonObject!=null){
//            if (jsonObject.optString("autoLoginFlag").equals("1")){
//                //自动登录成功
//                Intent intent=new Intent(this,HomeActivity.class);
//                startActivity(intent);
//            }else{//自动登录失败
//                Toast.makeText(this,jsonObject.optString("msg"),Toast.LENGTH_LONG).show();
//                PrefStore pref=PrefStore.getInstance();
//                pref.removePref("authority");
//                Intent intent=new Intent(this,MainActivity.class);
//                startActivity(intent);
//
//            }
//            finish();
//        }
//    }
//}
//
//
//

package com.example.neu.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
//import com.example.neu.shopapp.HomeActivity;
//import com.example.neu.shopapp.R;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;
import com.example.neu.util.PrefStore;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AutoLoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);
        PrefStore pref=PrefStore.getInstance();
        if (pref.getPref("authority",null)!=null)
        {
            Map<String,String> params=new HashMap<>();
            params.put("userName",pref.getPref("userName",""));
            params.put("authority",pref.getPref("authority",""));
            MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.autologinurl,params,this);
            MyApplication.getInstance().addToRequestQueue(mr);
            //自动登录操作
        }else
        {
            //不需要自动登录操作
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        if (jsonObject!=null)
        {
            if (jsonObject.optString("autoLoginFlag").equals("1"))
            {
                //自动登录成功
                Intent intent=new Intent(this, HomeActivity.class);
                startActivity(intent);

            }else
            {
                //自动登录失败
                Toast.makeText(this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
                PrefStore pref=PrefStore.getInstance();
                pref.removePref("userName");
                pref.removePref("authority");
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            finish();
        }
    }
}

