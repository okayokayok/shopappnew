package com.example.neu.shopapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.Response;
import com.example.neu.baseinfo.Connectinfo;
import com.example.neu.util.MyApplication;
import com.example.neu.util.MyGridView;
import com.example.neu.util.MyGridViewAdapter;
import com.example.neu.util.MyVolleyRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GoodsActivity extends AppCompatActivity {

    private GridView goods;
    private MyGridViewAdapter adapter4;
    private ArrayList<Map<String,String>> data4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);

        Intent intent=getIntent();
        String cateId=intent.getStringExtra("cateId");
        Map<String,String> params = new HashMap<String, String>();

        params.put("cateId",cateId);

        goods=(GridView)findViewById(R.id.goodsByCate);
        data4=new ArrayList<>();
        adapter4=new MyGridViewAdapter(data4,R.layout.goods_grid_view_item);
        goods.setAdapter(adapter4);
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.goodsurl,params,new GoodsListener());
        MyApplication.getInstance().addToRequestQueue(mr);

        goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,String> m=data4.get(i);
                Intent intent=new Intent(GoodsActivity.this,GoodsDetailActivity.class);
                intent.putExtra("goodsId",m.get("id"));
                startActivity(intent);
            }
        });

    }

    private class GoodsListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {


            JSONArray ja= jsonObject.optJSONArray("goodsList");
            if (ja!=null){
                if (ja.length()>0){
                    for (int i=0;i<ja.length();i++){
                        Map<String,String> m=new HashMap<>();
                        JSONObject a=ja.optJSONObject(i);
                        m.put("url",Connectinfo.contexturl+a.optString("goodsPic"));
                        m.put("id",a.optString("goodsId"));
                        m.put("text",a.optString("goodsName")+"\r\n原价： ￥"+a.optString("goodsPrice")+ "\r\n现售： ￥"+a.optString("goodsDiscount"));
                        //+ "\r\n现售： ￥"+a.optString("goodsDiscount")
                        data4.add(m);
                    }
                }else{
                    Toast.makeText(GoodsActivity.this,"当前分类没有商品",Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}
