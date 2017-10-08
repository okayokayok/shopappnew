package com.example.neu.shopapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegActivity extends AppCompatActivity implements Response.Listener<JSONObject>,View.OnClickListener{
    String userSex;
    int regFlag;
    EditText un,up1,up2,ua,ue;
    Button reg;
    RadioButton sf,sm;
    RadioGroup radioGroup;
    String txtSex;
    String userPass,userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        un=(EditText)findViewById(R.id.editText);
        up1=(EditText)findViewById(R.id.editText2);
        up2=(EditText)findViewById(R.id.editText3);
        ua=(EditText)findViewById(R.id.editText4);
        ue=(EditText)findViewById(R.id.editText5);
        sf=(RadioButton)findViewById(R.id.radioButton3);
        sm=(RadioButton)findViewById(R.id.radioButton4);
        reg=(Button)findViewById(R.id.button2);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        reg.setOnClickListener(this);

    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        Toast.makeText(RegActivity.this,"json"+jsonObject,Toast.LENGTH_LONG).show();
        try {
            regFlag = jsonObject.getInt("regFlag");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (regFlag==1){


            Intent intent=new Intent();
            //data.putExtra("userName", userName);
            //data.putExtra("userPass", userPass);
            //setResult(1, data);
            intent.putExtra("userName",userName);
            intent.putExtra("userPass",userPass);
            this.setResult(Activity.RESULT_OK,intent);
            //data.setClass(RegActivity.this, MainActivity.class);
             //RegActivity.this.startActivity(data);
            finish();

            Toast.makeText(this,"注册成功，请登录",Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this, jsonObject.optString("msg"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
         userName=un.getText().toString();
        String userPass1=up1.getText().toString();
        String userPass2=up2.getText().toString();
        if (userPass1.equals(userPass2)){
             userPass=userPass1;
            String userAge=ua.getText().toString();
            String userEmail=ue.getText().toString();
            boolean flag=false;
            for(int i = 0;i < radioGroup.getChildCount();i++){
                RadioButton rb = (RadioButton)radioGroup.getChildAt(i);
                if(rb.isChecked()){
                    txtSex=rb.getText().toString();
                    if (txtSex.equals("男")){
                         userSex="0";
                         flag=true;
                    }else {
                         userSex="1";
                         flag=true;
                    }
                }

                }if (flag==false){
                Toast.makeText(this,"请选择性别",Toast.LENGTH_LONG);
            }

            Map<String,String> params=new HashMap<>();
            params.put("userName",userName);
            params.put("userPass",userPass);
            params.put("userAge",userAge);
            params.put("userSex",userSex);
            params.put("userEmail",userEmail);
            MyVolleyRequest mr=new MyVolleyRequest(Request.Method.POST, Connectinfo.regurl,params,this);
            MyApplication.getInstance().addToRequestQueue(mr);
        }else {
            Toast.makeText(this,"密码不一致，请重新输入密码",Toast.LENGTH_LONG);
        }

    }

}
