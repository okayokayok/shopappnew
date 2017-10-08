package com.example.neu.shopapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CateFragment extends Fragment {


    private MyGridView cate;
    private MyGridViewAdapter adapter3;
    private ArrayList<Map<String,String>> data3;


    public CateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_cate, container, false);

        cate=(MyGridView) view.findViewById(R.id.cate);
        data3=new ArrayList<>();
        adapter3=new MyGridViewAdapter(data3,R.layout.cate_grid_view_item);
        cate.setAdapter(adapter3);
        MyVolleyRequest mr=new MyVolleyRequest(Connectinfo.cateurl,new CateListener());
        MyApplication.getInstance().addToRequestQueue(mr);

        cate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Map<String,String> m=data3.get(i);
                Intent intent=new Intent(CateFragment.this.getActivity(),GoodsActivity.class);
                intent.putExtra("cateId",m.get("id"));
                startActivity(intent);
            }
        });



        return view;
    }



    private class CateListener implements Response.Listener<JSONObject> {
        @Override
        public void onResponse(JSONObject jsonObject) {
            if (jsonObject!=null){
                parse(jsonObject);
                adapter3.notifyDataSetChanged();
            }
        }

    }

    private void parse(JSONObject jsonObject) {
        JSONArray ja= jsonObject.optJSONArray("catesList");
        if (ja!=null){
            if (ja.length()>0){
                for (int i=0;i<ja.length();i++){
                    Map<String,String> m=new HashMap<>();
                    JSONObject a=ja.optJSONObject(i);
                    m.put("url",Connectinfo.contexturl+a.optString("catePic"));
                    m.put("id",a.optString("cateId"));
                    m.put("text",a.optString("cateName"));
                    //+ "\r\n现售： ￥"+a.optString("goodsDiscount")
                    data3.add(m);
                }
            }else{
                Toast.makeText(CateFragment.this.getActivity(),"当前没有商品分类",Toast.LENGTH_LONG).show();
            }
        }
    }
}
