//package com.example.neu.shopapp;
//
//import android.content.Intent;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
////import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.example.neu.baseinfo.Connectinfo;
//import com.example.neu.util.MyApplication;
//import com.example.neu.util.MyVolleyRequest;
////import com.example.neu.shopapp.TestMyVolleyRequestActivity;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import static com.example.neu.shopapp.R.id.userNameEt;
//import static com.example.neu.shopapp.R.id.userPassEt;
//
//
//public class MainActivity extends AppCompatActivity implements View.OnClickListener,Response.Listener<JSONObject> {
//    int loginFlag;
//    Button loginBtn;
//    EditText un,up;
//    CheckBox autoLoginCheckBox;
//    TextView reg;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//
//       // getWindow().addFlags(WindowManager, ActionBar.LayoutParams.Flag_FU);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//
//        setContentView(R.layout.activity_main);
//        loginBtn=(Button) findViewById(R.id.loginBtn);
//        un=(EditText)findViewById(userNameEt);
//
//        up=(EditText)findViewById(R.id.userPassEt);
//        autoLoginCheckBox=(CheckBox)findViewById(R.id.isAutoLogCb);
//        TextView reg=(TextView)findViewById(R.id.regTv);
//        loginBtn.setOnClickListener(this);
//
//
////        @Override
////                onActivityResult(int requestCode,int resultCode,Stirng data){
////            super.onActivityResult(requestCode,resultCode,data);
////            if (requestCode==200&&data!=null){
////                String userName=data.getStringExtra("userName");
////                String userPass=data.getStringExtra("userPass");
////            }
////        }
//
//
//
//
//     reg.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View view) {
//             Intent intent=new Intent(MainActivity.this,RegActivity.class);
//             startActivityForResult(intent,200);
//         }
//     });
//
//    }
//
//
//
//    @Override
//    public void onClick(View view) {
//
//        String userName=un.getText().toString();
//        String userPass=up.getText().toString();
//        Map<String,String> params=new HashMap<>();
//        params.put("userName",userName);
//        params.put("userPass",userPass);
//        MyVolleyRequest mr=new MyVolleyRequest(Request.Method.POST, Connectinfo.loginurl,params,MainActivity.this);
//        MyApplication.getInstance().addToRequestQueue(mr,"login");
//
//
//
////        @Override
////        public void onResponse(JSONObject jsonObject) {
////            Toast.makeText(TestMyVolleyRequestActivity.this,"json"+jsonObject,Toast.LENGTH_LONG).show();
////        }
//    }
//
//
//    @Override
//    public void onResponse(JSONObject jsonObject) {
//
//        Toast.makeText(MainActivity.this,"json"+jsonObject,Toast.LENGTH_LONG).show();
//
//        try {
//            loginFlag = jsonObject.getInt("loginFlag");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (loginFlag==1){
////            Intent intent=new Intent();
////            intent.setClass(MainActivity.this,HomeActivity.class);
////            MainActivity.this.startActivityForResult(intent,1);
////            this.finish();
//              Intent intent=new Intent();
//            intent.setClass(this, HomeActivity.class);
//
//            if (autoLoginCheckBox.isChecked()){
//                UserManage.getInstance().saveUserInfo(MainActivity.this, un.getText().toString(), up.getText().toString());
//            }
//
//
//            this.startActivity(intent);
//            this.finish();
//        }else{
//            Toast.makeText(this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
//        }
//
//
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==200&&data!=null){
//            String userName=data.getStringExtra("userName");
//            String userPass=data.getStringExtra("userPass");
//            un.setText(userName);
//            up.setText(userPass);
//        }
//    }
//
//
//    //protected void onActivityResult(int requestCode, int resultCode, Intent data){
////        switch (requestCode) {
////            case 1:
//////判断注册是否成功  如果注册成功
////                if(resultCode==RESULT_OK){
//////则获取data中的账号和密码  动态设置到EditText中
////                    String userName=data.getStringExtra("userName");
////                    String userPass=data.getStringExtra("userPass");
////                    un.setText(userName);
////                    up.setText(userPass);
////                }
////                break;
////        }
//
//
//}

package com.example.neu.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.Md5Util;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;
import com.example.neu.util.PrefStore;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject> {
    private TextView regTv;
    private Button loginBtn;
    private EditText usernameEt;
    private EditText userpassEt;
    private CheckBox isautologinCb;
    private boolean isautologin;
    private String userName,userPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        regTv = (TextView) findViewById(R.id.regTv);
        usernameEt = (EditText) findViewById(R.id.userNameEt);
        userpassEt = (EditText) findViewById(R.id.userPassEt);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        isautologinCb = (CheckBox) findViewById(R.id.isAutoLogCb);

        regTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, RegActivity.class);
                startActivity(intent1);

            }
        });
        usernameEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                userName = usernameEt.getText().toString();
                if (TextUtils.isEmpty(userName)){
                    usernameEt.setError("请输入用户名");
                }
            }

        });
        userpassEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                userPassword = userpassEt.getText().toString();
                if (TextUtils.isEmpty(userPassword)){
                    userpassEt.setError("请输入密码");
                }
            }

        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = usernameEt.getText().toString();
                userPassword = userpassEt.getText().toString();
                isautologin=isautologinCb.isChecked();
                Map<String,String> params = new HashMap<String, String>();
                params.put("userName", userName);
                params.put("userPass", userPassword);

                MyVolleyRequest mr = new MyVolleyRequest(Connectinfo.loginurl,
                        params,MainActivity.this);

                MyApplication.getInstance().addToRequestQueue(mr,"login");



            }

        });


    }
    @Override
    public void onStop(){
        super.onStop();
        MyApplication.getInstance().cancelPendingRequests("login");
    }

    @Override
    public void onResponse(JSONObject response) {
        System.out.println(response);
        // TODO Auto-generated method stub
        if (response != null) {
            if (response.optString("loginFlag").equals("1")) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);

                this.finish();//关闭当前登录窗口
                PrefStore pref = PrefStore.getInstance(MainActivity.this);
                if (isautologin) {//选中自动登录
                    //authority格式：MD5加密过的(username+’,’+pwd)（MD5为32位小写）
                    pref.savePref("userName", userName);
                    pref.savePref("authority", Md5Util.getMD5Str(userName + "," + Md5Util.getMD5Str(userPassword)));
                    Log.v("authority", Md5Util.getMD5Str(userName + "," + Md5Util.getMD5Str(userPassword)));
                }
                pref.savePref("curUserName", userName);
            } else {
                Toast.makeText(MainActivity.this, response.optString("msg"),
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==200&&data!=null)
        {
            String username=data.getStringExtra("username");
            String password=data.getStringExtra("password");
            usernameEt.setText(username);
            userpassEt.setText(password);
        }
    }
}



