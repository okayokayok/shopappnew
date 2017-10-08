package com.example.neu.shopapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.example.neu.baseinfo.Connectinfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TestVolleyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_volley);
        //1.创建volley请求队列
        RequestQueue queue=Volley.newRequestQueue(this);
        //2.创建volley 请求
        Request<JSONObject> request=new Request<JSONObject>(Request.Method.POST, Connectinfo.loginurl, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(TestVolleyActivity.this,"error:"+volleyError.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }) {
            //在服务器端响应数据返回时，自动调用该方法，返回的数据都封装成networkresponse类型的对象，volley要求所有实现request接口的子类必须重写该方法，自定义解析数据的格式，
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
                String jsonStr=new String(networkResponse.data);
                try {
                    return Response.success(new JSONObject(jsonStr), HttpHeaderParser.parseCacheHeaders(networkResponse));
                } catch (JSONException e) {
                    e.printStackTrace();
                    return Response.error(new ParseError(e));
                }

            }
            //该方法就是处理服务器响应的回调函数,该方法参数就是服务器的返回数据。
            @Override
            protected void deliverResponse(JSONObject jsonObject) {
                Toast.makeText(TestVolleyActivity.this,"json:"+jsonObject,Toast.LENGTH_SHORT).show();
            }
            //post方式下，如果有请求参数的话，重写该方法

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params=new HashMap<>();
                params.put("userName","11");
                params.put("userPass","11");
                return params;
            }
        };
        //3.添加volley请求到队列中
        queue.add(request);


    }
}
