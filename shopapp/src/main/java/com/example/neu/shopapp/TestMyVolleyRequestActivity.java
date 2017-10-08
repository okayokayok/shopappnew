package com.example.neu.shopapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyVolleyRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestMyVolleyRequestActivity extends AppCompatActivity implements Response.Listener<JSONObject> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_my_volley_request);
        Map<String,String> params=new HashMap<>();
        params.put("userName","11");
        params.put("userPass","11");
        MyVolleyRequest mr=new MyVolleyRequest(Request.Method.POST, Connectinfo.loginurl,params,this);
        MyApplication.getInstance().addToRequestQueue(mr);
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        Toast.makeText(TestMyVolleyRequestActivity.this,"json"+jsonObject,Toast.LENGTH_LONG).show();
    }
}
